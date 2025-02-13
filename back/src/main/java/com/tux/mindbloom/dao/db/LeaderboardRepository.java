package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité Leaderboard.
 */
@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {
    // Vous pouvez définir ici des méthodes de requête personnalisées si nécessaire.
}
