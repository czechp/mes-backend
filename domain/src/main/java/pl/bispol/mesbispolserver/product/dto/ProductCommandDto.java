package pl.bispol.mesbispolserver.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.bispol.mesbispolserver.product.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter()
@Setter()
public class ProductCommandDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Product type cannot by null")
    private ProductType productType;

    ProductCommandDto() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProductCommandDto that = (ProductCommandDto) o;

        return new EqualsBuilder().append(name, that.name).append(productType, that.productType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(productType).toHashCode();
    }

    @Override
    public String toString() {
        return "ProductCommandDto{" +
                "name='" + name + '\'' +
                ", productType=" + productType +
                '}';
    }
}
