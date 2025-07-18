package com.skr.learning.bookstore.catalog.domain;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
	   private final ProductRepository productRepository;
    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }
    public List<ProductEntity> getProducts() {
    	return productRepository.findAll();
    	
    }
    }
