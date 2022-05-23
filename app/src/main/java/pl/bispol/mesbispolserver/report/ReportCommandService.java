package pl.bispol.mesbispolserver.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.breakdown.BreakdownQueryService;
import pl.bispol.mesbispolserver.downtime.DowntimeExecuted;
import pl.bispol.mesbispolserver.downtime.DowntimeExecutedState;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.line.WorkingHours;
import pl.bispol.mesbispolserver.product.ProductEfficient;
import pl.bispol.mesbispolserver.product.ProductEfficientQueryService;

import java.time.LocalDateTime;
import java.util.List;

@Service()
class ReportCommandService {
    private final ReportCommandRepository reportCommandRepository;
    private final LineQueryService lineQueryService;
    private final ProductEfficientQueryService productEfficientQueryService;
    private final BreakdownQueryService breakdownQueryService;
    private final Logger logger;

    @Autowired()
    ReportCommandService(ReportCommandRepository reportCommandRepository, LineQueryService lineQueryService, ProductEfficientQueryService productEfficientQueryService, BreakdownQueryService breakdownQueryService) {
        this.reportCommandRepository = reportCommandRepository;
        this.lineQueryService = lineQueryService;
        this.productEfficientQueryService = productEfficientQueryService;
        this.breakdownQueryService = breakdownQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Transactional
    public void createReport(final long lineId) {
        if (!reportCommandRepository.existsByStateAndLineId(ReportState.OPEN, lineId)) {
            Line line = lineQueryService
                    .findByIdAndOperatorExists(lineId)
                    .orElseThrow(() -> new BadRequestException(Statements.OPERATOR_NOT_EXISTS));

            if (line.getProductName().equals(""))
                throw new BadRequestException(Statements.LINE_HAS_NO_PRODUCT);

            ProductEfficient productEfficient = productEfficientQueryService
                    .findByProductNameAndLineName(line.getProductName(), line.getName())
                    .orElseThrow(() -> new BadRequestException(Statements.THERE_IS_NO_EFFICIENT_FOR_LINE));
            Report report = new Report(line, productEfficient.getAmount(), line.getCurrentCounter(), determineWorkShift(line.getWorkingHours()));
            breakdownQueryService.findByActiveAndLineId(lineId)
                    .forEach(report::addBreakdown);
            reportCommandRepository.save(report);

        } else
            throw new BadRequestException(Statements.REPORT_ALREADY_EXISTS);
    }

    @Transactional()
    public void closeReport(long reportId, long trashAmount) {
        Report report = reportCommandRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException(Statements.OPEN_REPORT_DOES_NOT_EXIST));
        Line line = report.getLine();
        boolean operatorCardDetected = !line.getOperator().equals("");
        if (operatorCardDetected) {
            report.setReportState(ReportState.CLOSED);
            report.setFinishOperator(line.getOperator());
            report.setFinishDate(LocalDateTime.now());
            report.setTrashAmount(trashAmount);
            report.setAmount(line.getCurrentCounter() - report.getStartCounter() - trashAmount);

            closeDowntimes(report);
        } else
            throw new BadRequestException(Statements.OPERATOR_NOT_EXISTS);

    }

    private void closeDowntimes(Report report) {
        report.getDowntimeExecutedList()
                .stream()
                .filter(r -> r.getDowntimeExecutedState() == DowntimeExecutedState.OPEN)
                .forEach(DowntimeExecuted::close);
    }

    private ReportWorkShift determineWorkShift(WorkingHours workingHours) {
        return workingHours == WorkingHours.HOURS8 ? workShiftFor8Hours() : workShiftFor12Hours();
    }

    private ReportWorkShift workShiftFor8Hours() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startFirst = LocalDateTime.now().withHour(6).withMinute(41);
        LocalDateTime finishFirst = LocalDateTime.now().withHour(14).withMinute(40);

        LocalDateTime startSecond = LocalDateTime.now().withHour(14).withMinute(41);
        LocalDateTime finishSecond = LocalDateTime.now().withHour(22).withMinute(40);

        if (now.isAfter(startFirst) && now.isBefore(finishFirst))
            return ReportWorkShift.FIRST;
        else if (now.isAfter(startSecond) && now.isBefore(finishSecond))
            return ReportWorkShift.SECOND;
        else
            return ReportWorkShift.THIRD;
    }

    private ReportWorkShift workShiftFor12Hours() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startFirst = LocalDateTime.now().withHour(6).withMinute(41);
        LocalDateTime finishFirst = LocalDateTime.now().withHour(18).withMinute(40);

        if (now.isAfter(startFirst) && now.isBefore(finishFirst))
            return ReportWorkShift.FIRST;
        else
            return ReportWorkShift.SECOND;
    }

    void deleteById(long reportId) {
        if (reportCommandRepository.existsById(reportId)) reportCommandRepository.deleteById(reportId);
        else throw new NotFoundException(Statements.REPORT_DOES_NOT_EXIST);
    }

    public void closeReportScheduler(WorkingHours workingHours) {
        List<Report> openReports = reportCommandRepository.findByReportStateAndLineWorkingHours(ReportState.OPEN, workingHours);
        openReports.forEach((el) -> {
            el.setReportState(ReportState.CLOSED);
            el.setFinishOperator("MES System");
            el.setFinishDate(LocalDateTime.now());
            el.setTrashAmount(0);
            el.setAmount(el.getLine().getCurrentCounter() - el.getStartCounter());
            closeDowntimes(el);
        });
        logger.info("-----------------------CLOSING OPEN REPORTS-----------------------");
    }


    @Scheduled(cron = "0 55 6 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeReportFor8hFirstShift() {
        closeReportScheduler(WorkingHours.HOURS8);
    }


    @Scheduled(cron = "0 55 14 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeReportFor8hSecondShift() {
        closeReportScheduler(WorkingHours.HOURS8);
    }


    @Scheduled(cron = "0 55 22 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeReportFor8hFirstThird() {
        closeReportScheduler(WorkingHours.HOURS8);
    }


    @Scheduled(cron = "0 55 6 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeReportFor12hFirstShift() {
        closeReportScheduler(WorkingHours.HOURS12);
    }


    @Scheduled(cron = "0 55 18 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeReportFor12hSecondShift() {
        closeReportScheduler(WorkingHours.HOURS12);
    }

}
