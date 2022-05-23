package pl.bispol.mesbispolserver.used_raw_material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UsedRawMaterialRepository extends JpaRepository<UsedRawMaterial, Long> {
}
