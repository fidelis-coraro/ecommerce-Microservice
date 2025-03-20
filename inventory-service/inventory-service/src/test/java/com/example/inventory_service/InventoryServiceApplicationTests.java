package com.example.inventory_service;

import com.example.inventory_service.exception.InventoryNotFoundException;
import com.example.inventory_service.model.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import com.example.inventory_service.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class InventoryServiceApplicationTests {

	@Mock
	private InventoryRepository inventoryRepository;

	@InjectMocks
	private InventoryService inventoryService;

	private Inventory inventory;

	@BeforeEach
	void setUp() {
		// Initialize mocks
		MockitoAnnotations.openMocks(this);

		// Set up the sample inventory object
		inventory = new Inventory();
		inventory.setProductId(1L);
		inventory.setQuantity(100);
	}

	@Test
	void testGetInventoryByProductId_Found() {
		// Mock the repository method
		when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

		// Call the service method
		Inventory result = inventoryService.getInventoryByProductId(1L);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result.getProductId());
		assertEquals(100, result.getQuantity());
	}

	@Test
	void testGetInventoryByProductId_NotFound() {
		// Mock the repository method to return an empty Optional
		when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

		// Call the service method and assert exception
		InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () ->
				inventoryService.getInventoryByProductId(1L));

		assertEquals("Inventory not found for product ID: 1", exception.getMessage());
	}

	@Test
	void testAddInventory() {
		// Mock the repository save method
		when(inventoryRepository.save(inventory)).thenReturn(inventory);

		// Call the service method
		Inventory result = inventoryService.addInventory(inventory);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result.getProductId());
		assertEquals(100, result.getQuantity());

		// Verify the repository save method is called
		verify(inventoryRepository, times(1)).save(inventory);
	}

	@Test
	void testUpdateInventory_Found() {
		// Mock repository methods
		when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));
		when(inventoryRepository.save(inventory)).thenReturn(inventory);

		// Call the service method
		Inventory result = inventoryService.updateInventory(1L, 200);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result.getProductId());
		assertEquals(200, result.getQuantity());

		// Verify repository interactions
		verify(inventoryRepository, times(1)).findByProductId(1L);
		verify(inventoryRepository, times(1)).save(inventory);
	}

	@Test
	void testUpdateInventory_NotFound() {
		// Mock repository method to return an empty Optional
		when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

		// Call the service method and assert exception
		InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () ->
				inventoryService.updateInventory(1L, 200));

		assertEquals("Inventory not found for product ID: 1", exception.getMessage());
	}

	@Test
	void testDeleteInventory_Found() {
		// Mock repository methods
		when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.of(inventory));

		// Call the service method
		inventoryService.deleteInventory(1L);

		// Verify repository delete method is called
		verify(inventoryRepository, times(1)).delete(inventory);
	}

	@Test
	void testDeleteInventory_NotFound() {
		// Mock repository method to return an empty Optional
		when(inventoryRepository.findByProductId(1L)).thenReturn(Optional.empty());

		// Call the service method and assert exception
		InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () ->
				inventoryService.deleteInventory(1L));

		assertEquals("Inventory not found for product ID: 1", exception.getMessage());
	}
}
