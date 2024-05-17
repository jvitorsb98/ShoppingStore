package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
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
@Table(name = "product_rating")
public class ProductRating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

//        @Column(name = "product_id")
//        private Product product;

        @Column(name = "rating_stars")
        private BigDecimal ratingStars;

        @Column(name = "review")
        private String review;

        @Column(name = "user_id")
        private User user;

//        public ProductRating(DataRegisterProductRating data , User user , Product product){
//
//        }

}
