package com.example.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ControllerAdvice
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class ErrorAdvice {

        @ExceptionHandler(ResourceNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public @ResponseBody
        ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
            return ErrorResponse.from(HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public @ResponseBody
        ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
            List<String> messages = e.getBindingResult().getAllErrors().stream()
                .map(objectError -> String.format("Field '%s' %s", ((FieldError) objectError).getField(), objectError.getDefaultMessage()))
                .collect(Collectors.toList());
            return ErrorResponse.from(HttpStatus.BAD_REQUEST, messages);
        }
    }

    private static class ErrorResponse {

        private final HttpStatus httpStatus;

        private final List<String> messages;

        private ErrorResponse(HttpStatus httpStatus, List<String> messages) {
            this.httpStatus = httpStatus;
            this.messages = messages;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public List<String> getMessages() {
            return messages;
        }

        public static ErrorResponse from(HttpStatus httpStatus, List<String> messages) {
            return new ErrorResponse(httpStatus, messages);
        }
    }
}
