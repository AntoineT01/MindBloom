package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.QuizCategoriesDto;
import com.tux.mindbloom.business.QuizCategoriesService;
import com.tux.mindbloom.business.mappers.QuizCategoriesMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.QuizCategoriesRepository;
import com.tux.mindbloom.dao.db.entities.QuizCategories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.QuizCategoriesService}.
 */
@Service
public class QuizCategoriesServiceImpl implements QuizCategoriesService {

    private final QuizCategoriesRepository repository;
    private final QuizCategoriesMapper mapper;

    public QuizCategoriesServiceImpl(QuizCategoriesRepository repository, QuizCategoriesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<QuizCategoriesDto> findAll() {
        return mapper.toDtos(repository.findAll());
    }

    @Override
    public QuizCategoriesDto findById(Long quizId, Long categoryId) {
        QuizCategories.QuizCategoriesId id = new QuizCategories.QuizCategoriesId(quizId, categoryId);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("QuizCategories", quizId, categoryId));
    }

    @Override
    public QuizCategoriesDto create(QuizCategoriesDto dto) {
        // On mappe le DTO en entité
        QuizCategories entity = mapper.toEntity(dto);

        // Sauvegarde
        QuizCategories created = repository.save(entity);

        // Retour en DTO
        return mapper.toDto(created);
    }

    @Override
    public QuizCategoriesDto updateById(Long quizId, Long categoryId, QuizCategoriesDto dto) {
        // On vérifie si la liaison existe
        QuizCategories.QuizCategoriesId id = new QuizCategories.QuizCategoriesId(quizId, categoryId);
        Optional<QuizCategories> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException("QuizCategories", quizId, categoryId);
        }

        // Dans une table de liaison, "mettre à jour" revient souvent au même
        // que créer une nouvelle liaison ou supprimer puis recréer.
        // Mais on peut imaginer qu'on autorise le "renaming" du quizId/categoryId.
        // Ici on va simplement mettre à jour si c'est la même clé.
        QuizCategories existing = optional.get();

        // On met à jour. (Ici, c'est bizarre, mais on suit le schéma.)
        existing.setQuizId(dto.getQuizId());
        existing.setCategoryId(dto.getCategoryId());

        // Sauvegarde
        QuizCategories updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public Object deleteById(Long quizId, Long categoryId) {
        QuizCategories.QuizCategoriesId id = new QuizCategories.QuizCategoriesId(quizId, categoryId);

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("QuizCategories", quizId, categoryId);
        }

        repository.deleteById(id);
        return true;
    }
}
