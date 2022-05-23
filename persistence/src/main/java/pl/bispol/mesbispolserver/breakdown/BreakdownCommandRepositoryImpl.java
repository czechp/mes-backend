package pl.bispol.mesbispolserver.breakdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class BreakdownCommandRepositoryImpl implements BreakdownCommandRepository {
    private final BreakdownRepository breakdownRepository;

    @Autowired
    BreakdownCommandRepositoryImpl(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    @Override
    public Optional<Breakdown> findById(long id) {
        return breakdownRepository.findById(id);
    }

    @Override
    public void save(Breakdown breakdown) {
        breakdownRepository.save(breakdown);
    }

    @Override
    public void deleteById(long id) {
        breakdownRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return breakdownRepository.existsById(id);
    }

    @Override
    public List<Breakdown> findByStatusNotClose() {
        return breakdownRepository.findByBreakdownStatusNot(BreakdownStatus.CLOSE);
    }
}
