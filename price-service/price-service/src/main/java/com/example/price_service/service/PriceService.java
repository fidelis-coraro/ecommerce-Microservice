package com.example.price_service.service;

import com.example.price_service.exception.PriceNotFoundException;
import com.example.price_service.model.Price;
import com.example.price_service.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getPriceByProductId(Long productId) {
        return priceRepository.findByProductId(productId)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for product ID: " + productId));
    }

    public Price savePrice(Price price) {
        return priceRepository.save(price);
    }

    public Price updatePrice(Long productId, Double price) {
        Price priceEntity = priceRepository.findByProductId(productId)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for product ID: " + productId));
        priceEntity.setPrice(price);
        return priceRepository.save(priceEntity);
    }

    public void deletePrice(Long productId) {
        Price priceEntity = priceRepository.findByProductId(productId)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for product ID: " + productId));
        priceRepository.delete(priceEntity);
    }
}
