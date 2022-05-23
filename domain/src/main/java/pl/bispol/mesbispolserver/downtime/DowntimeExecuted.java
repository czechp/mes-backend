package pl.bispol.mesbispolserver.downtime;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.PersistenceConstructor;
import pl.bispol.mesbispolserver.report.Report;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "downtimes_executed")
@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString
public class DowntimeExecuted extends DowntimeSuperClass {
    @CreationTimestamp
    private LocalDateTime creationDate;

    private LocalDateTime closeDate;

    @NotNull(message = "Rfid number cannot be null")
    private String operatorRfid;

    @NotNull(message = "Operator name cannot be null")
    private String operatorName;

    @Enumerated(EnumType.STRING)
    private DowntimeExecutedState downtimeExecutedState = DowntimeExecutedState.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    private Report report;

    @PersistenceConstructor
    DowntimeExecuted() {
        super();
    }

    void addReport(Report report) {
        report.getDowntimeExecutedList().add(this);
        this.setReport(report);
    }

    public void close() {
        this.closeDate = LocalDateTime.now();
        this.downtimeExecutedState = DowntimeExecutedState.CLOSE;
    }

    public long countDuration() {
        LocalDateTime finishDate = this.downtimeExecutedState == DowntimeExecutedState.CLOSE ? this.closeDate : LocalDateTime.now();
        return Duration.between(this.getCreationDate(), finishDate).toMinutes();
    }
}
