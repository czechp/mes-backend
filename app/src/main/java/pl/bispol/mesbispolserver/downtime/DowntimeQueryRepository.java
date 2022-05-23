package pl.bispol.mesbispolserver.downtime;

import pl.bispol.mesbispolserver.AbstractQueryRepository;

import java.util.List;

interface DowntimeQueryRepository extends AbstractQueryRepository<Downtime> {
    List<Downtime> findByLineId(long lineId);
}
