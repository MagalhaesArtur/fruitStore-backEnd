package com.fruitStoreSystem.api.domain.fruit;

public enum FruitTypes {

    EXTRA("EXTRA"),
    PRIMEIRA("PRIMEIRA"),
    SEGUNDA("SEGUNDA"),
    TERCEIRA("TERCEIRA");

    private final String type;

    FruitTypes(String fruitType) {
        this.type = fruitType;
    }

    public String getFruitType() {
        return type;
    }

    public static FruitTypes fromString(String text) {
        for (FruitTypes ft : FruitTypes.values()) {
            if (ft.type.equals(text)) {
                return ft;
            }
        }
        throw new IllegalArgumentException("Classificação inválida: " + text);
    }
}
