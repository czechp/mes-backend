package pl.bispol.mesbispolserver.downtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.downtime.dto.DowntimeExecutedQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class DowntimeExecutedQueryService {
    private final DowntimeExecutedQueryRepository downtimeExecutedQueryRepository;

    @Autowired
    DowntimeExecutedQueryService(DowntimeExecutedQueryRepository downtimeExecutedQueryRepository) {
        this.downtimeExecutedQueryRepository = downtimeExecutedQueryRepository;
    }

    List<DowntimeExecutedQueryDto> findBy() {
        return downtimeExecutedQueryRepository.findBy(Pageable.unpaged())
                .stream()
                .map(DowntimeExecutedFactory::toDto)
                .collect(Collectors.toList());
    }

    Optional<DowntimeExecutedQueryDto> findById(long id) {
        return downtimeExecutedQueryRepository.findById(id)
                .map(DowntimeExecutedFactory::toDto);
    }

    Optional<DowntimeExecutedQueryDto> findByLineIdAndOpenStatus(long lineId) {
        return downtimeExecutedQueryRepository.findByLineIdAndStatusOpen(lineId)
                .map(DowntimeExecutedFactory::toDto);
    }

    List<DowntimeExecutedQueryDto> findByLineId(long lineId) {
        return downtimeExecutedQueryRepository.findByLineId(lineId)
                .stream()
                .map(DowntimeExecutedFactory::toDto)
                .collect(Collectors.toList());
    }
}
