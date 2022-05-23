package pl.bispol.mesbispolserver.used_raw_material;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class UsedRawMaterialQueryRepositoryImpl implements UsedRawMaterialQueryRepository {
    private final UsedRawMaterialRepository usedRawMaterialRepository;

    UsedRawMaterialQueryRepositoryImpl(UsedRawMaterialRepository usedRawMaterialRepository) {
        this.usedRawMaterialRepository = usedRawMaterialRepository;
    }

    @Override
    public Optional<UsedRawMaterial> findById(long id) {
        return usedRawMaterialRepository.findById(id);
    }

    @Override
    public List<UsedRawMaterial> findBy(Pageable pageable) {
        return usedRawMaterialRepository.findAll(pageable).toList();
    }
}
