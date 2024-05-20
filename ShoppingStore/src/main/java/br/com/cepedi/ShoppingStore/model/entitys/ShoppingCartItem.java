package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
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
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "price_purchase")
    private BigDecimal pricePurchase;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;


    private BigInteger quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Boolean disabled;


    public ShoppingCartItem(DataRegisterShoppingCartItem data, ShoppingCart shoppingCart , Product product){
        this.name = product.getName();
        this.pricePurchase = product.getPrice();
        this.shoppingCart = shoppingCart;
        this.product = product;
        this.quantity = data.quantity();
        this.disabled = false;
    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }

}
