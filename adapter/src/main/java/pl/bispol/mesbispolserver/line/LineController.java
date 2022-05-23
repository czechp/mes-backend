package pl.bispol.mesbispolserver.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bispol.mesbispolserver.Statements;
import pl.bispol.mesbispolserver.exception.NotFoundException;
import pl.bispol.mesbispolserver.line.dto.LineCommandDto;
import pl.bispol.mesbispolserver.line.dto.LineQueryDto;
import pl.bispol.mesbispolserver.product.ProductType;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController()
@RequestMapping("/api/lines")
@CrossOrigin("*")
@Validated()
class LineController {
    private final LineQueryService lineQueryService;
    private final LineCommandService lineCommandService;


    @Autowired()
    LineController(LineQueryService lineQueryService, LineCommandService lineCommandService) {
        this.lineQueryService = lineQueryService;
        this.lineCommandService = lineCommandService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<LineQueryDto> findAllBasicDto() {
        return lineQueryService.findAllBasicDto();
    }

    @GetMapping("/{lineId}")
    @ResponseStatus(HttpStatus.OK)
    LineQueryDto findById(
            @PathVariable(name = "lineId") final long lineId
    ) {
        return lineQueryService.findByIdBasicDto(lineId)
                .orElseThrow(() -> new NotFoundException(Statements.LINE_NOT_EXISTS));
    }

    @GetMapping("/product-type")
    @ResponseStatus(HttpStatus.OK)
    List<LineQueryDto> findByProductionType(
            @RequestParam(name = "productType") final ProductType productType
    ) {
        return lineQueryService.findByProductType(productType);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void saveLine(
            @RequestBody() @Valid() LineCommandDto lineCommandDto
    ) {
        lineCommandService.save(lineCommandDto);
    }

    @PatchMapping("/operator/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateOperator(
            @PathVariable(name = "lineId") final long lineId,
            @RequestParam(name = "rfid") final String userRfid
    ) {
        lineCommandService.updateOperator(lineId, userRfid);
    }

    @PatchMapping("/rfid-error/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void setRfidReaderError(
            @PathVariable(name = "lineId") final long lineId
    ) {
        lineCommandService.setRfidReaderError(lineId);
    }


    @PatchMapping("/opc-error/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void setCommunicationOpcError(
            @PathVariable(name = "lineId") final long lineId
    ) {
        lineCommandService.setOpcUError(lineId);
    }

    @PatchMapping("/line-basic-info/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateLineBasicInfo(
            @PathVariable(name = "lineId") final long lineId,
            @RequestParam(name = "lineStatus") final LineStatus lineStatus,
            @RequestParam(name = "lineCounter") @Min(0) final long lineCounter
    ) {
        lineCommandService.updateLineBasicInfo(lineId, lineStatus, lineCounter);
    }

    @DeleteMapping("/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(
            @PathVariable(name = "lineId") final long lineId
    ) {
        lineCommandService.deleteById(lineId);
    }

    @PutMapping("/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modifyLine(
            @PathVariable(name = "lineId") final long lineId,
            @RequestBody() @Valid() LineCommandDto lineCommandDto
    ) {
        lineCommandService.modifyLine(lineId, lineCommandDto);
    }

    @PatchMapping("/product/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateProduct(
            @RequestParam(name = "productId") final long productId,
            @PathVariable(name = "lineId") final long lineId
    ) {
        lineCommandService.updateProduct(lineId, productId);
    }
}


