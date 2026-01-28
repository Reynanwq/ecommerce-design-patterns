package com.ecommerce.ecommerce_gof_patterns.factory;

import com.ecommerce.ecommerce_gof_patterns.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductFactoryCreator {

    private final ElectronicsProductFactory electronicsFactory;

    public ProductFactoryCreator(ElectronicsProductFactory electronicsFactory) {
        this.electronicsFactory = electronicsFactory;
    }

    public ProductFactory getFactory(ProductCategory category) {
        if (ProductCategory.ELECTRONICS.equals(category)) {
            return electronicsFactory;
        }
        throw new IllegalArgumentException("Factory não implementada para a categoria: " + category);
    }
}