package pl.bispol.mesbispolserver.raw_material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class RawMaterialCommandRepositoryImpl implements RawMaterialCommandRepository {
    private final RawMaterialRepository rawMaterialRepository;

    @Autowired
    RawMaterialCommandRepositoryImpl(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public Optional<RawMaterial> findById(long id) {
        return rawMaterialRepository.findById(id);
    }

    @Override
    public void save(RawMaterial rawMaterial) {
        rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public void deleteById(long id) {
        rawMaterialRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return rawMaterialRepository.existsById(id);
    }
}
