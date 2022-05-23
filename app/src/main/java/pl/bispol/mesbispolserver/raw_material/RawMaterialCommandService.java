package pl.bispol.mesbispolserver.raw_material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.Line;
import pl.bispol.mesbispolserver.line.LineQueryService;
import pl.bispol.mesbispolserver.raw_material.dto.RawMaterialCommandDto;

import javax.transaction.Transactional;

@Service
class RawMaterialCommandService {
    private final RawMaterialCommandRepository rawMaterialCommandRepository;
    private final LineQueryService lineQueryService;


    @Autowired
    RawMaterialCommandService(RawMaterialCommandRepository rawMaterialCommandRepository, LineQueryService lineQueryService) {
        this.rawMaterialCommandRepository = rawMaterialCommandRepository;
        this.lineQueryService = lineQueryService;
    }


    void deleteById(long id) {
        if (rawMaterialCommandRepository.existsById(id))
            rawMaterialCommandRepository.deleteById(id);
        else
            throw new NotFoundException(Statements.RAW_MATERIAL_NOT_FOUND);
    }

    @Transactional
    public void save(RawMaterialCommandDto rawMaterialCommandDto) {
        final Line line = lineQueryService.findById(rawMaterialCommandDto.getLineId())
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
        final RawMaterial rawMaterial = RawMaterialFactory.toEntity(rawMaterialCommandDto);
        rawMaterial.setLine(line);
        rawMaterialCommandRepository.save(rawMaterial);
    }
}
