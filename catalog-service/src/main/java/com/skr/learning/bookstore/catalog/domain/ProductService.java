package com.skr.learning.bookstore.catalog.domain;

import com.skr.learning.bookstore.catalog.ApplicationProperties;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
  private final ProductRepository productRepository;
  private final ApplicationProperties applicationProperties;

  ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
    this.productRepository = productRepository;
    this.applicationProperties = applicationProperties;
  }

  public PagedResult<ProductDto> getProducts(int pageNo) {
    pageNo = pageNo <= 1 ? 0 : pageNo - 1;
    Pageable pageable =
        PageRequest.of(pageNo, applicationProperties.pageSize(), Sort.by("name").ascending());
    Page<ProductDto> productsPage =
        productRepository.findAll(pageable).map(ProductMapper::toProduct);
    return new PagedResult<>(
        productsPage.getContent(),
        productsPage.getTotalElements(),
        productsPage.getNumber() + 1,
        productsPage.getTotalPages(),
        productsPage.isFirst(),
        productsPage.isLast(),
        productsPage.hasNext(),
        productsPage.hasPrevious());
  }
  public Optional<ProductDto> getProductByCode(String code) {
    return productRepository.findByCode(code).map(ProductMapper::toProduct);
  }
}
