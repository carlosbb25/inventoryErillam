// service/ProductService.java
package com.example.inventory.service;

import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class ProductService {
    @Inject
    private ProductRepository productRepository;

    @Transactional
    public void createProduct(Product product) {
        productRepository.create(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        return productRepository.update(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(page, size);
    }

    public List<Product> getProductsByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategory(name, category);
    }

    @Transactional
    public void updateProductStock(Long id, int stock) {
        Product product = productRepository.findById(id);
        if (product != null) {
            product.setStock(stock);
            productRepository.update(product);
        }
    }
}
