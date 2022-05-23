package pl.bispol.mesbispolserver.downtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DowntimeQueryDto {
    private long id;
    private String content;
    private String lineName;

}
