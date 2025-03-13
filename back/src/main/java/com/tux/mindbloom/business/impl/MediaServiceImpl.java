package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.MediaDto;
import com.tux.mindbloom.business.MediaService;
import com.tux.mindbloom.business.mappers.MediaMapper;
import com.tux.mindbloom.config.Constants;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.MediaRepository;
import com.tux.mindbloom.dao.db.entities.Media;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.MediaService}.
 */
@Service
@RequestMapping(path = Constants.Api.MEDIA)
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }

    @Override
    public List<MediaDto> findAll() {
        // Récupération de tous les enregistrements en BDD
        List<Media> medias = mediaRepository.findAll();
        // Mapping en DTO
        return mediaMapper.toDtos(medias);
    }

    @Override
    public MediaDto findById(Long id) {
        // Recherche par ID
        return mediaRepository.findById(id)
                .map(mediaMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(Media.class.getSimpleName(), id));
    }

    @Override
    public MediaDto create(MediaDto dto) {
        // Conversion du DTO en entité
        Media media = mediaMapper.toEntity(dto);
        // Sauvegarde
        Media createdMedia = mediaRepository.save(media);
        // Conversion de l'entité persistée en DTO de sortie
        return mediaMapper.toDto(createdMedia);
    }

    @Override
    public MediaDto updateById(Long id, MediaDto dto) {
        // Récupération du média existant
        Optional<Media> optionalMedia = mediaRepository.findById(id);
        if (!optionalMedia.isPresent()) {
            throw new EntityNotFoundException(Media.class.getSimpleName(), id);
        }

        // Mise à jour des champs
        Media existingMedia = optionalMedia.get();
        existingMedia.setQuestionId(dto.getQuestionId());
        existingMedia.setType(dto.getType());
        existingMedia.setUrl(dto.getUrl());

        // Sauvegarde
        Media updatedMedia = mediaRepository.save(existingMedia);
        // Retour en DTO
        return mediaMapper.toDto(updatedMedia);
    }

    @Override
    public Object deleteById(Long id) {
        // Vérifier si l'entité existe
        if (!mediaRepository.existsById(id)) {
            throw new EntityNotFoundException(Media.class.getSimpleName(), id);
        }

        // Suppression définitive (ou logic si besoin)
        mediaRepository.deleteById(id);

        // On retourne par exemple un boolean ou un message
        return true;
    }
}
