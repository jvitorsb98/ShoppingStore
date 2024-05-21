package br.com.cepedi.ShoppingStore.security.model.entitys;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Role Test - Random Order")
public class RoleTest {

    @Test
    @DisplayName("Test Role constructor with name")
    void testRoleConstructor() {
        Role role = new Role("ADMIN");
        assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Test adding user to role")
    void testAddingUserToRole() {
        Role role = new Role("USER");
        User user = new User();
        role.getUsers().add(user);

        assertEquals(1, role.getUsers().size());
        assertTrue(role.getUsers().contains(user));
    }

    @Test
    @DisplayName("Test removing user from role")
    void testRemovingUserFromRole() {
        Role role = new Role("MANAGER");
        User user = new User();
        role.getUsers().add(user);

        assertEquals(1, role.getUsers().size());

        role.getUsers().remove(user);
        assertEquals(0, role.getUsers().size());
    }

    @Test
    @DisplayName("Test equals method")
    void testEquals() {
        Role role1 = new Role("ROLE_ADMIN");
        role1.setId(1L);
        Role role2 = new Role("ROLE_ADMIN");
        role2.setId(1L);
        Role role3 = new Role("ROLE_USER");
        role3.setId(2L);

        assertEquals(role1, role2); // Deve ser igual
        assertNotEquals(role1, role3); // Deve ser diferente
    }

    @Test
    @DisplayName("Test canEquals method")
    void testCanEquals() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_ADMIN");
        Object obj = new Object();

        assertTrue(role1.canEqual(role2)); // Deve ser true
        assertFalse(role1.canEqual(obj)); // Deve ser false
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
        role.setUsers(new HashSet<>());

        assertEquals(1L, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
        assertNotNull(role.getUsers());
    }

}
