package com.fruitStoreSystem.api.repositories;

import com.fruitStoreSystem.api.domain.sale.Sale;
import com.fruitStoreSystem.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
    List<Sale> findBySeller(User seller);
}
