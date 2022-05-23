package pl.bispol.mesbispolserver.downtime;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
class DowntimeSuperClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Content of downtime cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    DowntimeSuperClass(String content) {
        this.content = content;
    }
}
