package pl.bispol.mesbispolserver.used_raw_material;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UsedRawMaterialCommandRepositoryImpl implements UsedRawMaterialCommandRepository {
    private final UsedRawMaterialRepository usedRawMaterialRepository;

    UsedRawMaterialCommandRepositoryImpl(UsedRawMaterialRepository usedRawMaterialRepository) {
        this.usedRawMaterialRepository = usedRawMaterialRepository;
    }

    @Override
    public Optional<UsedRawMaterial> findById(long id) {
        return usedRawMaterialRepository.findById(id);
    }

    @Override
    public void save(UsedRawMaterial usedRawMaterial) {
        usedRawMaterialRepository.save(usedRawMaterial);
    }

    @Override
    public void deleteById(long id) {
        usedRawMaterialRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return usedRawMaterialRepository.existsById(id);
    }
}
