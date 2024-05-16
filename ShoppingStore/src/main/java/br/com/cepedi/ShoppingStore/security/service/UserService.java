package br.com.cepedi.ShoppingStore.security.service;


import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User getUserActivatedByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
;            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.saveAndFlush(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }



}
