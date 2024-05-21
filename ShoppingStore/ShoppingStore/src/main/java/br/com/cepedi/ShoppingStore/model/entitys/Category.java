package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean disabled;


    public Category(DataRegisterCategory data){
        this.name = data.name();
        this.description = data.description();
        this.disabled = false;
    }

    public void updateDataCategory(DataUpdateCategory data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.description() != null) {
            this.description = data.description();
        }
    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }

}
