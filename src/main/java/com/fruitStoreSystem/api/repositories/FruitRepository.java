package com.fruitStoreSystem.api.repositories;

import com.fruitStoreSystem.api.domain.fruit.Fruit;
import com.fruitStoreSystem.api.domain.fruit.FruitTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, UUID> {
    List<Fruit> findByNameContainingIgnoreCase(String name);
    List<Fruit> findByClassificacao(FruitTypes classificacao);
    List<Fruit> findByFresca(Boolean fresca);
    Fruit findByName(String name);
}
