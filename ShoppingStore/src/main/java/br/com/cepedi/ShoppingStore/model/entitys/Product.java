package br.com.cepedi.ShoppingStore.model.entitys;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	    private Category category;
	    private Long categoryId;
//	    private List<ProductAttribute> productAttributeList;
	    private Integer quantity;
	    private String manufacturer;
	    private boolean featured;
//	    private List<ProductRating> productRating;
}
