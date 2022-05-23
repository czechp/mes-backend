package pl.bispol.mesbispolserver.report;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.PersistenceConstructor;
import pl.bispol.mesbispolserver.breakdown.Breakdown;
import pl.bispol.mesbispolserver.downtime.DowntimeExecuted;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.quality_control.QualityControl;
import pl.bispol.mesbispolserver.used_raw_material.UsedRawMaterial;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "reports")
@Getter()
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode()
@ToString()
public class Report {
    @Enumerated(EnumType.STRING)
    ReportState reportState = ReportState.OPEN;

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    private String productName;

    private long startCounter = 0;

    private String createOperator;

    private String finishOperator = "";

    private ReportWorkShift reportWorkShift;

    @CreationTimestamp()
    private LocalDateTime creationDate;

    private LocalDateTime finishDate;

    private long amount;

    private long trashAmount;

    private long targetAmount;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QualityControl> qualityControls = new ArrayList<>();

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DowntimeExecuted> downtimeExecutedList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Breakdown> breakdowns = new ArrayList<>();

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UsedRawMaterial> materials = new ArrayList<>();

    @PersistenceConstructor()
    public Report() {
    }

    Report(Line line, long targetAmount, long startCounter, ReportWorkShift reportWorkShift) {
        this.line = line;
        this.createOperator = line.getOperator();
        this.targetAmount = targetAmount;
        this.startCounter = line.getCurrentCounter();
        this.productName = line.getProductName();
        this.startCounter = startCounter;
        this.reportWorkShift = reportWorkShift;
        this.creationDate = LocalDateTime.now();
    }

    public void setLine(Line line) {
        this.line = line;
    }

    void addBreakdown(Breakdown breakdown) {
        this.breakdowns.add(breakdown);
        breakdown.getReports().add(this);
    }
}
