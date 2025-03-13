package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.MediaDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion des médias.
 */
@Transactional
public interface MediaService {

    /**
     * Récupère la liste de tous les médias existants.
     *
     * @return la liste des MediaDto
     */
    List<MediaDto> findAll();

    /**
     * Récupère un média par son identifiant.
     *
     * @param id l'id du média
     * @return le MediaDto correspondant
     */
    MediaDto findById(@NotNull Long id);

    /**
     * Crée un nouveau média à partir d'un DTO.
     *
     * @param dto les informations du média à créer
     * @return le MediaDto créé
     */
    MediaDto create(MediaDto dto);

    /**
     * Met à jour un média existant.
     *
     * @param id  l'id du média à mettre à jour
     * @param dto les informations à mettre à jour
     * @return le MediaDto après mise à jour
     */
    MediaDto updateById(Long id, MediaDto dto);

    /**
     * Supprime (ou « soft-delete ») un média par son id.
     *
     * @param id l'id du média
     * @return un objet représentant le résultat de la suppression (true ou un objet de confirmation)
     */
    Object deleteById(Long id);
}
