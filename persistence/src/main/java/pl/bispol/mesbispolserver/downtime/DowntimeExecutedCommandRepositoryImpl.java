package pl.bispol.mesbispolserver.downtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.line.WorkingHours;

import java.util.List;
import java.util.Optional;

@Service
class DowntimeExecutedCommandRepositoryImpl implements DowntimeExecutedCommandRepository {
    private final DowntimeExecutedRepository downtimeExecutedRepository;

    @Autowired
    DowntimeExecutedCommandRepositoryImpl(DowntimeExecutedRepository downtimeExecutedRepository) {
        this.downtimeExecutedRepository = downtimeExecutedRepository;
    }

    @Override
    public Optional<DowntimeExecuted> findById(long id) {
        return downtimeExecutedRepository.findById(id);
    }

    @Override
    public void save(DowntimeExecuted downtimeExecuted) {
        downtimeExecutedRepository.save(downtimeExecuted);
    }

    @Override
    public void deleteById(long id) {
        downtimeExecutedRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return downtimeExecutedRepository.existsById(id);
    }

    @Override
    public Optional<DowntimeExecuted> findByIdAndOpenState(long downtimeId) {
        return downtimeExecutedRepository.findByIdAndDowntimeExecutedState(downtimeId, DowntimeExecutedState.OPEN);
    }

    @Override
    public List<DowntimeExecuted> findByOpenStateAndWorkingHours(WorkingHours workingHours) {
        return downtimeExecutedRepository.findByDowntimeExecutedStateAndReport_Line_WorkingHours(DowntimeExecutedState.OPEN, workingHours);
    }
}
