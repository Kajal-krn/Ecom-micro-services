package com.kajal.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "User")
@Data
public class User {
    @Id
    private String id;

    @NotNull(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Invalid Username")
    private String userName;

    @NotBlank(message = "Invalid password")
    private String password;

    @Field("roles")
    private UserRole roles;

    public User(String email, String userName, String password, UserRole roles) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public UserRole getRoles(){
        return roles;
    }

    public void setRoles(UserRole roles){
        this.roles = roles;
    }
}

