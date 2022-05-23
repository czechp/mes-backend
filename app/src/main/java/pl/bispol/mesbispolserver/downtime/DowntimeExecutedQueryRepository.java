package pl.bispol.mesbispolserver.downtime;

import pl.bispol.mesbispolserver.AbstractQueryRepository;

import java.util.List;
import java.util.Optional;

interface DowntimeExecutedQueryRepository extends AbstractQueryRepository<DowntimeExecuted> {
    Optional<DowntimeExecuted> findByLineIdAndStatusOpen(long lineId);

    List<DowntimeExecuted> findByLineId(long lineId);
}
