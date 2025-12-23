package com.retail.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
  @NotBlank
  @Size(max = 64)
  private String sku;

  @NotBlank
  @Size(max = 200)
  private String name;

  @DecimalMin(value = "0.00")
  private BigDecimal price;

  @Min(0)
  private Integer stockQuantity;
}
