package pl.bispol.mesbispolserver.line.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import pl.bispol.mesbispolserver.line.WorkingHours;
import pl.bispol.mesbispolserver.product.ProductType;

@Getter()
@Setter()
public class LineCommandDto {
    @Length(min = 4, max = 4, message = "Line name has to have 4 characters")
    private String name;

    private ProductType productionType;

    private WorkingHours workingHours;

    LineCommandDto() {
    }

    LineCommandDto(String name, ProductType productionType, WorkingHours workingHours) {
        this.name = name;
        this.productionType = productionType;
        this.workingHours = workingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LineCommandDto that = (LineCommandDto) o;

        return new EqualsBuilder().append(name, that.name).append(productionType, that.productionType).append(workingHours, that.workingHours).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(productionType).append(workingHours).toHashCode();
    }

    @Override
    public String toString() {
        return "LineCommandDto{" +
                "name='" + name + '\'' +
                ", productionType=" + productionType +
                ", workingHours=" + workingHours +
                '}';
    }

}
