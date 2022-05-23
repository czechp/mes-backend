package pl.bispol.mesbispolserver.quality_control.dto;

import lombok.Data;

@Data()
public
class QualityInspectionQueryDto {
    private long id;
    private String content;
    private boolean qualityOK;
}
