package com.mitchell.recipeapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "description cannot be blank")
    private String description;

    @Column(name = "ingredients")
    @NotEmpty
    private String[] ingredients;

    @Column(name = "directions")
    @NotEmpty
    private String[] directions;

    @Column(name = "category")
    @NotBlank(message = "category cannot be blank")
    private String category;

    @Column(name = "date")
    //@NotBlank(message = "date cannot be blank")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    @JsonIgnore
    private User user;

    @JsonIgnore
    @Column(name = "username")
    private String username;

    Recipe (String name, String description, String[] ingredients,
            String[] directions, String category) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        date = LocalDateTime.now();
    }

    Recipe () {
        date = LocalDateTime.now();
    }

    public void updateRecipe (Recipe inputRecipe) {
        this.name = inputRecipe.getName();
        this.description = inputRecipe.getDescription();
        this.ingredients = inputRecipe.getIngredients();
        this.directions = inputRecipe.getDirections();
        this.category = inputRecipe.getCategory();
        this.date = LocalDateTime.now();
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String[] getIngredients() {return ingredients;}

    public void setIngredients(String[] ingredients) {this.ingredients = ingredients;}

    public String[] getDirections() {return directions;}

    public void setDirections(String[] directions) {this.directions = directions;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public LocalDateTime getDate() {return date;}

    public void setDate(LocalDateTime date) {this.date = date;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}
}
