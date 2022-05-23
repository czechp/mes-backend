package pl.bispol.mesbispolserver.used_raw_material;

import lombok.*;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.superclass.RawMaterialSuperClass;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "used_raw_materials")
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UsedRawMaterial extends RawMaterialSuperClass {
    private String partNr = "";
    private String date = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Report cannot be null")
    private Report report;

    UsedRawMaterial(long systemId, String provider, String name, Report report) {
        super(systemId, provider, name);
        this.report = report;
        report.getMaterials().add(this);
    }

    UsedRawMaterial(long systemId, String provider, String name, String partNr, String date, Report report) {
        super(systemId, provider, name);
        this.partNr = partNr;
        this.date = date;
        this.report = report;
        report.getMaterials().add(this);
    }
}
