package pl.bispol.mesbispolserver.used_raw_material;

import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.report.Report;
import pl.bispol.mesbispolserver.report.ReportQueryService;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialCommandDto;

import javax.transaction.Transactional;

@Service
class UsedRawMaterialCommandService {
    private final UsedRawMaterialCommandRepository usedRawMaterialCommandRepository;
    private final LineQueryService lineQueryService;
    private final ReportQueryService reportQueryService;

    UsedRawMaterialCommandService(UsedRawMaterialCommandRepository usedRawMaterialCommandRepository, LineQueryService lineQueryService, ReportQueryService reportQueryService) {
        this.usedRawMaterialCommandRepository = usedRawMaterialCommandRepository;
        this.lineQueryService = lineQueryService;
        this.reportQueryService = reportQueryService;
    }


    void deleteById(long id) {
        if (usedRawMaterialCommandRepository.existsById(id))
            usedRawMaterialCommandRepository.deleteById(id);
        else throw new NotFoundException(Statements.RAW_MATERIAL_NOT_FOUND);
    }

    @Transactional
    void save(UsedRawMaterialCommandDto dto) {
        lineQueryService.findCurrentOperatorByLineId(dto.getLineId())
                .orElseThrow(() -> new BadRequestException(Statements.OPERATOR_NOT_EXISTS));
        Report report = reportQueryService.findActiveByLineId(dto.getLineId())
                .orElseThrow(() -> new BadRequestException(Statements.OPEN_REPORT_DOES_NOT_EXIST));
        UsedRawMaterial usedRawMaterial = UsedRawMaterialFactory.toEntity(dto, report);
        usedRawMaterialCommandRepository.save(usedRawMaterial);
    }
}
