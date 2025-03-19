package com.fruitStoreSystem.api.domain.sale;

import com.fruitStoreSystem.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(nullable = false)
    private float totalValue;

    public String getBuyer(){
        return this.buyer.getId();
    }

    public String getSeller(){
        return this.seller.getId();
    }

    @Column(nullable = false)
    private LocalDateTime saleTime;
}
