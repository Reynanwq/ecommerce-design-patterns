package com.ecommerce.ecommerce_gof_patterns.factory;

import com.ecommerce.ecommerce_gof_patterns.dto.ProductDTO;

import java.util.List;

public interface ProductFactory {
    List<ProductDTO> getProducts();
}
