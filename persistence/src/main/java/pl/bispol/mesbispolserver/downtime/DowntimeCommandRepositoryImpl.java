package pl.bispol.mesbispolserver.downtime;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class DowntimeCommandRepositoryImpl implements DowntimeCommandRepository {
    private final DowntimeRepository downtimeRepository;

    DowntimeCommandRepositoryImpl(DowntimeRepository downtimeRepository) {
        this.downtimeRepository = downtimeRepository;
    }


    @Override
    public Optional<Downtime> findById(long id) {
        return downtimeRepository.findById(id);
    }

    @Override
    public void save(Downtime downtime) {
        downtimeRepository.save(downtime);
    }

    @Override
    public void deleteById(long id) {
        downtimeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return downtimeRepository.existsById(id);
    }
}
