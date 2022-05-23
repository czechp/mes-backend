package pl.bispol.mesbispolserver.used_raw_material;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.used_raw_material.dto.UsedRawMaterialQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsedRawMaterialQueryService {
    private final UsedRawMaterialQueryRepository usedRawMaterialQueryRepository;

    UsedRawMaterialQueryService(UsedRawMaterialQueryRepository usedRawMaterialQueryRepository) {
        this.usedRawMaterialQueryRepository = usedRawMaterialQueryRepository;
    }

    List<UsedRawMaterialQueryDto> findByWithLimit(Optional<Integer> limit) {
        final Pageable pageable = limit.map(l -> (Pageable) PageRequest.of(0, l)).orElse(Pageable.unpaged());
        return usedRawMaterialQueryRepository
                .findBy(pageable)
                .stream()
                .map(UsedRawMaterialFactory::toDto)
                .collect(Collectors.toList());
    }


    Optional<UsedRawMaterialQueryDto> findById(long id) {
        return usedRawMaterialQueryRepository.findById(id)
                .map(UsedRawMaterialFactory::toDto);
    }
}
