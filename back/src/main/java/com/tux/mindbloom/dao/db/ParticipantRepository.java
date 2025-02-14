package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité Participant.
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    // Méthodes de requête personnalisées si nécessaire.
}
