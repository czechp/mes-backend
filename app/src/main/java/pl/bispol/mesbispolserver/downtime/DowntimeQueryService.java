package pl.bispol.mesbispolserver.downtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DowntimeQueryService {
    private final DowntimeQueryRepository downtimeQueryRepository;

    @Autowired
    DowntimeQueryService(DowntimeQueryRepository downtimeQueryRepository) {
        this.downtimeQueryRepository = downtimeQueryRepository;
    }

    List<DowntimeQueryDto> findBy() {
        return downtimeQueryRepository.findBy(Pageable.unpaged())
                .stream().map(DowntimeFactory::toDto)
                .collect(Collectors.toList());
    }

    Optional<DowntimeQueryDto> findById(long id) {
        return downtimeQueryRepository.findById(id)
                .map(DowntimeFactory::toDto);
    }

    List<DowntimeQueryDto> findByLineId(long lineId) {
        return downtimeQueryRepository.findByLineId(lineId)
                .stream()
                .map(DowntimeFactory::toDto)
                .collect(Collectors.toList());
    }
}
