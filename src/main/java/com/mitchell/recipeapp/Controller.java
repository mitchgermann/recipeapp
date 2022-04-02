package com.mitchell.recipeapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
class Controller {

    @Autowired
    RecipesService recipesService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UsersService usersService;

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody User newUser) {
        System.out.println("registering user \n email: " + newUser.getEmail() + "\n" +
                "password: " + newUser.getPassword());
        System.out.println(newUser.getUsername());

        if (usersService.userExists(newUser)) {
            throw new UserAlreadyExistsException("user already exists");
        } else {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getEmail());
            newUser.setRole("ROLE_USER");
            usersService.save(newUser);
        }
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> addRecipe(@Valid @RequestBody Recipe recipe,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("name: " + recipe.getName());
        System.out.println("description: " + recipe.getDescription());
        System.out.println("ingredients: " + Arrays.toString(recipe.getIngredients()));
        System.out.println("directions: " + Arrays.toString(recipe.getDirections()));
        System.out.println("category: " + recipe.getCategory());

        recipe.setUsername(userDetails.getUsername());
        Recipe savedRecipe = recipesService.save(recipe);

        System.out.println("saved id: " + savedRecipe.getId());
        System.out.println("saved name: " + savedRecipe.getName());
        System.out.println("saved user: " + savedRecipe.getUser());
        return Map.of("id", savedRecipe.getId());
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        System.out.println("getting id: " + id);

        Optional<Recipe> recipeOptional = Optional.ofNullable(recipesService.findRecipeById(id));
        if (recipeOptional.isPresent()) {
            return recipesService.findRecipeById(id);
        } else {
            throw new RecipeNotFoundException("recipe not found");
        }
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> searchRecipes(@RequestParam Map<String, String> allParams) {
        List<Recipe> results = new ArrayList<>();

        if (allParams.size() != 1 || (!allParams.containsKey("category") && !allParams.containsKey("name"))) {
            throw new InvalidSearchParameterException("search parameter must be either name or category");
        } else if (allParams.containsKey("category")) {
            results = recipesService.searchByCategory(allParams.get("category"));
        } else {
            results = recipesService.searchByName(allParams.get("name"));
        }

        return results;
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe inputRecipe,
                             @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Recipe> recipeOptional = Optional.ofNullable(recipesService.findRecipeById(id));
        String username = userDetails.getUsername();

        if (recipeOptional.isPresent()) {
            Recipe existingRecipe = recipeOptional.get();

            if (!username.equals(existingRecipe.getUsername())) {
                throw new WrongUserException("only the recipe author can edit a recipe");
            }

            existingRecipe.updateRecipe(inputRecipe);
            recipesService.save(existingRecipe);
            System.out.println("updated id: " + existingRecipe.getId());

        } else {
            throw new RecipeNotFoundException("recipe not found");
        }
    }

    @DeleteMapping("api/recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("deleting id: " + id);
        String username = userDetails.getUsername();
        Optional<Recipe> recipeOptional = Optional.ofNullable(recipesService.findRecipeById(id));

        if (recipeOptional.isPresent()) {
            if (!username.equals(recipeOptional.get().getUsername())) {
                throw new WrongUserException("only the recipe author can delete a recipe");
            }
            recipesService.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
