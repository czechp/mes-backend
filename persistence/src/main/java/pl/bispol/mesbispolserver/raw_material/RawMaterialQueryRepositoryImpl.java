package pl.bispol.mesbispolserver.raw_material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class RawMaterialQueryRepositoryImpl implements RawMaterialQueryRepository {
    private final RawMaterialRepository rawMaterialRepository;

    @Autowired
    RawMaterialQueryRepositoryImpl(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public List<RawMaterial> findBy(Pageable pageable) {
        return rawMaterialRepository.findAll(pageable).toList();
    }

    @Override
    public Optional<RawMaterial> findById(long id) {
        return rawMaterialRepository.findById(id);
    }

    @Override
    public List<RawMaterial> findByLineId(long lineId) {
        return rawMaterialRepository.findByLine_Id(lineId);
    }
}
