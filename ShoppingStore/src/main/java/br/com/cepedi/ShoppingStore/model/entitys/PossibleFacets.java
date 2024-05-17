package br.com.cepedi.ShoppingStore.model.entitys;


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
    
    // Constructors, getters, and setters will be automatically generated by Lombok
}
