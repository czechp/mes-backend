package pl.bispol.mesbispolserver.line;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.PersistenceConstructor;
import pl.bispol.mesbispolserver.breakdown.Breakdown;
import pl.bispol.mesbispolserver.downtime.Downtime;
import pl.bispol.mesbispolserver.product.ProductType;
import pl.bispol.mesbispolserver.raw_material.RawMaterial;
import pl.bispol.mesbispolserver.report.Report;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "production_lines")
@Getter()
@Setter(AccessLevel.PACKAGE)
public class Line {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(min = 4, max = 4, message = "Line name has to have 3 characters")
    private String name;

    private String productName = "";

    private String operator = "";

    private String operatorRfid = "";

    private long currentCounter;

    @NotNull()
    @Enumerated(EnumType.STRING)
    private ProductType productionType;

    @NotNull()
    @Enumerated(EnumType.STRING)
    private WorkingHours workingHours;

    @NotNull()
    @Enumerated(EnumType.STRING)
    private LineStatus lineStatus;

    private boolean rfidReaderError;

    private boolean opcUaCommunicationError;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Report> reports = new ArrayList();

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Downtime> downtimes = new ArrayList();

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Breakdown> breakdowns = new ArrayList<>();

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RawMaterial> rawMaterials = new ArrayList<>();

    @PersistenceConstructor()
    public Line() {
    }

    Line(String name, ProductType productionType, WorkingHours workingHours) {
        this.name = name;
        this.productionType = productionType;
        this.workingHours = workingHours;
        this.lineStatus = LineStatus.DEACTIVATED;
        this.operator = null;
        this.productName = null;
        this.rfidReaderError = true;
        this.opcUaCommunicationError = true;
    }

    public void setBreakdownState() {
        this.lineStatus = LineStatus.BREAKDOWN;
    }

    public void setTurnOffState() {
        this.lineStatus = LineStatus.DEACTIVATED;
    }

    public void addReport(Report report) {
        this.reports.add(report);
        report.setLine(this);
    }

    public void addDownTime(Downtime downTime) {
        this.downtimes.add(downTime);
        downTime.addLine(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        return new EqualsBuilder().append(id, line.id).append(currentCounter, line.currentCounter).append(rfidReaderError, line.rfidReaderError).append(opcUaCommunicationError, line.opcUaCommunicationError).append(name, line.name).append(productName, line.productName).append(operator, line.operator).append(productionType, line.productionType).append(workingHours, line.workingHours).append(lineStatus, line.lineStatus).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(productName).append(operator).append(currentCounter).append(productionType).append(workingHours).append(lineStatus).append(rfidReaderError).append(opcUaCommunicationError).toHashCode();
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productName='" + productName + '\'' +
                ", operator='" + operator + '\'' +
                ", currentCounter=" + currentCounter +
                ", productionType=" + productionType +
                ", workingHours=" + workingHours +
                ", lineStatus=" + lineStatus +
                ", rfidReaderError=" + rfidReaderError +
                ", opcUaCommunicationError=" + opcUaCommunicationError +
                '}';
    }

}
