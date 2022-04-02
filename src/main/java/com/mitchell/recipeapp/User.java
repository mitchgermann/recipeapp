package com.mitchell.recipeapp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @JsonIgnore
    private String username;

    @Column(name = "email")
    @Email
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;

    @Column(name = "password")
    @Size(min = 8)
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    @Column(name = "role")
    private String role;

    User() {};

    User (String email, String password) {
        this.email = email;
        this.password = password;
        this.username = email;
        this.role = "ROLE_USER";
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public List<Recipe> getRecipes() {return recipes;}

    public void setRecipes(List<Recipe> recipes) {this.recipes = recipes;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}
}
