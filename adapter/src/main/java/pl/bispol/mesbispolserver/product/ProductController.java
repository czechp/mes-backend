package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.product.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController()
@RequestMapping("/api/products")
@CrossOrigin("*")
class ProductController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;


    @Autowired()
    ProductController(final ProductCommandService productCommandService, final ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<ProductQueryDto> findAll() {
        return productQueryService.findAll();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    ProductQueryDto findById(
            @PathVariable(name = "productId") final long productId
    ) {
        return productQueryService.findById(productId)
                .orElseThrow(() -> new NotFoundException(Statements.PRODUCT_NOT_EXISTS));
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    ProductQueryDto findByName(@RequestParam(name = "productName") final String productName) {
        return productQueryService.findByName(productName)
                .orElseThrow(() -> new NotFoundException(Statements.PRODUCT_NOT_EXISTS));

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ProductQueryDto createProduct(
            @RequestBody() @Valid() ProductCommandDto productCommandDto
    ) {
        return productCommandService.save(productCommandDto);
    }

    @PostMapping("/properties/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    ProductPropertyQueryDto createProductProperty(
            @PathVariable(name = "productId") final long productId,
            @RequestBody() @Valid() ProductPropertyCommandDto productPropertyCommandDto
    ) {
        return productCommandService.addProperty(productId, productPropertyCommandDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable(name = "productId") final long productId) {
        productCommandService.deleteProductById(productId);
    }

    @PostMapping("/product-efficiencies/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addProductEfficient(
            @RequestBody() @Valid() final ProductEfficientCommandDto productEfficientCommandDto,
            @PathVariable() @Min(1) final long productId
    ) {
        productCommandService.addProductEfficient(productId, productEfficientCommandDto);
    }
}
