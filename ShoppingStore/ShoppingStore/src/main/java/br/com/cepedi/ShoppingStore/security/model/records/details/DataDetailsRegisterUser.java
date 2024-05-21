package br.com.cepedi.ShoppingStore.security.model.records.details;


import br.com.cepedi.ShoppingStore.security.model.entitys.User;

public record DataDetailsRegisterUser(

        String login,

        String name,

        String email

) {




    public DataDetailsRegisterUser(User user) {
        this(user.getLogin(),user.getName(),user.getEmail());
    }
}
