package pl.bispol.mesbispolserver.downtime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class DowntimeExecutedQueryRepositoryImpl implements DowntimeExecutedQueryRepository {
    private final DowntimeExecutedRepository downtimeExecutedRepository;

    @Autowired
    DowntimeExecutedQueryRepositoryImpl(DowntimeExecutedRepository downtimeExecutedRepository) {
        this.downtimeExecutedRepository = downtimeExecutedRepository;
    }

    @Override
    public List<DowntimeExecuted> findBy(Pageable pageable) {
        return downtimeExecutedRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<DowntimeExecuted> findById(long id) {
        return downtimeExecutedRepository.findById(id);
    }

    @Override
    public Optional<DowntimeExecuted> findByLineIdAndStatusOpen(long lineId) {
        return downtimeExecutedRepository.findByDowntimeExecutedStateAndReport_Line_Id(DowntimeExecutedState.OPEN, lineId);
    }

    @Override
    public List<DowntimeExecuted> findByLineId(long lineId) {
        return downtimeExecutedRepository.findByReport_Line_Id(lineId);
    }
}
