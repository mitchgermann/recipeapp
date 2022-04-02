package com.mitchell.recipeapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    String email = "abc123@gmail.com";
    String password = "123456";

    User testUser = new User(email, password);

    @Test
    public void testUser() {
        assertEquals(testUser.getUsername(), email);
    }

}
