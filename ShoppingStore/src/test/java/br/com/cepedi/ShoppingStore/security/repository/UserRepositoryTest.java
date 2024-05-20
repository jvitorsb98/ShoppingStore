package br.com.cepedi.ShoppingStore.security.repository;


import br.com.cepedi.ShoppingStore.config.TestConfig;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void delete(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test find by login")
    public void testFindByLoginToUserRepository() {
        // Dado
        String login = "testuser";
        String email = "testuser@example.com";
        String name = "Test User";
        String password = "password123*";
        DataRegisterUser dataRegisterUser = new DataRegisterUser(login, email, name, password);

        userRepository.save(new User(dataRegisterUser, TestConfig.passwordEncoder()));


        // Quando
        UserDetails userDetails = userRepository.findByLogin(login);

        // Então
        assertNotNull(userDetails);
    }

    @Test
    @DisplayName("Test find by e-mail")
    public void testFindUserByEmail() {
        // Dado
        String login = "testuser";
        String email = "testuser@example.com";
        String name = "Test User";
        String password = "password123";
        DataRegisterUser dataRegisterUser = new DataRegisterUser(login, email, name, password);

        userRepository.save(new User(dataRegisterUser, TestConfig.passwordEncoder()));

        // Quando
        User user = userRepository.findUserByEmail(email);

        // Então
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Test persistence of user")
    public void testUserPersistence() {
        // Dado
        String login = "testuser";
        String email = "testuser@example.com";
        String name = "Test User";
        String password = "password123";
        DataRegisterUser dataRegisterUser = new DataRegisterUser(login, email, name, password);

        // Quando
        User savedUser = userRepository.save(new User(dataRegisterUser, TestConfig.passwordEncoder()));

        // Então
        assertNotNull(savedUser.getId());
        assertEquals(login, savedUser.getLogin());
        assertEquals(email, savedUser.getEmail());
        assertEquals(name, savedUser.getName());
        assertTrue(TestConfig.passwordEncoder().matches(password, savedUser.getPassword()));
    }

    @Test
    @DisplayName("Test find by login when user does not exist")
    public void testFindByLoginWhenUserDoesNotExist() {
        // Dado
        String nonExistentLogin = "nonexistentuser";

        // Quando
        UserDetails userDetails = userRepository.findByLogin(nonExistentLogin);

        // Então
        assertNull(userDetails);
    }

    @Test
    @DisplayName("Test find by email when user does not exist")
    public void testFindByEmailWhenUserDoesNotExist() {
        // Dado
        String nonExistentEmail = "nonexistentuser@example.com";

        // Quando
        User user = userRepository.findUserByEmail(nonExistentEmail);

        // Então
        assertNull(user);
    }




}
