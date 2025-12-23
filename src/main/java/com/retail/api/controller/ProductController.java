package com.retail.api.controller;

import com.retail.api.dto.ProductRequest;
import com.retail.api.entity.Product;
import com.retail.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public List<Product> list() {
    return productService.list();
  }

  @GetMapping("/{id}")
  public Product get(@PathVariable Long id) {
    return productService.get(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@Valid @RequestBody ProductRequest req) {
    return productService.create(req);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
    return productService.update(id, req);
  }
}
