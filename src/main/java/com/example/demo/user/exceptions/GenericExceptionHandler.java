package com.example.demo.user.exceptions;


import com.example.demo.user.utils.ApiResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception e) throws JsonProcessingException{
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(e.getClass() + e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException e)
            throws JsonProcessingException {

        return  ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CONFLICT)
                        .message("Data Integrity Violation "+ e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.METHOD_NOT_ALLOWED)
                        .message("Method not allowed " + e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException e) throws JsonProcessingException {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponseBuilder()
                            .status(HttpStatus.UNAUTHORIZED)
                            .message("Invalid username or password")
                            .build()
            );
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) throws JsonProcessingException {

            List<String> errors = e.getConstraintViolations()
                        .stream()
                        .map(err -> err.getPropertyPath() + " : " + err.getMessage())
                        .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponseBuilder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message("Validation Failed")
                            .data(errors)
                            .build()
            );

        }

            @ExceptionHandler(MethodArgumentNotValidException.class)
            public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) throws JsonProcessingException {

                List<String> errors =  e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + " : " + error.getDefaultMessage())
                        .toList();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ApiResponseBuilder()
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Validation Failed")
                                .data(errors)
                                .build()
                );


            }
}


