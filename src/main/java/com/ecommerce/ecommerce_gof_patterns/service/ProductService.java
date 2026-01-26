package com.ecommerce.ecommerce_gof_patterns.service;

import com.ecommerce.ecommerce_gof_patterns.dto.ProductDTO;
import com.ecommerce.ecommerce_gof_patterns.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_gof_patterns.model.Product;
import com.ecommerce.ecommerce_gof_patterns.model.ProductCategory;
import com.ecommerce.ecommerce_gof_patterns.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        product.setActive(true);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setImageUrl(productDTO.getImageUrl());
        existingProduct.setWeight(productDTO.getWeight());
        existingProduct.setWidth(productDTO.getWidth());
        existingProduct.setHeight(productDTO.getHeight());
        existingProduct.setDepth(productDTO.getDepth());

        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setActive(false);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAvailableProducts() {
        return productRepository.findAvailableProducts().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    //design decorator
    public ProductWithExtras applyExtras(Long productId, boolean addGiftWrap, boolean addWarranty) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        String description = product.getName();
        BigDecimal price = product.getPrice();

        // Aplicar decoradores
        if (addGiftWrap) {
            description += " + Embalagem para Presente";
            price = price.add(new BigDecimal("15.00"));
        }

        if (addWarranty) {
            description += " + Garantia Estendida";
            price = price.add(new BigDecimal("99.00"));
        }

        return new ProductWithExtras(product.getId(), description, price);
    }

    /**
     * Classe interna para retornar produto decorado.
     */
    public static class ProductWithExtras {
        private Long id;
        private String description;
        private BigDecimal finalPrice;

        public ProductWithExtras(Long id, String description, BigDecimal finalPrice) {
            this.id = id;
            this.description = description;
            this.finalPrice = finalPrice;
        }

        public Long getId() { return id; }
        public String getDescription() { return description; }
        public BigDecimal getFinalPrice() { return finalPrice; }
    }

}
