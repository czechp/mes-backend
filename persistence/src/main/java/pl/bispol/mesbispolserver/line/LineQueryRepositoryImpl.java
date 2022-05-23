package pl.bispol.mesbispolserver.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.product.ProductType;

import java.util.List;
import java.util.Optional;

@Service()
class LineQueryRepositoryImpl implements LineQueryRepository {
    private final LineRepository lineRepository;

    @Autowired()
    LineQueryRepositoryImpl(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }


    @Override
    public Optional<Line> findByName(String lineName) {
        return lineRepository.findByName(lineName);
    }

    @Override
    public List<Line> findByProductionType(ProductType productType) {
        return lineRepository.findByProductionType(productType);
    }

    @Override
    public Optional<Line> findById(final long lineId) {
        return lineRepository.findById(lineId);
    }

    @Override
    public List<Line> findAll() {
        return lineRepository.findBy();
    }

    @Override
    public Optional<Line> findByIdAndOperatorExists(long lineId) {
        return lineRepository.findByIdAndOperatorNot(lineId, "");
    }
}

