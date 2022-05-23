package pl.bispol.mesbispolserver.raw_material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    List<RawMaterial> findByLine_Id(long lineId);
}
