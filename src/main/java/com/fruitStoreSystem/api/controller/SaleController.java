package com.fruitStoreSystem.api.controller;


import com.fruitStoreSystem.api.domain.sale.*;
import com.fruitStoreSystem.api.services.SaleService;
import com.fruitStoreSystem.api.domain.user.User;
import com.fruitStoreSystem.api.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;
    private final UserRepository userRepository;

    public SaleController(SaleService saleService, UserRepository userRepository) {
        this.saleService = saleService;
        this.userRepository = userRepository;
    }


    @PostMapping
    public ResponseEntity<Sale> createSale(
            @RequestBody MainSaleRequestDTO mainSaleRequestDTO) {

        UUID uuidSeller = UUID.fromString(mainSaleRequestDTO.sellerId());

        UUID uuidBuyer= UUID.fromString(mainSaleRequestDTO.buyerId());

        SaleRequestDTO saleRequestDTO = new SaleRequestDTO(uuidSeller,uuidBuyer,mainSaleRequestDTO.items());

        Sale sale = saleService.createSale(saleRequestDTO);
        return ResponseEntity.ok(sale);
    }


    @GetMapping
    public ResponseEntity<List<Sale>> getSales(@AuthenticationPrincipal User seller) {
        List<Sale> sales = saleService.getSalesBySeller(seller);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable UUID id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/saleItems/{id}")
    public ResponseEntity getSaleItemsById(@PathVariable UUID id) {
        List<SaleItem> sales = saleService.getSaleItemsBySaleId(id);
        return ResponseEntity.ok(sales);
    }
}


