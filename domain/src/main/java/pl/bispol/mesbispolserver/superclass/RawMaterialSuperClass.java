package pl.bispol.mesbispolserver.superclass;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class RawMaterialSuperClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    protected long id;

    @NotNull(message = "System id cannot be null")
    protected long systemId;

    @NotNull(message = "Provider cannot be null")
    @NotBlank(message = "Provider cannot be blank")
    protected String provider;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    protected String name;


    protected RawMaterialSuperClass() {
    }

    protected RawMaterialSuperClass(long systemId, String provider, String name) {
        this.systemId = systemId;
        this.provider = provider;
        this.name = name;
    }
}
