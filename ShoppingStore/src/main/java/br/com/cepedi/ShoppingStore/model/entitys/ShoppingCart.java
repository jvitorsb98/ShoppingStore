package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
//import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "total_price", nullable = false)
    @NotBlank(message = "Price must be a valid valor")
    @PositiveOrZero(message = "Total price must be a positive valor or zero")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean disabled;

//    @OneToMany(mappedBy = "shopping_cart", fetch = FetchType.LAZY)
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Set<ShoppingCartItem> shoppingCartItems = new HashSet<>();

    public ShoppingCart(User user){
        this.totalPrice = BigDecimal.ZERO;
        this.user = user;
        this.disabled = false;
    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }
}
