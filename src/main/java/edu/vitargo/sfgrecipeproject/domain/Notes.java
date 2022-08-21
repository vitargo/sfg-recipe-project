package edu.vitargo.sfgrecipeproject.domain;

import lombok.*;

import javax.persistence.*;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
