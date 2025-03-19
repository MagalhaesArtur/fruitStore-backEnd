package com.fruitStoreSystem.api.domain.sale;

import java.util.List;
import java.util.UUID;

public record SaleRequestDTO (UUID sellerId, UUID buyerId, List<SaleItemRequestDTO> items){
}
