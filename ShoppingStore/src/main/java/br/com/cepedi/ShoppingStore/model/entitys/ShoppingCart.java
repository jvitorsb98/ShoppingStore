package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public ShoppingCart(DataRegisterShoppingCart data, User user){
        this.totalPrice = data.price();
        this.user = user;
    }


}
