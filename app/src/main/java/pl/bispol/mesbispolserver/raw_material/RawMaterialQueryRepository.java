package pl.bispol.mesbispolserver.raw_material;

import pl.bispol.mesbispolserver.AbstractQueryRepository;

import java.util.List;

interface RawMaterialQueryRepository extends AbstractQueryRepository<RawMaterial> {
    List<RawMaterial> findByLineId(long lineId);
}
