package pl.bispol.mesbispolserver.raw_material;

import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialCommandDto;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialQueryDto;

class RawMaterialFactory {
    static RawMaterialQueryDto toDto(RawMaterial rawMaterial) {
        RawMaterialQueryDto result = new RawMaterialQueryDto();

        result.setId(rawMaterial.getId());
        result.setSystemId(rawMaterial.getSystemId());
        result.setName(rawMaterial.getName());
        result.setProvider(rawMaterial.getProvider());
        return result;
    }

    static RawMaterial toEntity(RawMaterialCommandDto rawMaterialCommandDto) {
        RawMaterial result = new RawMaterial(rawMaterialCommandDto.getSystemId(),
                rawMaterialCommandDto.getProvider(),
                rawMaterialCommandDto.getName());

        return result;
    }
}
