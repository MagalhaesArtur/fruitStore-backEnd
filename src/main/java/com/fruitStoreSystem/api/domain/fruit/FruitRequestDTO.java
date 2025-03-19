package com.fruitStoreSystem.api.domain.fruit;

import org.springframework.web.multipart.MultipartFile;

public record FruitRequestDTO(String name, FruitTypes classificacao, Boolean fresca, int quantidadeDisponivel, float valorVenda, MultipartFile image) {

}
