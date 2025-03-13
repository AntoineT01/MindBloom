package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.CategorieDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion des catégories.
 */
@Transactional
public interface CategorieService {

    /**
     * Récupère la liste de toutes les catégories existantes.
     */
    List<CategorieDto> findAll();

    /**
     * Récupère une catégorie par son identifiant.
     */
    CategorieDto findById(@NotNull Long id);

    /**
     * Crée une nouvelle catégorie.
     */
    CategorieDto create(CategorieDto dto);

    /**
     * Met à jour une catégorie existante par son identifiant.
     */
    CategorieDto updateById(Long id, CategorieDto dto);

    /**
     * Supprime (ou marque comme supprimée) une catégorie par son id.
     */
    Object deleteById(Long id);
}
