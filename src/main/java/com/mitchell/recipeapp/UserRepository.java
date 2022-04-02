package com.mitchell.recipeapp;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
