package pl.bispol.mesbispolserver.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "products")
@Getter()
@Setter(AccessLevel.PACKAGE)
public class Product {

    @Enumerated(EnumType.STRING)
    ProductType productType;

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductProperty> productProperties = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductEfficient> productEfficients = new ArrayList<>();


    @PersistenceConstructor()
    Product() {
    }

    Product(@NotBlank(message = "Product name cannot be blank") final String name, final ProductType productType) {
        this.name = name;
        this.productType = productType;
    }

    void addProductEfficient(ProductEfficient productEfficient) {
        this.productEfficients.add(productEfficient);
        productEfficient.addProduct(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return new EqualsBuilder().append(id, product.id).append(name, product.name).append(productType, product.productType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(productType).toHashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType=" + productType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", productProperties=" + productProperties +
                ", productEfficients=" + productEfficients +
                '}';
    }

    void addProperty(final ProductProperty productProperty) {
        productProperty.setProduct(this);
        ;
        this.productProperties.add(productProperty);
    }
}
