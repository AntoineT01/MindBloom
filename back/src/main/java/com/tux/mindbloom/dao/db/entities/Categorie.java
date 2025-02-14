package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Représente une catégorie de quiz, de session, etc.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Le nom de la catégorie.
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * La description de la catégorie.
     */
    @Column(name = "description")
    private String description;
}
