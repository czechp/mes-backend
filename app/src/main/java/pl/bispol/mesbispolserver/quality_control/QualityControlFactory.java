package pl.bispol.mesbispolserver.quality_control;

import pl.bispol.mesbispolserver.quality_control.dto.QualityControlEmailMsgDto;
import pl.bispol.mesbispolserver.quality_control.dto.QualityControlQueryDto;

import java.util.stream.Collectors;

public class QualityControlFactory {
    public static QualityControlQueryDto toQueryDto(QualityControl qualityControl) {
        QualityControlQueryDto result = new QualityControlQueryDto();
        result.setId(qualityControl.getId());
        result.setCreationDate(qualityControl.getCreationDate());
        result.setInspector(qualityControl.getInspector());
        result.setInspectorRole(qualityControl.getInspectorRole());
        result.setInspections(
                qualityControl.getInspections()
                        .stream()
                        .map(QualityInspectionFactory::toQueryDto)
                        .collect(Collectors.toList())
        );
        result.setQualityOK(qualityControl.getInspections().stream().allMatch(QualityInspection::isQualityOK));
        result.setLineName(qualityControl.getReport().getLine().getName());
        return result;
    }

    public static QualityControlEmailMsgDto toEmailMsg(QualityControl qualityControl) {
        QualityControlEmailMsgDto qualityControlEmailMsgDto = new QualityControlEmailMsgDto();
        qualityControlEmailMsgDto.setId(qualityControl.getId());
        qualityControlEmailMsgDto.setInspector(qualityControl.getInspector());
        qualityControlEmailMsgDto.setUserRole(qualityControl.getInspectorRole());
        qualityControlEmailMsgDto.setProductType(qualityControl.getReport().getLine().getProductionType());
        qualityControlEmailMsgDto.setLineName(qualityControl.getReport().getLine().getName());
        qualityControlEmailMsgDto.setCreationDate(qualityControl.getCreationDate());
        qualityControlEmailMsgDto.setMessage(
                "<br>" +
                        qualityControl.getInspections()
                                .stream()
                                .filter(q -> !q.isQualityOK())
                                .map(QualityInspection::getContent)
                                .collect(Collectors.joining("<br>"))
        );
        return qualityControlEmailMsgDto;
    }
}
