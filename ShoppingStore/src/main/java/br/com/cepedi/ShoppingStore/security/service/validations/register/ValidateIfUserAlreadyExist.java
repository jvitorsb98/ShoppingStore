package br.com.cepedi.ShoppingStore.security.service.validations.register;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfUserAlreadyExist implements ValidationRegisterUser{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validation(DataRegisterUser data){
        User user = userRepository.findUserByEmail(data.email());
        UserDetails details = userRepository.findByLogin(data.login());
        if (user != null || details != null){
            throw new ValidationException("The user already exist");
        }
    }
}
