package pl.bispol.mesbispolserver.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.line.dto.LineQueryDto;
import pl.bispol.mesbispolserver.product.ProductType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class LineQueryService {
    private final LineQueryRepository lineQueryRepository;

    @Autowired()
    LineQueryService(LineQueryRepository lineQueryRepository) {
        this.lineQueryRepository = lineQueryRepository;
    }

    public List<LineQueryDto> findAllBasicDto() {
        return lineQueryRepository.findAll()
                .stream()
                .map(LineFactory::toQueryDto)
                .collect(Collectors.toList());
    }

    public Optional<LineQueryDto> findByIdBasicDto(long lineId) {
        return lineQueryRepository.findById(lineId)
                .map(LineFactory::toQueryDto);
    }

    public Optional<LineQueryDto> findByNameBasicDto(String lineName) {
        return lineQueryRepository.findByName(lineName).map(LineFactory::toQueryDto);
    }

    public List<LineQueryDto> findByProductType(ProductType productType) {
        return lineQueryRepository.findByProductionType(productType)
                .stream()
                .map(LineFactory::toQueryDto)
                .collect(Collectors.toList());
    }

    public List<Line> findAll() {
        return lineQueryRepository.findAll();
    }

    public Optional<Line> findById(long lineId) {
        return lineQueryRepository.findById(lineId);
    }

    public Optional<Line> findByIdAndOperatorExists(final long lineId) {
        return lineQueryRepository.findByIdAndOperatorExists(lineId);
    }

    public Optional<String> findCurrentOperatorByLineId(long lineId) {
        return lineQueryRepository.findById(lineId)
                .filter(l -> !l.getOperatorRfid().equals(""))
                .map(Line::getOperator);

    }
}
