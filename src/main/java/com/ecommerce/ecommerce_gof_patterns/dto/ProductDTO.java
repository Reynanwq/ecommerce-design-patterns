package com.ecommerce.ecommerce_gof_patterns.dto;

import com.ecommerce.ecommerce_gof_patterns.model.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    private String imageUrl;

    private Boolean active;

    @DecimalMin(value = "0.0", message = "Weight cannot be negative")
    private Double weight;

    @Min(value = 0, message = "Width cannot be negative")
    private Integer width;

    @Min(value = 0, message = "Height cannot be negative")
    private Integer height;

    @Min(value = 0, message = "Depth cannot be negative")
    private Integer depth;
}
