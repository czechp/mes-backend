package pl.bispol.mesbispolserver.used_raw_material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsedRawMaterialCommandDto {
    @NotNull(message = "System id cannot be null")
    private long systemId;
    @NotNull(message = "Provider cannot be null")
    private String provider;
    @NotNull(message = "Name cannot be null")
    private String name;
    private String partNr;
    private String date;
    @NotNull(message = "Line id cannot be null")
    private long lineId;
}
