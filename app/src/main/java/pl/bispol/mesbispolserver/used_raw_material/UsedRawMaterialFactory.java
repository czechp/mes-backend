package pl.bispol.mesbispolserver.used_raw_material;


import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialCommandDto;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialQueryDto;

public class UsedRawMaterialFactory {
    public static UsedRawMaterial toEntity(UsedRawMaterialCommandDto dto, Report report) {
        return new UsedRawMaterial(dto.getSystemId(),
                dto.getProvider(),
                dto.getName(),
                dto.getPartNr(),
                dto.getDate(),
                report);
    }

    public static UsedRawMaterialQueryDto toDto(UsedRawMaterial usedRawMaterial) {
        return new UsedRawMaterialQueryDto(usedRawMaterial.getId(),
                usedRawMaterial.getSystemId(),
                usedRawMaterial.getProvider(),
                usedRawMaterial.getName(),
                usedRawMaterial.getPartNr(),
                usedRawMaterial.getDate(),
                usedRawMaterial.getReport().getId());
    }
}
