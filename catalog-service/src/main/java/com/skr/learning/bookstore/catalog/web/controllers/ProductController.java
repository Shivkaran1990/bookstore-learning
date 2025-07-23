package com.skr.learning.bookstore.catalog.web.controllers;

import com.skr.learning.bookstore.catalog.domain.PagedResult;
import com.skr.learning.bookstore.catalog.domain.ProductDto;
import com.skr.learning.bookstore.catalog.domain.ProductNotFoundException;
import com.skr.learning.bookstore.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
  private final ProductService productService;

  ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  PagedResult<ProductDto> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
    return productService.getProducts(pageNo);
  }

  @GetMapping("/{code}")
  ResponseEntity<ProductDto> getProductByCode(@PathVariable String code) {
    //log.info("Fetching product for code: {}", code);
    return productService
            .getProductByCode(code)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> ProductNotFoundException.forCode(code));
  }
}
