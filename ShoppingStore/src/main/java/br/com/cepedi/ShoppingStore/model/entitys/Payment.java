package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "disabled")
    private boolean disabled = false;

    public Payment(DataRegisterPayment dataRegisterPayment , ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
        this.paymentType = dataRegisterPayment.paymentType();
        this.disabled = false;
    }

    public void disable() {
        this.disabled = true;
    }


}
