package com.mitchell.recipeapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    String name = "macaroni and cheese";
    String description = "noodles with american cheese and bread crumbs";
    String[] ingredients = {"noodles", "cheese", "bread crumbs"};
    String[] directions = {"1. boil water", "2. poor in noodles", "3. cook for 5 minutes", "4. strain water",
                            "5. mix in cheese"};
    String category = "side";

    Recipe testRecipe = new Recipe(name, description, ingredients,
    directions, category);

    @Test
    public void testName() {
        String actualName = testRecipe.getName();
        assertEquals("macaroni and cheese", actualName);
    }

    @Test
    public void testUpdateRecipe() {
        String newName = "casserole";
        Recipe testRecipe2 = new Recipe(newName, description, ingredients, directions, category);
        testRecipe2.updateRecipe(testRecipe);
        assertEquals(testRecipe2.getName(), name);
    }

}
