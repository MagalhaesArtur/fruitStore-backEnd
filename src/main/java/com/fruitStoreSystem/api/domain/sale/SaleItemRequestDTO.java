package com.fruitStoreSystem.api.domain.sale;

import java.util.UUID;


public record SaleItemRequestDTO(UUID fruitId, int quantity,float subtotal, int discountPercentage) {
    public UUID getFruitId() {
        return fruitId;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getSubtotal() {
        return quantity;
    }


    public int getDiscountPercentage() {
        return discountPercentage;
    }
}
