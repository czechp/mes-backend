package pl.bispol.mesbispolserver.raw_material.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialCommandDto {
    @NotNull(message = "Line id cannot be null")
    private long lineId;

    @NotNull(message = "System id cannot be null")
    private long systemId;

    @NotNull(message = "Provider cannot be null")
    private String provider;

    @NotNull(message = "Name cannot be null")
    private String name;
}
