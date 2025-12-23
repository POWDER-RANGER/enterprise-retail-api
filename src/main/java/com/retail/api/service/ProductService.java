package com.retail.api.service;

import com.retail.api.dto.ProductRequest;
import com.retail.api.entity.Product;
import com.retail.api.exception.BadRequestException;
import com.retail.api.exception.NotFoundException;
import com.retail.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public List<Product> list() {
    return productRepository.findAll();
  }

  public Product get(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product not found: id=" + id));
  }

  @Transactional
  public Product create(ProductRequest req) {
    if (productRepository.existsBySku(req.getSku())) {
      throw new BadRequestException("SKU already exists: " + req.getSku());
    }
    Product p = Product.builder()
        .sku(req.getSku())
        .name(req.getName())
        .price(req.getPrice())
        .stockQuantity(req.getStockQuantity())
        .build();
    return productRepository.save(p);
  }

  @Transactional
  public Product update(Long id, ProductRequest req) {
    Product existing = get(id);
    productRepository.findBySku(req.getSku()).ifPresent(other -> {
      if (!other.getId().equals(existing.getId())) {
        throw new BadRequestException("SKU already exists: " + req.getSku());
      }
    });
    existing.setSku(req.getSku());
    existing.setName(req.getName());
    existing.setPrice(req.getPrice());
    existing.setStockQuantity(req.getStockQuantity());
    return productRepository.save(existing);
  }
}
