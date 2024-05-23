package br.com.cepedi.ShoppingStore.model.entitys;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	    private String name;
	    private String description;
	    private BigDecimal price;
	    private String sku;
	    private String imageUrl;  
	    private BigInteger quantity;

	    private Boolean featured;

		private Boolean disabled;

		@JoinColumn(name = "brand_id")
		@ManyToOne
		private Brand brand;

		@JoinColumn(name = "category_id")
	    @ManyToOne 
	    private Category category;

//	    @OneToMany(mappedBy = "product")
//	    private List<ProductAttribute> productAttributeList;
//
//	    @OneToMany(mappedBy = "product")
//	    private List<ProductRating> productRating;


		public Product(DataRegisterProduct data, Category category, Brand brand){
			this.name = data.name();
			this.description = data.description();
			this.price = data.price();
			this.sku = data.sku();
			this.imageUrl = data.imageUrl();
			this.quantity = data.quantity();
			this.brand = brand;
			this.featured = data.featured();
			this.category = category;
			this.disabled = false;
		}

		public void disable() {
			this.disabled = true;
		}

		public void enable() {
			this.disabled = false;
		}

		public void updateDataProduct(DataUpdateProduct data, Category category) {
			 if(data.name() != null){
				 this.name = data.name();
			 }
			 if(data.description() != null){
				 this.description = data.description();
			 }
			 if(data.price() != null){
				 this.price = data.price();
			 }
			 if(data.sku() != null){
				 this.sku = data.sku();
			 }
			 if(data.imageUrl() != null){
				 this.imageUrl = data.imageUrl();
			 }
			 if(data.quantity() != null){
				 this.quantity = data.quantity();
			 }
			 if(brand != null){
				 this.brand = brand;
			 }

			 if(category!= null){
				 this.category = category;
			 }


//			 if(data.featured() != null){
//				 this.featured = data.featured();
//			 }
			
		}
}
