package br.com.cepedi.ShoppingStore.security.model.entitys;

import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("User Test - Random Order")
public class UserTest {

    @Test
    @DisplayName("Test constructor with DataRegisterUser and PasswordEncoder")
    void testConstructor() {
        DataRegisterUser dataRegisterUser = new DataRegisterUser("login", "email", "name", "password");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(dataRegisterUser, passwordEncoder);

        assertEquals("login", user.getLogin());
        assertEquals("email", user.getEmail());
        assertEquals("name", user.getName());
        assertNotNull(user.getPassword());
        assertFalse(user.getActivated());
    }

    @Test
    @DisplayName("Test activate method")
    void testActivate() {
        User user = new User();
        user.activate();

        assertTrue(user.getActivated());
    }

    @Test
    @DisplayName("Test disabled method")
    void testDisabled() {
        User user = new User();
        user.disabled();

        assertFalse(user.getActivated());
    }

    @Test
    @DisplayName("Test getAuthorities method")
    void testGetAuthorities() {
        User user = new User();
        Role role = new Role("ADMIN");
        user.getRoles().add(role);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Set<String> authorityNames = new HashSet<>();
        authorities.forEach(authority -> authorityNames.add(authority.getAuthority()));

        assertTrue(authorityNames.contains("ROLE_ADMIN"));
    }

    @Test
    @DisplayName("Test getPassword method")
    void testGetPassword() {
        User user = new User();
        user.setPassword("password");

        assertEquals("password", user.getPassword());
    }

    @Test
    @DisplayName("Test getUsername method")
    void testGetUsername() {
        User user = new User();
        user.setLogin("login");

        assertEquals("login", user.getUsername());
    }

    @Test
    @DisplayName("Test isAccountNonExpired method")
    void testIsAccountNonExpired() {
        User user = new User();

        assertTrue(user.isAccountNonExpired());
    }

    @Test
    @DisplayName("Test isAccountNonLocked method")
    void testIsAccountNonLocked() {
        User user = new User();

        assertTrue(user.isAccountNonLocked());
    }

    @Test
    @DisplayName("Test isCredentialsNonExpired method")
    void testIsCredentialsNonExpired() {
        User user = new User();

        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("Test isEnabled method")
    void testIsEnabled() {
        User user = new User();

        assertTrue(user.isEnabled());
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        User user = new User();

        user.setId(1L);
        user.setLogin("john_doe");
        user.setEmail("john.doe@example.com");
        user.setName("John Doe");
        user.setPassword("password123");
        user.setActivated(true);

        assertEquals(1L, user.getId());
        assertEquals("john_doe", user.getLogin());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertTrue(user.getActivated());
    }

    @Test
    @DisplayName("Test hashCode method")
    void testHashcode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("john_doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setLogin("john_doe");
        user2.setEmail("john.doe@example.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setLogin("jane_smith");
        user3.setEmail("jane.smith@example.com");

        assertEquals(user1, user2); // Deve ser igual
        assertNotEquals(user1, user3); // Deve ser diferente
    }

    @Test
    @DisplayName("Test equals method")
    void testEquals() {
        User user1 = new User();
        user1.setId(1L);
        user1.setLogin("john_doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setLogin("john_doe");
        user2.setEmail("john.doe@example.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setLogin("jane_smith");
        user3.setEmail("jane.smith@example.com");

        assertTrue(user1.equals(user2)); // Deve ser igual
        assertFalse(user1.equals(user3)); // Deve ser diferente

        // Teste adicional para verificar a reflexividade
        assertTrue(user1.equals(user1));
    }

    @Test
    @DisplayName("Test setting roles")
    void testSettingRoles() {
        User user = new User();
        Role role1 = new Role("ROLE_ADMIN");
        role1.setId(1L);
        Role role2 = new Role("ROLE_USER");
        role2.setId(2L);
        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);

        user.setRoles(roles);

        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(role1));
        assertTrue(user.getRoles().contains(role2));
    }


}


