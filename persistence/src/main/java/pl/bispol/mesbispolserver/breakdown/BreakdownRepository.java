package pl.bispol.mesbispolserver.breakdown;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BreakdownRepository extends JpaRepository<Breakdown, Long> {
    List<Breakdown> findByBreakdownStatusNot(BreakdownStatus breakdownStatus);

    List<Breakdown> findByBreakdownStatusNotAndLine_Id(BreakdownStatus close, long lineId);

    List<Breakdown> findByLine_IdOrderByIdDesc(long lineId);
}
