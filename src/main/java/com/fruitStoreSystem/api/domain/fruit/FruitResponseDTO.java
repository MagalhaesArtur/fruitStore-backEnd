package com.fruitStoreSystem.api.domain.fruit;


public record FruitResponseDTO(String id, String name, int quantidadeDisponivel,String imgUrl, FruitTypes classificacao, Boolean fresca, float valorVenda) {
    public FruitResponseDTO(Fruit product){
        this(String.valueOf(product.getId()), product.getName(), product.getQuantidadeDisponivel(),product.getImgUrl(), product.getClassificacao(),product.getFresca()
        ,product.getValorVenda());
    }
}