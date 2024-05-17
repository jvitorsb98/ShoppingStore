package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.records.ProductCartItem.input.DataRegisterProductCartItem;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "shopping_cart_item")
public class ProductCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "price_purchase")
    private BigDecimal pricePurchase;

    @ManyToOne
    @JoinColumn(name = "shopping_cart")
    private ShoppingCart shoppingCart;


    private BigInteger quantity;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

//    public ProductCartItem(DataRegisterProductCartItem data, ShoppingCart shoppingCart , BigInteger quantity , Product product){
//
//    }

}
