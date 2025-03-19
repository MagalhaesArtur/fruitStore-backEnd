package com.fruitStoreSystem.api.domain.fruit;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Table(name = "fruit")
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Fruit {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FruitTypes classificacao;

    @Column(nullable = false)
    private Boolean fresca;

    @Column(nullable = false)
    private int quantidadeDisponivel;

    @Column(nullable = false)
    private float valorVenda;

    @Column(nullable = false)
    private String imgUrl;


}
