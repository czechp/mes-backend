package pl.bispol.mesbispolserver.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/product-properties")
@CrossOrigin("*")
class ProductPropertyController {
    private final ProductPropertyCommandService productPropertyCommandService;

    @Autowired()
    ProductPropertyController(final ProductPropertyCommandService productPropertyCommandService) {
        this.productPropertyCommandService = productPropertyCommandService;
    }


    @DeleteMapping("/{productPropertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductProperty(
            @PathVariable(name = "productPropertyId") final long productPropertyId
    ) {
        productPropertyCommandService.deleteById(productPropertyId);
    }

}
