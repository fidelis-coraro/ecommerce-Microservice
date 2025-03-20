package com.example.price_service;

import com.example.price_service.exception.PriceNotFoundException;
import com.example.price_service.model.Price;
import com.example.price_service.repository.PriceRepository;
import com.example.price_service.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PriceServiceApplicationTests {

	@Mock
	private PriceRepository priceRepository;

	@InjectMocks
	private PriceService priceService;

	private Price price;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		price = new Price();
		price.setProductId(1L);
		price.setPrice(99.99);
	}

	@Test
	void testGetPriceByProductId_Success() {
		// Arrange
		Long productId = 1L;
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.of(price));

		// Act
		Price result = priceService.getPriceByProductId(productId);

		// Assert
		assertNotNull(result);
		assertEquals(price.getProductId(), result.getProductId());
		assertEquals(price.getPrice(), result.getPrice());
		verify(priceRepository, times(1)).findByProductId(productId);
	}

	@Test
	void testGetPriceByProductId_NotFound() {
		// Arrange
		Long productId = 1L;
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.empty());

		// Act & Assert
		PriceNotFoundException exception = assertThrows(PriceNotFoundException.class,
				() -> priceService.getPriceByProductId(productId));
		assertEquals("Price not found for product ID: 1", exception.getMessage());
	}

	@Test
	void testSavePrice() {
		// Arrange
		when(priceRepository.save(any(Price.class))).thenReturn(price);

		// Act
		Price result = priceService.savePrice(price);

		// Assert
		assertNotNull(result);
		assertEquals(price.getProductId(), result.getProductId());
		assertEquals(price.getPrice(), result.getPrice());
		verify(priceRepository, times(1)).save(any(Price.class));
	}

	@Test
	void testUpdatePrice_Success() {
		// Arrange
		Long productId = 1L;
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.of(price));
		price.setPrice(120.00);
		when(priceRepository.save(any(Price.class))).thenReturn(price);

		// Act
		Price result = priceService.updatePrice(productId, 120.00);

		// Assert
		assertNotNull(result);
		assertEquals(120.00, result.getPrice());
		verify(priceRepository, times(1)).findByProductId(productId);
		verify(priceRepository, times(1)).save(any(Price.class));
	}

	@Test
	void testUpdatePrice_NotFound() {
		// Arrange
		Long productId = 1L;
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.empty());

		// Act & Assert
		PriceNotFoundException exception = assertThrows(PriceNotFoundException.class,
				() -> priceService.updatePrice(productId, 120.00));
		assertEquals("Price not found for product ID: 1", exception.getMessage());
	}

	@Test
	void testDeletePrice_Success() {
		// Arrange
		Long productId = 1L;
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.of(price));

		// Act
		priceService.deletePrice(productId);

		// Assert
		verify(priceRepository, times(1)).findByProductId(productId);
		verify(priceRepository, times(1)).delete(price);
	}

	@Test
	void testDeletePrice_NotFound() {
		// Arrange
		Long productId = 1L;
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.empty());

		// Act & Assert
		PriceNotFoundException exception = assertThrows(PriceNotFoundException.class,
				() -> priceService.deletePrice(productId));
		assertEquals("Price not found for product ID: 1", exception.getMessage());
	}
}
