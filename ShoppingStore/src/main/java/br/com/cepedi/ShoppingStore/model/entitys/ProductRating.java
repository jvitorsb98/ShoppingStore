package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
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

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "product_id")
        private Product product;

        @Column(name = "rating_stars")
        private BigDecimal ratingStars;

        @Column(name = "review")
        private String review;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        private User user;

        private Boolean disabled;


        public ProductRating(DataRegisterProductRating data , User user , Product product){
                this.ratingStars = data.ratingStars();
                this.review = data.review();
                this.user = user;
                this.product = product;
                this.disabled = false;
        }

        public void updateDataProductRating(DataUpdateProductRating data, User user , Product product) {
                if (data.ratingStars() != null){
                        this.ratingStars = data.ratingStars();
                }
                if (data.review() != null){
                        this.review = data.review();
                }

                if (data.userId() != null){
                        this.user = user;
                }
                if (data.productId() != null){
                        this.product = product;
                }
        }



        public void disable() {
                this.disabled = true;
        }

        public void enable() {
                this.disabled = false;
        }

}
