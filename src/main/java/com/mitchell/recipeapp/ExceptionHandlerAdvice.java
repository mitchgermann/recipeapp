package com.mitchell.recipeapp;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecipeNotFoundException.class)
    public Map<String, Object> handleException(RecipeNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        System.out.println("Exception for method parameter validation thrown");
        return errors;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidSearchParameterException.class)
    public Map<String, Object> handleException(InvalidSearchParameterException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        return response;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, Object> handleException(UserAlreadyExistsException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        return response;
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(WrongUserException.class)
    public Map<String, Object> handleException(WrongUserException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        return response;
    }
}

class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
class InvalidSearchParameterException extends RuntimeException {
    public InvalidSearchParameterException(String message) {
        super(message);
    }
}
class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) { super(message);}
}
class WrongUserException extends RuntimeException {
    public WrongUserException(String message) { super(message);}
}
