package com.fruitStoreSystem.api.domain.sale;

import com.fruitStoreSystem.api.domain.fruit.Fruit;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "sale_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "fruit_id", nullable = false)
    private Fruit fruit;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = true)
    private int discountPercentage;

    @Column(nullable = false)
    private float subtotal;

    public UUID getSale(){
     return sale.getId();
    }

}
