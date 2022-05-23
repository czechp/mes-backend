package pl.bispol.mesbispolserver.raw_material.dto;


import lombok.Data;

@Data
public
class RawMaterialQueryDto {
    private long id;
    private long systemId;
    private String provider;
    private String name;
}
