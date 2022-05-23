package pl.bispol.mesbispolserver.quality_control.dto;

import lombok.Data;
import pl.bispol.mesbispolserver.product.ProductType;
import pl.bispol.mesbispolserver.user.UserRole;

import java.time.LocalDateTime;

@Data
public class QualityControlEmailMsgDto {
    private long id;
    private String inspector;
    private UserRole userRole;
    private String message;
    private LocalDateTime creationDate;
    private ProductType productType;
    private String lineName;
}
