package com.fruitStoreSystem.api.domain.user;

public enum UserRoles {
    ADMIN("admin"),

    USER("user"),

    SELLER("seller");


    private String role;

     UserRoles(String role){
        this.role = role;
    }

    public String getRole(){
         return role;
    }
}
