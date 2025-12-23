package com.retail.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_products_sku", columnList = "sku", unique = true)
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 64)
  @Column(nullable = false, unique = true, length = 64)
  private String sku;

  @NotBlank
  @Size(max = 200)
  @Column(nullable = false, length = 200)
  private String name;

  @DecimalMin(value = "0.00")
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal price;

  @Min(0)
  @Column(nullable = false)
  private Integer stockQuantity;
}
