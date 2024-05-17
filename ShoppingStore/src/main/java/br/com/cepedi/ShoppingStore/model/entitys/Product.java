package br.com.cepedi.ShoppingStore.model.entitys;

import java.math.BigDecimal;
import java.util.List;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
	    private Integer quantity;
	    private String manufacturer;
	    private boolean featured;

	    @JoinColumn(name = "category_id")
	    @ManyToOne 
	    private Category category;

//	    @OneToMany(mappedBy = "product")
//	    private List<ProductAttribute> productAttributeList;
//
//	    @OneToMany(mappedBy = "product")
//	    private List<ProductRating> productRating;


		public Product(DataRegisterProduct data, Category category){
			this.name = data.name();
			this.description = data.description();
			this.price = data.price();
			this.sku = data.sku();
			this.imageUrl = data.imageUrl();
			this.quantity = data.quantity();
			this.manufacturer = data.manufacturer();
			this.featured = data.featured();
			this.category = category;
		}
}
