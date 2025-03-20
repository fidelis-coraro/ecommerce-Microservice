package com.example.product_service;

import com.example.inventory_service.model.Inventory;
import com.example.price_service.model.Price;
import com.example.product_service.DTO.ProductDTO;
import com.example.product_service.exception.ProductNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ProductService productService;

	private Product product;
	private ProductDTO productDTO;
	private Inventory inventory;
	private Price price;

	@BeforeEach
	public void setup() {
		product = new Product(1L, "Laptop", "Dell", "High-performance laptop", "Electronics", true);
		inventory = new Inventory(1L,100);
		price = new Price(1L, 10.00);
		productDTO = new ProductDTO(1L, "Laptop", "Dell", "High-performance laptop", "Electronics", true, inventory, price);
	}

	@Test
	public void testGetProduct_ShouldReturnProductDTO() {
		// Arrange
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		when(restTemplate.getForObject("http://localhost:8082/inventory/{id}", Inventory.class, 1L)).thenReturn(inventory);
		when(restTemplate.getForObject("http://localhost:8083/prices/{id}", Price.class, 1L)).thenReturn(price);

		// Act
		ProductDTO result = productService.getProduct(1L);

		// Assert
		assertNotNull(result);
		assertEquals(product.getId(), result.getId());
		assertEquals(product.getName(), result.getName());
		assertEquals(inventory, result.getInventory());
		assertEquals(price, result.getPrice());
	}

	@Test
	public void testGetProduct_ProductNotFound_ShouldThrowException() {
		// Arrange
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ProductNotFoundException.class, () -> productService.getProduct(1L));
	}

	@Test
	public void testAddProduct_ShouldReturnSavedProduct() {
		// Arrange
		when(productRepository.save(product)).thenReturn(product);

		// Act
		Product result = productService.addProduct(product);

		// Assert
		assertNotNull(result);
		assertEquals(product.getId(), result.getId());
		assertEquals(product.getName(), result.getName());
	}

	@Test
	public void testUpdateProduct_ShouldReturnUpdatedProduct() {
		// Arrange
		Product updatedProduct = new Product(1L, "Updated Product", "Updated Brand", "Updated Description", "Category", true);
		when(productRepository.existsById(1L)).thenReturn(true);
		when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

		// Act
		Product result = productService.updateProduct(1L, updatedProduct);

		// Assert
		assertNotNull(result);
		assertEquals(updatedProduct.getName(), result.getName());
	}

	@Test
	public void testUpdateProduct_ProductNotFound_ShouldThrowException() {
		// Arrange
		Product updatedProduct = new Product(1L, "Updated Product", "Updated Brand", "Updated Description", "Category", true);
		when(productRepository.existsById(1L)).thenReturn(false);

		// Act & Assert
		assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, updatedProduct));
	}

	@Test
	public void testDeleteProduct_ShouldDeleteProduct() {
		// Arrange
		when(productRepository.existsById(1L)).thenReturn(true);

		// Act
		productService.deleteProduct(1L);

		// Assert
		verify(productRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testDeleteProduct_ProductNotFound_ShouldThrowException() {

		when(productRepository.existsById(1L)).thenReturn(false);


		assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
	}
}
