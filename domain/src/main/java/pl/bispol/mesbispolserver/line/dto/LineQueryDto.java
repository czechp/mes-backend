package pl.bispol.mesbispolserver.line.dto;


import lombok.*;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownQueryDto;
import pl.bispol.mesbispolserver.line.LineStatus;
import pl.bispol.mesbispolserver.line.WorkingHours;
import pl.bispol.mesbispolserver.product.ProductType;
import pl.bispol.mesbispolserver.report.dto.ReportQueryDto;

import java.util.List;

@Getter()
@Setter()
@NoArgsConstructor()
@EqualsAndHashCode()
@ToString()
public class LineQueryDto {
    private long id;
    private String name;
    private long currentCounter;
    private String productName;
    private String operator;
    private ProductType productionType;
    private WorkingHours workingHours;
    private LineStatus lineStatus;
    private boolean isRfidReaderError;
    private boolean isOpcUaCommunicationError;
    private ReportQueryDto activeReport;
    private List<BreakdownQueryDto> activeBreakdowns;
}
