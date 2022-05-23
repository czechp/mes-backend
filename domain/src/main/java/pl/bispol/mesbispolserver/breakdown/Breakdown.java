package pl.bispol.mesbispolserver.breakdown;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.PersistenceConstructor;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.report.Report;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "breakdowns")
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
public class Breakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private BreakdownStatus breakdownStatus = BreakdownStatus.NEW;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    private String operatorRfid = "";

    private String operatorName = "";

    private String maintenanceRfid = "";

    private String maintenanceName = "";

    private String umupNumber = "";

    private LocalDateTime maintenanceArrivedTime;

    private boolean notificationSent;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private LocalDateTime closeDate;

    @ManyToMany(mappedBy = "breakdowns", fetch = FetchType.LAZY)
    private List<Report> reports = new ArrayList<>();

    @PersistenceConstructor
    Breakdown() {
    }

    Breakdown(String content, Line line, String operatorRfid, String operatorName) {
        this.content = content;
        this.line = line;
        this.operatorRfid = operatorRfid;
        this.operatorName = operatorName;
    }

    public void inProgress(String maintenanceRfid, String maintenanceName, String umupNumber) {
        this.maintenanceRfid = maintenanceRfid;
        this.maintenanceName = maintenanceName;
        this.breakdownStatus = BreakdownStatus.IN_PROGRESS;
        this.umupNumber = umupNumber;
        this.maintenanceArrivedTime = LocalDateTime.now();
    }

    public void close() {
        this.breakdownStatus = BreakdownStatus.CLOSE;
        this.closeDate = LocalDateTime.now();
        this.line.setTurnOffState();
    }

    void addReport(Report report) {
        this.reports.add(report);
        report.getBreakdowns().add(this);
    }

    void clearReports() {
        this.reports.forEach(r -> r.getBreakdowns().remove(this));
    }
}
