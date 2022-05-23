package pl.bispol.mesbispolserver.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.dto.LineCommandDto;
import pl.bispol.mesbispolserver.product.ProductQueryService;
import pl.bispol.mesbispolserver.product.dto.ProductQueryDto;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.tools.HttpClientService;
import pl.bispol.mesbispolserver.user.UserQueryService;
import pl.bispol.mesbispolserver.user.dto.UserQueryDto;

import java.net.URISyntaxException;
import java.util.Optional;

@Service()
class LineCommandService {
    private final LineCommandRepository lineCommandRepository;
    private final UserQueryService userQueryService;
    private final ProductQueryService productQueryService;
    private final ReportQueryService reportQueryService;
    private final HttpClientService httpClientService;

    @Autowired()
    LineCommandService(LineCommandRepository lineCommandRepository,
                       UserQueryService userQueryService,
                       ProductQueryService productQueryService,
                       ReportQueryService reportQueryService,
                       HttpClientService httpClientService) {
        this.lineCommandRepository = lineCommandRepository;
        this.userQueryService = userQueryService;
        this.productQueryService = productQueryService;
        this.reportQueryService = reportQueryService;
        this.httpClientService = httpClientService;
    }

    @Transactional()
    public void updateOperator(final long lineId, final String userRfid) {
        Line line = lineCommandRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        Optional<UserQueryDto> user = userQueryService.findByRfidId(userRfid);
        user.ifPresentOrElse(
                (u) -> {
                    line.setOperator(u.getFirstName() + " " + u.getSecondName());
                    line.setOperatorRfid(u.getRfidId());
                },
                () -> {
                    line.setOperator("");
                    line.setOperatorRfid("");
                }
        );
        line.setRfidReaderError(false);
    }


    void setRfidReaderError(final long lineId) {
        Line line = lineCommandRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        line.setRfidReaderError(true);
        lineCommandRepository.save(line);
    }

    void setOpcUError(long lineId) {
        Line line = lineCommandRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        line.setOpcUaCommunicationError(true);
        lineCommandRepository.save(line);
    }

    @Transactional()
    public void updateLineBasicInfo(long lineId, LineStatus lineStatus, long lineCounter) {
        Line line = lineCommandRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        line.setOpcUaCommunicationError(false);
        line.setCurrentCounter(lineCounter);
        if (line.getLineStatus() != LineStatus.BREAKDOWN)
            line.setLineStatus(lineStatus);

    }

    void save(LineCommandDto lineCommandDto) {
        lineCommandRepository.save(LineFactory.toEntity(lineCommandDto));
    }

    @Transactional()
    public void deleteById(final long lineId) {
        if (lineCommandRepository.existsById(lineId))
            lineCommandRepository.deleteById(lineId);
        else
            throw new NotFoundException(Statements.LINE_NOT_EXISTS);
    }

    @Transactional()
    public void modifyLine(long lineId, LineCommandDto lineCommandDto) {
        Line line = lineCommandRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        line.setName(lineCommandDto.getName());
        line.setWorkingHours(lineCommandDto.getWorkingHours());
        line.setProductionType(lineCommandDto.getProductionType());
    }

    @Transactional()
    public void updateProduct(long lineId, long productId) {
        Line line = lineCommandRepository.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        ProductQueryDto product = productQueryService.findById(productId)
                .orElseThrow(() -> new NotFoundException(Statements.PRODUCT_NOT_EXISTS));

        reportQueryService.findActiveReport(lineId)
                .ifPresentOrElse(
                        (report) -> {
                            throw new BadRequestException(Statements.ACTIVE_REPORT_EXISTS);
                        }, () -> {
                            product.getProductEfficients()
                                    .stream()
                                    .filter(el -> el.getLineName().equals(line.getName()))
                                    .findAny()
                                    .orElseThrow(() -> new BadRequestException(Statements.PRODUCT_HAS_NO_LINE));
                            line.setProductName(product.getName());
                        });
    }

    @Scheduled(cron = "5 56 6 * * *")
    @Transactional()
    public void resetLineCounter() throws URISyntaxException {
        httpClientService.resetLineCounters();
    }
}

