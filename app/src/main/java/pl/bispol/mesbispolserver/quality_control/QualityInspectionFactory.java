package pl.bispol.mesbispolserver.quality_control;

import pl.bispol.mesbispolserver.quality_control.dto.QualityInspectionCommandDto;
import pl.bispol.mesbispolserver.quality_control.dto.QualityInspectionQueryDto;

class QualityInspectionFactory {
    static QualityInspection toEntity(QualityInspectionCommandDto dto) {
        QualityInspection result = new QualityInspection();
        result.setContent(dto.getContent());
        result.setQualityOK(dto.isQualityOK());

        return result;
    }

    static QualityInspectionQueryDto toQueryDto(QualityInspection qualityInspection) {
        QualityInspectionQueryDto result = new QualityInspectionQueryDto();
        result.setId(qualityInspection.getId());
        result.setContent(qualityInspection.getContent());
        result.setQualityOK(qualityInspection.isQualityOK());
        return result;
    }
}
