package com.fruitStoreSystem.api.controller;


import com.fruitStoreSystem.api.domain.fruit.Fruit;
import com.fruitStoreSystem.api.domain.fruit.FruitRequestDTO;
import com.fruitStoreSystem.api.domain.fruit.FruitResponseDTO;
import com.fruitStoreSystem.api.domain.fruit.FruitTypes;
import com.fruitStoreSystem.api.exceptions.FruitAlreadyExistsException;
import com.fruitStoreSystem.api.repositories.FruitRepository;
import com.fruitStoreSystem.api.services.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/fruit")
public class FruitController {
    @Autowired
    private FruitService fruitService;

    @Autowired
    FruitRepository repository;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity create(@RequestParam("name") String name,
                                        @RequestParam("classificacao") FruitTypes classificacao,
                                        @RequestParam("fresca") Boolean fresca,
                                        @RequestParam("quantidadeDisponivel") int quantidadeDisponivel,
                                        @RequestParam("valorVenda") float valorVenda,
                                        @RequestParam("image") MultipartFile img)

            {
                try{
                    FruitRequestDTO fruitRequestDTO = new FruitRequestDTO(name,classificacao,fresca,quantidadeDisponivel,valorVenda,img);
                    Fruit newFruit = this.fruitService.createFruit(fruitRequestDTO);
                    return ResponseEntity.ok(newFruit);
                }catch (FruitAlreadyExistsException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
    }

    @ExceptionHandler(FruitAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleFruitAlreadyExists(FruitAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        List<FruitResponseDTO> productList = this.repository.findAll().stream().map(FruitResponseDTO::new).toList();

        return ResponseEntity.ok(productList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Fruit>> searchFruits(@RequestParam String name) {
        return ResponseEntity.ok(fruitService.searchFruitsByName(name));
    }

    @GetMapping("/filter/classification")
    public ResponseEntity<List<Fruit>> filterByClassification(@RequestParam String classification) {
        try {
            FruitTypes fruitType;
            switch (classification) {
                case "EXTRA" -> fruitType = FruitTypes.EXTRA;
                case "PRIMEIRA" -> fruitType = FruitTypes.PRIMEIRA;
                case "SEGUNDA" -> fruitType = FruitTypes.SEGUNDA;
                case "TERCEIRA" -> fruitType = FruitTypes.TERCEIRA;
                case null, default -> {
                    return ResponseEntity.badRequest().body(Collections.emptyList());
                }
            }
            return ResponseEntity.ok(fruitService.filterFruitsByClassification(fruitType));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @GetMapping("/filter/fresh")
    public ResponseEntity<List<Fruit>> filterByFreshness(@RequestParam String fresh) {
        return ResponseEntity.ok(fruitService.filterFruitsByFreshness(Boolean.parseBoolean(fresh)));
    }
}
