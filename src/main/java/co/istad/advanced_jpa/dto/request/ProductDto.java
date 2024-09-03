package co.istad.advanced_jpa.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * @author Sattya
 * create at 8/30/2024 3:07 PM
 */
@Builder
public record ProductDto(
        @NotBlank
        @Size(min = 5)
        String name,
        @NotBlank
        String description,
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
        BigDecimal price,
        @NotNull
        @Positive
        Long categoryId) {
}
