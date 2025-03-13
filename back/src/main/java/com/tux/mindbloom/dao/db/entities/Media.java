package com.tux.mindbloom.dao.db.entities;

import com.tux.mindbloom.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Représentation d'un média lié à une question (ou tout autre objet si nécessaire).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'identifiant de la question à laquelle ce média est associé.
     * (Si vous avez une relation directe @ManyToOne, vous pouvez l'ajuster en conséquence.)
     */
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    /**
     * Le type de média : image, vidéo ou audio.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MediaType type;

    /**
     * L'URL du média (emplacement où est hébergé le média).
     */
    @Column(name = "url", nullable = false, length = 255)
    private String url;

}
