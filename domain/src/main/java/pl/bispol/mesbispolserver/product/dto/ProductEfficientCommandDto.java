package pl.bispol.mesbispolserver.product.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Getter()
@Setter()
@ToString()
@EqualsAndHashCode()
public class ProductEfficientCommandDto {
    @Length(min = 3, max = 4)
    private String lineName;

    @Min(1)
    private long amount;
}
