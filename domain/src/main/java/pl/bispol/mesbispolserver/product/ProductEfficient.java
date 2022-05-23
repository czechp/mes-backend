package pl.bispol.mesbispolserver.product;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity()
@Table(name = "product_efficients")
@Getter()
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode()
public class ProductEfficient {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull()
    @Length(min = 3, max = 4)
    private String lineName;

    @Min(1)
    private long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


    @PersistenceConstructor()
    public ProductEfficient() {
    }

    public ProductEfficient(String lineName, long amount) {
        this.lineName = lineName;
        this.amount = amount;
    }

    void addProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductEfficient{" +
                "id=" + id +
                ", lineName='" + lineName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
