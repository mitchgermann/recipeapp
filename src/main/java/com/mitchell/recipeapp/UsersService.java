package com.mitchell.recipeapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UserRepository userRepository;

    @Autowired
    UsersService(UserRepository userRepository) {this.userRepository = userRepository;}

    public boolean userExists(User user) {
        String email = user.getEmail();
        User existingUser = userRepository.findUserByEmail(email);

        return existingUser != null;
    }

    public User save(User toSave) {
        return userRepository.save(toSave);
    }
}
