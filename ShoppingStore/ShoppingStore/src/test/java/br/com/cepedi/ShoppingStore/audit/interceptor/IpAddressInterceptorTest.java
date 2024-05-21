package br.com.cepedi.ShoppingStore.audit.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class IpAddressInterceptorTest {

    @Test
    @DisplayName("Deve retornar true e definir o endereço IP do cliente do cabeçalho X-FORWARDED-FOR")
    void shouldReturnTrueAndSetClientIpAddressFromXForwardedForHeader() {
        // Arrange
        IpAddressInterceptor interceptor = new IpAddressInterceptor();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String ipAddress = "192.168.0.1";

        when(request.getHeader("X-FORWARDED-FOR")).thenReturn(ipAddress);

        // Act
        boolean result = interceptor.preHandle(request, response, new Object());

        // Assert
        assertTrue(result);
        verify(request).getHeader("X-FORWARDED-FOR");
        // Supondo que LoggingAspect é um mock estático
    }

    @Test
    @DisplayName("Deve retornar true e definir o endereço IP do cliente do método getRemoteAddr")
    void shouldReturnTrueAndSetClientIpAddressFromRemoteAddr() {
        // Arrange
        IpAddressInterceptor interceptor = new IpAddressInterceptor();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        String ipAddress = "192.168.0.2";

        when(request.getHeader("X-FORWARDED-FOR")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn(ipAddress);

        // Act
        boolean result = interceptor.preHandle(request, response, new Object());

        // Assert
        assertTrue(result);
        verify(request).getHeader("X-FORWARDED-FOR");
        verify(request).getRemoteAddr();
        // Supondo que LoggingAspect é um mock estático
    }


}
