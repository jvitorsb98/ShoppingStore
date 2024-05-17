package br.com.cepedi.ShoppingStore.model.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_attribute")
public class ProductAttribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "Value cannot be blank")
	private String value;
	
	
	//Quando criar a tabela produto, criar um product_id para fazer relação
	
}
