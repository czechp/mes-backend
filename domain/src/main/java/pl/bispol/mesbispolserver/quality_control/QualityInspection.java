package pl.bispol.mesbispolserver.quality_control;

import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;

@Entity()
@Table(name = "quality_inspections")
@Getter()
@Setter(AccessLevel.PACKAGE)
@ToString()
@EqualsAndHashCode()
public class QualityInspection {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content = "";

    private boolean qualityOK;

    @ManyToOne(fetch = FetchType.LAZY)
    private QualityControl qualityControl;

    @PersistenceConstructor()
    QualityInspection() {
    }

    QualityInspection(String content, boolean qualityOK) {
        this.content = content;
        this.qualityOK = qualityOK;
    }
}
