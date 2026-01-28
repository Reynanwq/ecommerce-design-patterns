package com.ecommerce.ecommerce_gof_patterns.factory;

import com.ecommerce.ecommerce_gof_patterns.dto.ProductDTO;
import com.ecommerce.ecommerce_gof_patterns.model.ProductCategory;
import com.ecommerce.ecommerce_gof_patterns.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ElectronicsProductFactory implements ProductFactory{
    private final ProductService productService;

    @Override
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts().stream()
                .filter(product -> ProductCategory.ELECTRONICS.equals(product.getCategory()))
                .collect(Collectors.toList());
    }
}
