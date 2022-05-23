package pl.bispol.mesbispolserver.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.report.dto.ReportListWithStatsQueryDto;
import pl.bispol.mesbispolserver.report.dto.ReportQueryDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/api/reports")
@CrossOrigin("*")
class ReportController {
    private final ReportQueryService reportQueryService;
    private final ReportCommandService reportCommandService;
    private final ReportSpreadSheetGenerator reportSpreadSheetGenerator;

    @Autowired()
    public ReportController(ReportQueryService reportQueryService, ReportCommandService reportCommandService, ReportSpreadSheetGenerator reportSpreadSheetGenerator) {
        this.reportQueryService = reportQueryService;
        this.reportCommandService = reportCommandService;
        this.reportSpreadSheetGenerator = reportSpreadSheetGenerator;
    }

    @GetMapping("/{reportId}")
    ReportQueryDto findById(@PathVariable(name = "reportId") final long reportId) {
        return reportQueryService.findQueryDtoById(reportId)
                .orElseThrow(() -> new NotFoundException(Statements.REPORT_DOES_NOT_EXIST));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<ReportQueryDto> findBy() {
        return reportQueryService.findByClosedStateQueryDto();
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    ReportListWithStatsQueryDto findWithStatisticsBy() {
        return reportQueryService.findAllWithStatistics();
    }

    @GetMapping("/status/active/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    ReportQueryDto findActiveByLineId(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return reportQueryService.findActiveReport(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.OPEN_REPORT_DOES_NOT_EXIST));
    }

    @GetMapping("/line/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    List<ReportQueryDto> findByLine(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return reportQueryService.findByLineId(lineId);
    }

    @GetMapping("/line/statistics/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    ReportListWithStatsQueryDto findWithStatisticsByLine(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return reportQueryService.findByLineIdWithStatistics(lineId);
    }

    @GetMapping("/creationDate")
    @ResponseStatus(HttpStatus.OK)
    List<ReportQueryDto> findByCreationDateBetween(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime end
    ) {
        return reportQueryService.findByCreationDateBetween(start, end);
    }


    @GetMapping("/line/creationDate/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    List<ReportQueryDto> findByLineIdCreationDateBetween(
            @PathVariable(name = "lineId") final long lineId,
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime end
    ) {
        return reportQueryService.findByLineIdCreationDateBetween(lineId, start, end);
    }

    @GetMapping("/line/creationDate/spreadsheet/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity createSpreadSheetByLineIdAndCreationDateBetweenByLine(
            @PathVariable(name = "lineId") final long lineId,
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime end
    ) throws IOException {
        reportSpreadSheetGenerator.clearDirectories();
        String spreadSheetName = reportQueryService.generateReportsSpreadSheetByLine(lineId, start, end);
        return generateResponseEntityForSpreadSheet(spreadSheetName);
    }

    @GetMapping("/line/creationDate/spreadsheet")
    ResponseEntity createSpreadSheetByLineIdAndCreationDateBetween(
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime start,
            @RequestParam(name = "stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime end
    ) throws IOException {
        reportSpreadSheetGenerator.clearDirectories();
        String spreadSheetName = reportQueryService.generateReportsSpreadSheetByLine(start, end);
        return generateResponseEntityForSpreadSheet(spreadSheetName);
    }

    @PostMapping("/{lineId}")
    @ResponseStatus(HttpStatus.CREATED)
    void createReport(
            @PathVariable(name = "lineId") final long lineId
    ) {
        reportCommandService.createReport(lineId);
    }

    @PatchMapping("/status/close/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void closeReport(
            @PathVariable(name = "reportId") final long reportId,
            @RequestParam(name = "trashAmount") final long trashAmount
    ) {
        reportCommandService.closeReport(reportId, trashAmount);
    }

    @DeleteMapping("/{reportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_SUPERUSER"})
    void deleteById(@PathVariable(name = "reportId") final long reportId) {
        reportCommandService.deleteById(reportId);
    }

    private ResponseEntity<InputStreamResource> generateResponseEntityForSpreadSheet(String spreadSheetName) throws FileNotFoundException {
        File spreadSheetFile = new File(spreadSheetName);
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(spreadSheetFile));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", spreadSheetFile.getName()));
        httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(spreadSheetFile.length())
                .contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(inputStreamResource);
    }
}
