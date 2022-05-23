package pl.bispol.mesbispolserver.downtime;

import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;
import pl.bispol.mesbispolserver.line.Line;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "downtimes")
@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString
public class Downtime extends DowntimeSuperClass {
    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    @PersistenceConstructor
    Downtime() {
    }

    Downtime(String content, Line line) {
        super(content);
        this.line = line;
    }

    public void addLine(Line line) {
        this.line = line;
    }
}
