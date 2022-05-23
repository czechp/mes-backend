package pl.bispol.mesbispolserver.downtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface DowntimeRepository extends JpaRepository<Downtime, Long> {
    List<Downtime> findByLine_Id(long lineId);

}
