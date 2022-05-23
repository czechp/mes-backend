package pl.bispol.mesbispolserver.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service()
class LineCommandRepositoryImpl implements LineCommandRepository {
    private final LineRepository lineRepository;

    @Autowired()
    LineCommandRepositoryImpl(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Override
    public Optional<Line> findById(long lineId) {
        return lineRepository.findById(lineId);
    }

    @Override
    public Line save(Line line) {
        return lineRepository.save(line);
    }

    @Override
    public void deleteById(long lineId) {
        lineRepository.deleteById(lineId);
    }

    @Override
    public boolean existsById(long lineId) {
        return lineRepository.existsById(lineId);
    }
}
