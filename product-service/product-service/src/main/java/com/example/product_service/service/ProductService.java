package com.example.product_service.service;

import com.example.inventory_service.model.Inventory;
import com.example.price_service.model.Price;
import com.example.product_service.DTO.ProductDTO;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDTO getProduct(Long productId) {

        Inventory inventory = restTemplate.getForObject("http://localhost:8082/inventory/{id}", Inventory.class, productId);

        Price price = restTemplate.getForObject("http://localhost:8083/prices/{id}", Price.class, productId);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));


        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getDescription(),
                product.getCategory(),
                product.getAvailable(),
                inventory,
                price
        );
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
