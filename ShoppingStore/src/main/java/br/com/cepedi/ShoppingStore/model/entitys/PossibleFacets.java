package br.com.cepedi.ShoppingStore.model.entitys;


import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "possible_facets")
public class PossibleFacets {
    
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Name of the possible facet
    @Column(name = "name")
    private String name;
    
    // Category ID associated with the possible facet
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    private Boolean disabled;


    public PossibleFacets(DataRegisterPossibleFacets data , Category category ){
        this.name = data.name();
        this.category = category;
        this.disabled = false;
    }

    public void updateDataPossibleFacets(DataUpdatePossibleFacets data  , Category category) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (category != null) {
            this.category = category;
        }

    }

    public void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }


}
