package pl.bispol.mesbispolserver.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity()
@Table(name = "product_properties")
@Getter()
@Setter(AccessLevel.PACKAGE)
class ProductProperty {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Product property cannot be blank")
    @Length(min = 3, max = 50, message = "Length of product property has to be between 3 and 50 characters")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @PersistenceConstructor()
    ProductProperty() {
    }

    ProductProperty(final String content) {
        this.content = content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProductProperty that = (ProductProperty) o;

        return new EqualsBuilder().append(id, that.id).append(content, that.content).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(content).toHashCode();
    }

    @Override
    public String toString() {
        return "ProductProperty{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
