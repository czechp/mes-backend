package pl.bispol.mesbispolserver.quality_control.dto;

import lombok.Data;
import pl.bispol.mesbispolserver.user.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Data()
public class QualityControlQueryDto {
    List<QualityInspectionQueryDto> inspections;
    private long id;
    private LocalDateTime creationDate;
    private String lineName;
    private String inspector;
    private UserRole inspectorRole;
    private boolean qualityOK;
}
