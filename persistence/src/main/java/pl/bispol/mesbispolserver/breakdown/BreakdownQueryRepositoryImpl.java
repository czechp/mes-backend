package pl.bispol.mesbispolserver.breakdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class BreakdownQueryRepositoryImpl implements BreakdownQueryRepository {
    private final BreakdownRepository breakdownRepository;

    @Autowired
    BreakdownQueryRepositoryImpl(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    @Override
    public List<Breakdown> findBy(Pageable pageable) {
        return breakdownRepository.findAll(pageable).toList();
    }

    @Override
    public Optional<Breakdown> findById(long id) {
        return breakdownRepository.findById(id);
    }

    @Override
    public List<Breakdown> findActiveReportAndLineId(long lineId) {
        return breakdownRepository.findByBreakdownStatusNotAndLine_Id(BreakdownStatus.CLOSE, lineId);
    }

    @Override
    public List<Breakdown> findByLineId(long lineId) {
        return breakdownRepository.findByLine_IdOrderByIdDesc(lineId);
    }
}
