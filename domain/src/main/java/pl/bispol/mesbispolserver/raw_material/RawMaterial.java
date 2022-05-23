package pl.bispol.mesbispolserver.raw_material;

import lombok.NoArgsConstructor;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.superclass.RawMaterialSuperClass;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "raw_materials")
@NoArgsConstructor

public class RawMaterial extends RawMaterialSuperClass {
    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;


    RawMaterial(long systemId, String provider, String name, Line line) {
        super(systemId, provider, name);
        this.line = line;
        line.getRawMaterials().add(this);
    }

    RawMaterial(long systemId, String provider, String name) {
        super(systemId, provider, name);
    }

    void setLine(Line line) {
        this.line = line;
        line.getRawMaterials().add(this);
    }
}
