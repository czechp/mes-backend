package pl.bispol.mesbispolserver.quality_control;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.PersistenceConstructor;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.user.UserRole;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "quality_controls")
@Getter()
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode()
@ToString()
public class QualityControl {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp()
    private LocalDateTime creationDate;

    private String inspector = "";

    private UserRole inspectorRole;

    @OneToMany(mappedBy = "qualityControl", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QualityInspection> inspections = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    @PersistenceConstructor()
    QualityControl() {

    }

    QualityControl(String inspector, UserRole inspectorRole) {
        this.inspector = inspector;
        this.inspectorRole = inspectorRole;
    }

    void addQualityInspection(QualityInspection qualityInspection) {
        this.inspections.add(qualityInspection);
        qualityInspection.setQualityControl(this);
    }


}
