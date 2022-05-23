package pl.bispol.mesbispolserver.downtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bispol.mesbispolserver.line.WorkingHours;

import java.util.List;
import java.util.Optional;

@Repository
interface DowntimeExecutedRepository extends JpaRepository<DowntimeExecuted, Long> {
    Optional<DowntimeExecuted> findByIdAndDowntimeExecutedState(long downtimeId, DowntimeExecutedState state);

    Optional<DowntimeExecuted> findByDowntimeExecutedStateAndReport_Line_Id(DowntimeExecutedState open, long lineId);

    List<DowntimeExecuted> findByOrderByIdDesc();

    List<DowntimeExecuted> findByReport_Line_Id(long lineId);

    List<DowntimeExecuted> findByDowntimeExecutedStateAndReport_Line_WorkingHours(DowntimeExecutedState downtimeExecutedState, WorkingHours workingHours);
}
