package pl.bispol.mesbispolserver.downtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedCommandDto;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.line.WorkingHours;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportState;

import java.time.LocalDateTime;

@Service
class DowntimeExecutedCommandService {
    private final DowntimeExecutedCommandRepository downtimeExecutedCommandRepository;
    private final LineQueryService lineQueryService;
    private final Logger logger;

    @Autowired
    DowntimeExecutedCommandService(DowntimeExecutedCommandRepository downtimeExecutedCommandRepository, LineQueryService lineQueryService) {
        this.downtimeExecutedCommandRepository = downtimeExecutedCommandRepository;
        this.lineQueryService = lineQueryService;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    void deleteById(long id) {
        if (downtimeExecutedCommandRepository.existsById(id))
            downtimeExecutedCommandRepository.deleteById(id);
        else
            throw new NotFoundException(Statements.DOWN_TIME_NOT_EXISTS);
    }

    @Transactional
    public void save(DowntimeExecutedCommandDto downtimeExecutedCommandDto, long lineId) {
        Line line = lineQueryService.findById(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        Report report = line.getReports()
                .stream()
                .filter(r -> r.getReportState() == ReportState.OPEN)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Statements.OPEN_REPORT_DOES_NOT_EXIST));
        boolean openDowntimeAlreadyExists = report.getDowntimeExecutedList()
                .stream()
                .anyMatch(r -> r.getDowntimeExecutedState() == DowntimeExecutedState.OPEN);

        if (report.getReportState() == ReportState.CLOSED)
            throw new BadRequestException(Statements.OPEN_REPORT_DOES_NOT_EXIST);
        if (openDowntimeAlreadyExists) throw new BadRequestException(Statements.OPEN_DOWNTIME_ALREADY_EXISTS);
        if (line.getOperator().equals("")) throw new BadRequestException(Statements.OPERATOR_NOT_EXISTS);

        DowntimeExecuted downtimeExecuted = DowntimeExecutedFactory.toEntity(downtimeExecutedCommandDto);
        downtimeExecuted.addReport(report);
        downtimeExecuted.setOperatorName(line.getOperator());
        downtimeExecuted.setOperatorRfid(line.getOperatorRfid());

        downtimeExecutedCommandRepository.save(downtimeExecuted);
    }

    @Transactional
    public void closeDowntimeExecuted(long downtimeId) {
        DowntimeExecuted downtimeExecuted = downtimeExecutedCommandRepository.findByIdAndOpenState(downtimeId)
                .orElseThrow(() -> new NotFoundException(Statements.DOWN_TIME_NOT_EXISTS));
        String currentOperator = downtimeExecuted
                .getReport()
                .getLine()
                .getOperator();

        if (currentOperator.equals("")) throw new BadRequestException(Statements.OPERATOR_NOT_EXISTS);

        downtimeExecuted.setCloseDate(LocalDateTime.now());
        downtimeExecuted.setDowntimeExecutedState(DowntimeExecutedState.CLOSE);
    }

    public void closeDowntimes(WorkingHours workingHours) {
        downtimeExecutedCommandRepository.findByOpenStateAndWorkingHours(workingHours)
                .forEach(DowntimeExecuted::close);

        logger.info("-----------------------CLOSING OPEN DOWNTIMES-----------------------");
    }


    @Scheduled(cron = "0 54 6 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeDowntimesFor8hFirstShift() {
        closeDowntimes(WorkingHours.HOURS8);

    }


    @Scheduled(cron = "0 54 14 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeDowntimesFor8hSecondShift() {
        closeDowntimes(WorkingHours.HOURS8);
    }


    @Scheduled(cron = "0 54 22 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeDowntimesFor8hFirstThird() {
        closeDowntimes(WorkingHours.HOURS8);
    }


    @Scheduled(cron = "0 54 6 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeDowntimesFor12hFirstShift() {
        closeDowntimes(WorkingHours.HOURS12);
    }


    @Scheduled(cron = "0 55 18 * * *")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeDowntimesFor12hSecondShift() {
        closeDowntimes(WorkingHours.HOURS12);
    }


}
