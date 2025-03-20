package com.example.inventory_service.controller;

import com.example.inventory_service.model.Inventory;
import com.example.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PostMapping
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return inventoryService.addInventory(inventory);
    }


    @PutMapping("/{productId}")
    public Inventory updateInventory(@PathVariable Long productId, @RequestBody Inventory inventory) {
        return inventoryService.updateInventory(productId, inventory.getQuantity());
    }

    @DeleteMapping("/{productId}")
    public void deleteInventory(@PathVariable Long productId) {
        inventoryService.deleteInventory(productId);
    }
}
