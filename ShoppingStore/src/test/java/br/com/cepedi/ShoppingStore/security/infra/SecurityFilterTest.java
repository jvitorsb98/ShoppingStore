package br.com.cepedi.ShoppingStore.security.infra;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

@DisplayName("SecurityFilter Tests")
@TestMethodOrder(MethodOrderer.Random.class)
public class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private SecurityFilter securityFilter;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test doFilterInternal with valid token")
    void testDoFilterInternalWithValidToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String token = "valid_token";
        String subject = "john_doe";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.getSubject(token)).thenReturn(subject);

        User user = new User();
        user.setLogin(subject);
        user.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        when(repository.findByLogin(subject)).thenReturn(user);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(tokenService).getSubject(token);
        verify(repository).findByLogin(subject);
        verify(filterChain).doFilter(request, response);
    }
}