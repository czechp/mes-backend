package pl.bispol.mesbispolserver.product.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data()
public class ProductEfficientLineQueryDto {
    private long id;
    private long productId;
    private String productName;
    private long amount;
    private String lineName;
    private List<ProductPropertyQueryDto> productProperties = new ArrayList();
}
