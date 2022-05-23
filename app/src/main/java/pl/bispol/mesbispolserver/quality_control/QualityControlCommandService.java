package pl.bispol.mesbispolserver.quality_control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.quality_control.dto.QualityInspectionCommandDto;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportState;
import pl.bispol.mesbispolserver.tools.HttpClientService;
import pl.bispol.mesbispolserver.user.UserQueryService;
import pl.bispol.mesbispolserver.user.UserRole;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.util.List;

@Service()
class QualityControlCommandService {
    private final QualityControlCommandRepository qualityControlCommandRepository;
    private final UserQueryService userQueryService;
    private final LineQueryService lineQueryService;
    private final HttpClientService httpClientService;

    @Autowired()
    QualityControlCommandService(QualityControlCommandRepository qualityControlCommandRepository, UserQueryService userQueryService, LineQueryService lineQueryService, HttpClientService httpClientService) {
        this.qualityControlCommandRepository = qualityControlCommandRepository;
        this.userQueryService = userQueryService;
        this.lineQueryService = lineQueryService;
        this.httpClientService = httpClientService;
    }

    void save(long lineId, List<QualityInspectionCommandDto> inspections) {
        Line line = lineQueryService.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));

        if (line.getOperator().equals(""))
            throw new BadRequestException(Statements.OPERATOR_NOT_EXISTS);

        Report report = line.getReports().stream().filter(r -> r.getReportState() == ReportState.OPEN)
                .findAny()
                .orElseThrow(() -> new NotFoundException(Statements.REPORT_DOES_NOT_EXIST));

        UserRole userRole = userQueryService.findByRfidId(line.getOperatorRfid())
                .map(UserQueryDto::getUserRole)
                .orElse(UserRole.NOT_EXISTS);

        QualityControl qualityControl = new QualityControl(line.getOperator(), userRole);
        qualityControl.setReport(report);

        inspections.stream()
                .map(QualityInspectionFactory::toEntity)
                .forEach(qualityControl::addQualityInspection);

        QualityControl qualityControlSaved = qualityControlCommandRepository.save(qualityControl);
        sendEmailAboutFailedQualityControl(qualityControlSaved);


    }

    void deleteById(long qualityControlId) {
        qualityControlCommandRepository.findById(qualityControlId)
                .ifPresentOrElse(q -> {
                            qualityControlCommandRepository.deleteById(q.getId());
                        },
                        () -> {
                            throw new NotFoundException(Statements.QUALITY_CONTROL_NOT_EXISTS);
                        }
                );
    }

    private void sendEmailAboutFailedQualityControl(QualityControl qualityControl) {
        qualityControl.getInspections()
                .stream()
                .filter(q -> !q.isQualityOK())
                .findAny()
                .ifPresent((q) -> {
                    try {
                        httpClientService.qualityControlSendEmail(QualityControlFactory.toEmailMsg(qualityControl));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
