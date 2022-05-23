package pl.bispol.mesbispolserver.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter()
@Setter()
public class ProductPropertyCommandDto {

    @NotBlank(message = "Product property cannot be blank")
    @Length(min = 3, max = 50, message = "Length of product property has to be between 3 and 50 characters")
    private String content;

    ProductPropertyCommandDto() {
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProductPropertyCommandDto that = (ProductPropertyCommandDto) o;

        return new EqualsBuilder().append(content, that.content).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(content).toHashCode();
    }

    @Override
    public String toString() {
        return "ProductPropertyCommandDto{" +
                "content='" + content + '\'' +
                '}';
    }
}
