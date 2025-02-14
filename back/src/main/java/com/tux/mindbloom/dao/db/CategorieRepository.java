package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la base de données pour l'entité Categorie.
 */
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Méthodes de requête personnalisées si nécessaire.
}
