package com.ecommerce.ecommerce_gof_patterns.service.proxy;

import com.ecommerce.ecommerce_gof_patterns.dto.ProductDTO;
import com.ecommerce.ecommerce_gof_patterns.model.ProductCategory;
import com.ecommerce.ecommerce_gof_patterns.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Proxy para ProductService que adiciona cache.
 * Padrão de Projeto: Proxy (Estrutural)
 *
 * O Proxy envolve o ProductService original e adiciona cache
 * apenas no método getProductById.
 */
@Slf4j
@Service
@Primary
public class ProductServiceProxy {

    private final ProductService productService;

    // Cache em memória
    private final Map<Long, CachedProduct> cache = new HashMap<>();

    // TTL do cache: 5 minutos
    private static final long CACHE_TTL_SECONDS = 300;

    public ProductServiceProxy(ProductService productService) {
        this.productService = productService;
        log.info("ProductServiceProxy initialized with cache");
    }

    // Método com cache
    public ProductDTO getProductById(Long id) {
        log.debug("getProductById called for id: {}", id);

        // Verificar cache
        CachedProduct cachedProduct = cache.get(id);

        if (cachedProduct != null && !cachedProduct.isExpired()) {
            log.info("Cache HIT for product id: {}", id);
            return cachedProduct.getProductDTO();
        }

        // Cache MISS - chamar serviço real
        log.info("Cache MISS for product id: {} - fetching from database", id);
        ProductDTO product = productService.getProductById(id);

        // Armazenar no cache
        cache.put(id, new CachedProduct(product));
        log.debug("Product id: {} stored in cache", id);

        return product;
    }

    // Demais métodos apenas delegam para o serviço real

    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductDTO created = productService.createProduct(productDTO);
        // Adicionar ao cache
        if (created.getId() != null) {
            cache.put(created.getId(), new CachedProduct(created));
            log.debug("New product id: {} added to cache", created.getId());
        }
        return created;
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductDTO updated = productService.updateProduct(id, productDTO);
        // Invalidar cache
        cache.remove(id);
        log.debug("Cache invalidated for product id: {} after update", id);
        return updated;
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
        // Remover do cache
        cache.remove(id);
        log.debug("Cache invalidated for product id: {} after delete", id);
    }

    public List<ProductDTO> getProductsByCategory(ProductCategory category) {
        return productService.getProductsByCategory(category);
    }

    public List<ProductDTO> searchProducts(String name) {
        return productService.searchProducts(name);
    }

    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    public List<ProductDTO> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    // Métodos de gerenciamento do cache

    public void clearCache() {
        int size = cache.size();
        cache.clear();
        log.info("Cache cleared - {} entries removed", size);
    }

    public CacheStats getCacheStats() {
        return new CacheStats(cache.size(), CACHE_TTL_SECONDS);
    }

    // Classes internas

    private static class CachedProduct {
        private final ProductDTO productDTO;
        private final LocalDateTime cachedAt;

        public CachedProduct(ProductDTO productDTO) {
            this.productDTO = productDTO;
            this.cachedAt = LocalDateTime.now();
        }

        public ProductDTO getProductDTO() {
            return productDTO;
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(cachedAt.plusSeconds(CACHE_TTL_SECONDS));
        }
    }

    public static class CacheStats {
        private final int size;
        private final long ttlSeconds;

        public CacheStats(int size, long ttlSeconds) {
            this.size = size;
            this.ttlSeconds = ttlSeconds;
        }

        public int getSize() {
            return size;
        }

        public long getTtlSeconds() {
            return ttlSeconds;
        }

        @Override
        public String toString() {
            return String.format("Cache: %d entries, TTL: %d seconds", size, ttlSeconds);
        }
    }

    public ProductService.ProductWithExtras applyExtras(Long id, boolean giftWrap, boolean warranty) {
        return productService.applyExtras(id, giftWrap, warranty);
    }


}
