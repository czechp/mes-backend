package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.product.dto.ProductEfficientLineQueryDto;

import javax.validation.constraints.Min;
import java.util.List;

@RestController()
@RequestMapping("/api/product-efficiencies")
@CrossOrigin("*")
@Validated()
class ProductEfficientController {
    private final ProductEfficientCommandService productEfficientCommandService;
    private final ProductEfficientQueryService productEfficientQueryService;

    @Autowired()
    public ProductEfficientController(ProductEfficientCommandService productEfficientCommandService, ProductEfficientQueryService productEfficientQueryService) {
        this.productEfficientCommandService = productEfficientCommandService;
        this.productEfficientQueryService = productEfficientQueryService;
    }

    @GetMapping("/lines/{lineName}")
    @ResponseStatus(HttpStatus.OK)
    List<ProductEfficientLineQueryDto> findByLineName(
            @PathVariable(name = "lineName") final String lineName
    ) {
        return productEfficientQueryService.findByLineName(lineName);
    }


    @DeleteMapping("/{productEfficientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduceEfficient(
            @PathVariable(name = "productEfficientId") @Min(1) final long productEfficientId
    ) {
        productEfficientCommandService.deleteById(productEfficientId);
    }
}

