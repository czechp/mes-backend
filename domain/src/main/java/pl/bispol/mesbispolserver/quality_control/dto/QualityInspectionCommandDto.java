package pl.bispol.mesbispolserver.quality_control.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data()
public class QualityInspectionCommandDto {
    @Length(min = 5, max = 50)
    private String content;
    @NotNull()
    private boolean qualityOK;
}
