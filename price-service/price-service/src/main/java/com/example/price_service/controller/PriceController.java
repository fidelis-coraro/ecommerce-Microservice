package com.example.price_service.controller;

import com.example.price_service.model.Price;
import com.example.price_service.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/{productId}")
    public Price getPrice(@PathVariable Long productId) {
        return priceService.getPriceByProductId(productId);
    }

    @PostMapping
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        Price savedPrice = priceService.savePrice(price);
        return ResponseEntity.status(201).body(savedPrice);
    }

    @PutMapping("/{productId}")
    public Price updatePrice(@PathVariable Long productId, @RequestBody Price price) {
        return priceService.updatePrice(productId, price.getPrice());
    }

    @DeleteMapping("/{productId}")
    public void deletePrice(@PathVariable Long productId) {
        priceService.deletePrice(productId);
    }
}
