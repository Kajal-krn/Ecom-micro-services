package com.kajal.user_service.entity;

import java.util.HashMap;
import java.util.Random;

public enum UserRole {
    USER("User"),
    ADMIN("Admin");

    private final String name;

    UserRole(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public static final HashMap<String,UserRole> map = new HashMap<>();

    static {
        for(UserRole type : values()){
            map.put(type.name, type);
        }
    }

    public static UserRole of(String name){
        return map.get(name);
    }
}
