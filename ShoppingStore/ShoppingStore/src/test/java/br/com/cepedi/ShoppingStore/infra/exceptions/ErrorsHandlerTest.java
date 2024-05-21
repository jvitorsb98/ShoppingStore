package br.com.cepedi.ShoppingStore.infra.exceptions;

import br.com.cepedi.ShoppingStore.infra.exception.ErrorsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorsHandlerTest {

    private final ErrorsHandler errorHandler = new ErrorsHandler();

    @Test
    @DisplayName("Test EntityNotFoundException")
    public void testEntityNotFoundException() {
        ResponseEntity<Object> responseEntity = errorHandler.Error404();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    @DisplayName("Test BadCredentialsException")
    public void testBadCredentialsException() {
        ResponseEntity<Object> responseEntity = errorHandler.handleBadCredentialsError();
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test AuthenticationException")
    public void testAuthenticationException() {
        ResponseEntity<Object> responseEntity = errorHandler.handleAuthenticationError();
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test AccessDeniedException")
    public void testAccessDeniedException() {
        ResponseEntity<Object> responseEntity = errorHandler.handleAccessDeniedError();
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test Exception")
    public void testException() {
        Exception exception = new Exception("Test exception");
        ResponseEntity<Object> responseEntity = errorHandler.handle500Error(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}
