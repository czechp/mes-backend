package pl.bispol.mesbispolserver.breakdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.TimeCalculator;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownCommandDto;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.tools.HttpClientService;
import pl.bispol.mesbispolserver.user.UserQueryService;
import pl.bispol.mesbispolserver.user.UserRole;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
class BreakdownCommandService {
    private static final int EMAIL_NOTIFICATION_MINUTES = 120;
    private final BreakdownCommandRepository breakdownCommandRepository;
    private final LineQueryService lineQueryService;
    private final UserQueryService userQueryService;
    private final HttpClientService httpClientService;
    private final ReportQueryService reportQueryService;
    private final Logger logger;

    @Autowired
    BreakdownCommandService(BreakdownCommandRepository breakdownCommandRepository,
                            LineQueryService lineQueryService,
                            UserQueryService userQueryService,
                            HttpClientService httpClientService,
                            ReportQueryService reportQueryService) {
        this.breakdownCommandRepository = breakdownCommandRepository;
        this.lineQueryService = lineQueryService;
        this.userQueryService = userQueryService;
        this.httpClientService = httpClientService;
        this.reportQueryService = reportQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Transactional
    void deleteById(long id) {
        breakdownCommandRepository.findById(id)
                .ifPresentOrElse(
                        (b) -> {
                            b.clearReports();
                            breakdownCommandRepository.deleteById(id);
                        },
                        () -> {
                            throw new NotFoundException(Statements.BREAKDOWN_NOT_EXISTS);
                        }
                );
    }

    @Transactional
    void save(BreakdownCommandDto breakdownCommandDto, long lineId) {
        Line line = lineQueryService.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));

        if (line.getOperatorRfid().equals("")) throw new BadRequestException(Statements.OPERATOR_NOT_EXISTS);
        Breakdown breakdown = BreakdownFactory.toEntity(breakdownCommandDto, line, line.getOperatorRfid(), line.getOperator());
        httpClientService.breakdownSendEmails(breakdown);
        reportQueryService.findActiveByLineId(lineId)
                .ifPresent(breakdown::addReport);
        breakdown.getLine().setBreakdownState();
        breakdownCommandRepository.save(breakdown);
    }

    @Transactional
    public void changeStatusInProgress(long breakdownId, String umupNumber) {
        Breakdown breakdown = breakdownCommandRepository.findById(breakdownId)
                .orElseThrow(() -> new NotFoundException(Statements.BREAKDOWN_NOT_EXISTS));

        String userRfid = breakdown.getLine().getOperatorRfid();
        UserQueryDto maintenanceWorker = userQueryService.findByRfidId(userRfid).orElseThrow(() -> new BadRequestException(Statements.OPERATOR_NOT_EXISTS));

        if (maintenanceWorker.getUserRole() != UserRole.MAINTENANCE)
            throw new BadRequestException(Statements.USER_HAVE_TO_BE_MAINTENANCE);

        breakdown.inProgress(maintenanceWorker.getRfidId(),
                maintenanceWorker.getFirstName() + " " + maintenanceWorker.getSecondName(),
                umupNumber);
    }

    @Transactional
    public void changeStatusClose(long breakdownId) {
        Breakdown breakdown = breakdownCommandRepository.findById(breakdownId)
                .orElseThrow(() -> new NotFoundException(Statements.BREAKDOWN_NOT_EXISTS));

        String operatorRfid = breakdown.getLine().getOperatorRfid();
        if (operatorRfid.equals("")) throw new BadRequestException(Statements.OPERATOR_NOT_EXISTS);
        breakdown.close();
    }

    void checkDurationOfBreakdown(Breakdown breakdown) {
        if (TimeCalculator.timeBetweenToMinutes(breakdown.getCreationDate(), LocalDateTime.now()) >= EMAIL_NOTIFICATION_MINUTES) {
            breakdown.setNotificationSent(true);
            httpClientService.longBreakdownSendEmails(breakdown);
        }
    }

    @Scheduled(cron = "0 0/10 * * * *")
    @Transactional
    public void sendEmailsAboutLongBreakdown() {
        List<Breakdown> openBreakdown = breakdownCommandRepository.findByStatusNotClose();
        logger.info("--------------------------- Checking time of breakdowns ---------------------------");
        openBreakdown
                .stream()
                .filter(b -> !b.isNotificationSent())
                .forEach(this::checkDurationOfBreakdown);
    }


}
