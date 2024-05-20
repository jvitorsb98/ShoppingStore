package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product_attribute")
public class ProductAttribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "Value cannot be blank")
	private String value;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	public ProductAttribute(DataRegisterProductAttribute data, Product product){
		this.name = data.name();
		this.value = data.value();
		this.product = product;
	}
	
}
