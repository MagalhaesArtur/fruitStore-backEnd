package com.fruitStoreSystem.api.services;

import com.fruitStoreSystem.api.domain.fruit.Fruit;
import com.fruitStoreSystem.api.domain.sale.Sale;
import com.fruitStoreSystem.api.domain.sale.SaleItem;
import com.fruitStoreSystem.api.domain.sale.SaleItemRequestDTO;
import com.fruitStoreSystem.api.domain.sale.SaleRequestDTO;
import com.fruitStoreSystem.api.domain.user.User;
import com.fruitStoreSystem.api.repositories.FruitRepository;
import com.fruitStoreSystem.api.repositories.SaleItemRepository;
import com.fruitStoreSystem.api.repositories.SaleRepository;
import com.fruitStoreSystem.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    public void createSaleItem(SaleItem saleItem){
        saleItemRepository.save(saleItem);
    }

    public Sale createSale(SaleRequestDTO sale) {
        List<SaleItem> lista = new ArrayList<>();
        float totalValue = 0;

        Sale tempSale = new Sale();

        userRepository.findById(String.valueOf(sale.sellerId()))
                .orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));
        userRepository.findById(String.valueOf(sale.buyerId()))
                .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado"));

        User sellerTemp = userRepository.findById(String.valueOf(sale.sellerId())).get();
        User buyerTemp = userRepository.findById(String.valueOf(sale.buyerId())).get();

        tempSale.setSeller(sellerTemp);
        tempSale.setBuyer(buyerTemp);

        tempSale.setSaleTime(LocalDateTime.now());


        for (SaleItemRequestDTO item : sale.items()) {
            Fruit fruit = fruitRepository.findById(item.fruitId())
                    .orElseThrow(() -> new IllegalArgumentException("Fruta não encontrada"));
            totalValue+=fruit.getValorVenda() * ((float) (100 - item.getDiscountPercentage()) /100);
            totalValue*= item.getQuantity();

        }
        tempSale.setTotalValue(totalValue);
        tempSale = saleRepository.save(tempSale);

        for (SaleItemRequestDTO item : sale.items()) {
            Fruit fruit = fruitRepository.findById(item.fruitId())
                    .orElseThrow(() -> new IllegalArgumentException("Fruta não encontrada"));

            if (fruit.getQuantidadeDisponivel() < item.getQuantity()) {
                throw new IllegalArgumentException("Quantidade insuficiente para a fruta: " + fruit.getName());
            }

            fruit.setQuantidadeDisponivel(fruit.getQuantidadeDisponivel() - item.getQuantity());
            fruitRepository.save(fruit);

            SaleItem newSaleItem = new SaleItem();
            newSaleItem.setSale(tempSale);
            newSaleItem.setFruit(fruit);
            newSaleItem.setQuantity(item.quantity());
            newSaleItem.setDiscountPercentage(item.discountPercentage());
            newSaleItem.setSubtotal(fruit.getValorVenda());

            lista.add(newSaleItem);

        }

        for (SaleItem item : lista) {
            createSaleItem(item);
        }


        return tempSale;
    }

    public List<SaleItem> getSaleItemsBySaleId(UUID saleId){
        return saleItemRepository.findBySaleId(saleId);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(UUID id) {
        return saleRepository.findById(id);
    }
    public List<Sale> getSalesBySeller(User seller) {
        return saleRepository.findBySeller(seller);
    }
}
