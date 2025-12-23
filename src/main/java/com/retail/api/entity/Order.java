package com.retail.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_orders_customer", columnList = "customerId"),
    @Index(name = "idx_orders_status", columnList = "status")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Min(1)
  @Column(nullable = false)
  private Long customerId;

  @NotNull
  @Column(nullable = false)
  private OffsetDateTime orderDate;

  @NotNull
  @DecimalMin(value = "0.00")
  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal totalAmount;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 24)
  private OrderStatus status;
}
