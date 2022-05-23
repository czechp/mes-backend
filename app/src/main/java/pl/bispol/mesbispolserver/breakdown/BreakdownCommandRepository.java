package pl.bispol.mesbispolserver.breakdown;

import pl.bispol.mesbispolserver.AbstractCommandRepository;

import java.util.List;

interface BreakdownCommandRepository extends AbstractCommandRepository<Breakdown> {
    List<Breakdown> findByStatusNotClose();
}
