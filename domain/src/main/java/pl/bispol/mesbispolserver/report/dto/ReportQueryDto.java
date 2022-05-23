package pl.bispol.mesbispolserver.report.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownQueryDto;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedQueryDto;
import pl.bispol.mesbispolserver.product.ProductType;
import pl.bispol.mesbispolserver.quality_control.dto.QualityControlQueryDto;
import pl.bispol.mesbispolserver.report.ReportState;
import pl.bispol.mesbispolserver.report.ReportWorkShift;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialQueryDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter()
@Setter()
@NoArgsConstructor()
@EqualsAndHashCode()
@ToString()
public class ReportQueryDto {
    List<QualityControlQueryDto> qualityControls = new ArrayList<>();
    List<UsedRawMaterialQueryDto> materials = new ArrayList<>();
    private long id;
    private String createOperator;
    private String finishOperator;
    private ProductType productType;
    private LocalDateTime creationDate;
    private LocalDateTime finishDate;
    private long amount;
    private long trashAmount;
    private long targetAmount;
    private ReportState reportState;
    private String lineName;
    private String productName;
    private ReportWorkShift reportWorkShift;
    @JsonProperty("statistics")
    private ReportStatisticsDto reportStatisticsDto;
    @JsonProperty("downtimes")
    private List<DowntimeExecutedQueryDto> downtimeExecutedQueryDtoList;
    private List<BreakdownQueryDto> breakdowns;
}
