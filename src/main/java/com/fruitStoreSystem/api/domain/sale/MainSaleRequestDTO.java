package com.fruitStoreSystem.api.domain.sale;


import java.util.List;

public record MainSaleRequestDTO(String sellerId, String buyerId, List<SaleItemRequestDTO> items) {
}
