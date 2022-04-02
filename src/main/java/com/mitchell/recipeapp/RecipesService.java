package com.mitchell.recipeapp;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RecipesService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipesService(RecipeRepository userRepository) {
        this.recipeRepository = userRepository;
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findRecipeById(id);
    }

    public Recipe save(Recipe toSave) {
        return recipeRepository.save(toSave);
    }

    public void deleteById(Long id) {recipeRepository.deleteById(id);}

    public List<Recipe> searchByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public String findUsernameById(Long id) {
        return recipeRepository.findUsernameById(id);}
}
