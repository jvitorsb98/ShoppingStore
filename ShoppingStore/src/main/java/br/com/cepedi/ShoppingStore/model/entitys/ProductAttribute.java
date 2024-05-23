package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "product_attribute")
public class ProductAttribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private String value;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private Boolean disabled;


	public ProductAttribute(DataRegisterProductAttribute data, Product product){
		this.name = data.name();
		this.value = data.value();
		this.product = product;
		this.disabled = false;
	}

	public void disable() {
		this.disabled = true;
	}

	public void enable() {
		this.disabled = false;
	}

	public void updateProductAttribute(DataUpdateProductAttribute data){
		if (data.name() != null){
			this.name = data.name();
		}
		if (data.value() != null){
			this.value = data.value();
		}
	}
}
