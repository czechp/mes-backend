package pl.bispol.mesbispolserver.breakdown;

import pl.bispol.mesbispolserver.AbstractQueryRepository;

import java.util.List;

interface BreakdownQueryRepository extends AbstractQueryRepository<Breakdown> {
    List<Breakdown> findActiveReportAndLineId(long lineId);

    List<Breakdown> findByLineId(long lineId);
}
