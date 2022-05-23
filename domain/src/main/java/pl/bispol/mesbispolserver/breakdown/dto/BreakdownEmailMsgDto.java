package pl.bispol.mesbispolserver.breakdown.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakdownEmailMsgDto {
    private String line;
    private String content;
    private LocalDateTime creationDate;
    private String breakdownType;
    private String applicant;
}
