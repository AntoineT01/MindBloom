package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.SessionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité SessionResponse.
 */
@Repository
public interface SessionResponseRepository extends JpaRepository<SessionResponse, Long> {
    // Vous pouvez définir ici des méthodes de requête personnalisées si nécessaire.
}
