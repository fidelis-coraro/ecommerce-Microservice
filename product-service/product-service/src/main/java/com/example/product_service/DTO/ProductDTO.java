package com.example.product_service.DTO;

import com.example.inventory_service.model.Inventory;
import com.example.price_service.model.Price;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private String category;
    private Boolean available;
    private Inventory inventory;
    private Price price;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String brand, String description, String category, Boolean available, Inventory inventory, Price price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.available = available;
        this.inventory = inventory;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
