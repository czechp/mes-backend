package pl.bispol.mesbispolserver.downtime;

import pl.bispol.mesbispolserver.AbstractCommandRepository;
import pl.bispol.mesbispolserver.line.WorkingHours;

import java.util.List;
import java.util.Optional;

interface DowntimeExecutedCommandRepository extends AbstractCommandRepository<DowntimeExecuted> {
    Optional<DowntimeExecuted> findByIdAndOpenState(long downtimeId);

    List<DowntimeExecuted> findByOpenStateAndWorkingHours(WorkingHours workingHours);
}
