package pl.bispol.mesbispolserver.line;

import pl.bispol.mesbispolserver.breakdown.BreakdownFactory;
import pl.bispol.mesbispolserver.breakdown.BreakdownStatus;
import pl.bispol.mesbispolserver.line.dto.LineCommandDto;
import pl.bispol.mesbispolserver.line.dto.LineQueryDto;
import pl.bispol.mesbispolserver.report.ReportFactory;
import pl.bispol.mesbispolserver.report.ReportState;

import java.util.stream.Collectors;

class LineFactory {
    static Line toEntity(LineCommandDto lineCommandDto) {
        return new Line(
                lineCommandDto.getName(),
                lineCommandDto.getProductionType(),
                lineCommandDto.getWorkingHours()
        );
    }

    static LineQueryDto toQueryDto(Line line) {
        LineQueryDto result = new LineQueryDto();
        result.setId(line.getId());
        result.setName(line.getName());
        result.setCurrentCounter(line.getCurrentCounter());
        result.setProductName(line.getProductName());
        result.setOperator(line.getOperator());
        result.setProductionType(line.getProductionType());
        result.setWorkingHours(line.getWorkingHours());
        result.setLineStatus(line.getLineStatus());
        result.setRfidReaderError(line.isRfidReaderError());
        result.setOpcUaCommunicationError(line.isOpcUaCommunicationError());
        line.getReports().stream()
                .filter(el -> el.getReportState() == ReportState.OPEN)
                .findAny()
                .ifPresentOrElse(
                        report -> result.setActiveReport(ReportFactory.toQueryDto(report)),
                        () -> result.setActiveReport(null)
                );
        result.setActiveBreakdowns(line.getBreakdowns()
                .stream()
                .filter(b -> b.getBreakdownStatus() != BreakdownStatus.CLOSE)
                .map(BreakdownFactory::toDto)
                .collect(Collectors.toList()));
        return result;
    }
}
