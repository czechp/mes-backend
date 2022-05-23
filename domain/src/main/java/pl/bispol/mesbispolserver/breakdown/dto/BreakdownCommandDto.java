package pl.bispol.mesbispolserver.breakdown.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BreakdownCommandDto {
    @Length(min = 5, max = 1000)
    private String content;
}
