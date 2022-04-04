package com.endava.exam.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SupermarketAlreadyExist.class)
    public ResponseEntity<ErrorResponse> supermarketAlreadyExist(SupermarketAlreadyExist exception, WebRequest webRequest) {

        ErrorResponse errors = new ErrorResponse();
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setMessage(exception.getMessage());
        errors.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SupermarketNotFoundException.class)
    public ResponseEntity<ErrorResponse> supermarketIsNotFound (SupermarketNotFoundException exception,
                                                                WebRequest webRequest) {

        ErrorResponse errors = new ErrorResponse();
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage(exception.getMessage());
        errors.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemDoesNotExist.class)
    public ResponseEntity<ErrorResponse> itemDoesNotExist (ItemDoesNotExist exception, WebRequest webRequest) {

        ErrorResponse errors = new ErrorResponse();
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage(exception.getMessage());
        errors.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(getFieldErrors(ex, status), headers, status);
    }

    private Map<String, Object> getFieldErrors(MethodArgumentNotValidException ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        Map<String, List<String>> fieldErrors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String key = error.getField();

            if (!fieldErrors.containsKey(key)) {
                fieldErrors.put(key, new ArrayList<>());
            }
            fieldErrors.get(key).add(error.getDefaultMessage());
        }
        body.put("errors", fieldErrors);
        return body;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        return new ResponseEntity<>(getBody(INTERNAL_SERVER_ERROR, ex, "Something Went Wrong"), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("exception", ex.toString());

        Throwable cause = ex.getCause();
        if (cause != null) {
            body.put("exceptionCause", ex.getCause().toString());
        }

        return body;
    }
}