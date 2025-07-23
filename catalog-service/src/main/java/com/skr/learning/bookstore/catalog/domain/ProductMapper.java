package com.skr.learning.bookstore.catalog.domain;

class ProductMapper {

  static ProductDto toProduct(ProductEntity productEntity) {
    return new ProductDto(
        productEntity.getCode(),
        productEntity.getName(),
        productEntity.getDescription(),
        productEntity.getImageUrl(),
        productEntity.getPrice());
  }
}
