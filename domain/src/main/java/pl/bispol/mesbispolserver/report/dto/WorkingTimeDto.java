package pl.bispol.mesbispolserver.report.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter()
@Setter()
@ToString()
@EqualsAndHashCode()
public class WorkingTimeDto {
    private long totalMinutes;
    private long hours;
    private long minutes;
    private float hoursDecimal;

    public WorkingTimeDto(long totalMinutes) {
        this.totalMinutes = totalMinutes;
        this.minutes = totalMinutes % 60;
        this.hours = totalMinutes / 60;
        this.hoursDecimal = this.hours + (float) this.minutes / 60;
    }
}
