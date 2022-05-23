package pl.bispol.mesbispolserver.downtime;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class DowntimeQueryRepositoryImpl implements DowntimeQueryRepository {
    private final DowntimeRepository downtimeRepository;

    DowntimeQueryRepositoryImpl(DowntimeRepository downtimeRepository) {
        this.downtimeRepository = downtimeRepository;
    }


    @Override
    public List<Downtime> findBy(Pageable pageable) {
        return downtimeRepository.findAll();
    }

    @Override
    public Optional<Downtime> findById(long id) {
        return downtimeRepository.findById(id);
    }

    @Override
    public List<Downtime> findByLineId(long lineId) {
        return downtimeRepository.findByLine_Id(lineId);
    }
}
