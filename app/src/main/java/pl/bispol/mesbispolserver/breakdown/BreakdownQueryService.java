package pl.bispol.mesbispolserver.breakdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.breakdown.dto.BreakdownQueryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreakdownQueryService {
    private final BreakdownQueryRepository breakdownQueryRepository;

    @Autowired
    BreakdownQueryService(BreakdownQueryRepository breakdownQueryRepository) {
        this.breakdownQueryRepository = breakdownQueryRepository;
    }

    List<BreakdownQueryDto> findBy(Optional<Integer> limit) {
        Pageable pageable = limit.map(l -> (Pageable) PageRequest.of(0, l)).orElse(Pageable.unpaged());
        return breakdownQueryRepository.findBy(pageable)
                .stream()
                .map(BreakdownFactory::toDto)
                .collect(Collectors.toList());
    }

    Optional<BreakdownQueryDto> findById(long id) {
        return breakdownQueryRepository.findById(id)
                .map(BreakdownFactory::toDto);
    }

    public List<Breakdown> findByActiveAndLineId(long lineId) {
        return breakdownQueryRepository.findActiveReportAndLineId(lineId);
    }

    List<BreakdownQueryDto> findByActiveAndLineIdQueryDto(long lineId) {
        return breakdownQueryRepository.findActiveReportAndLineId(lineId)
                .stream()
                .map(BreakdownFactory::toDto)
                .collect(Collectors.toList());
    }

    List<BreakdownQueryDto> findByLineId(long lineId) {
        return breakdownQueryRepository.findByLineId(lineId)
                .stream()
                .map(BreakdownFactory::toDto)
                .collect(Collectors.toList());
    }
}
