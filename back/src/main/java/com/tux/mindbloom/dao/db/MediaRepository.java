package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité Media.
 */
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    // Vous pouvez définir ici des méthodes de requête personnalisées si nécessaire.
}
