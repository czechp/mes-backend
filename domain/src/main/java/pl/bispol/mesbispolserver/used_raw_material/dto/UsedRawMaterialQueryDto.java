package pl.bispol.mesbispolserver.used_raw_material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsedRawMaterialQueryDto {
    private long id;
    private long systemId;
    private String provider;
    private String name;
    private String partNr;
    private String date;
    private long reportId;
}
