package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.CategorieDto;
import com.tux.mindbloom.business.CategorieService;
import com.tux.mindbloom.business.mappers.CategorieMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.CategorieRepository;
import com.tux.mindbloom.dao.db.entities.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation de {@link com.tux.mindbloom.business.CategorieService}.
 */
@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    public CategorieServiceImpl(CategorieRepository categorieRepository,
                                CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    @Override
    public List<CategorieDto> findAll() {
        List<Categorie> allCategories = categorieRepository.findAll();
        return categorieMapper.toDtos(allCategories);
    }

    @Override
    public CategorieDto findById(Long id) {
        return categorieRepository.findById(id)
                .map(categorieMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(Categorie.class.getSimpleName(), id));
    }

    @Override
    public CategorieDto create(CategorieDto dto) {
        Categorie entity = categorieMapper.toEntity(dto);
        Categorie created = categorieRepository.save(entity);
        return categorieMapper.toDto(created);
    }

    @Override
    public CategorieDto updateById(Long id, CategorieDto dto) {
        Optional<Categorie> optional = categorieRepository.findById(id);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException(Categorie.class.getSimpleName(), id);
        }

        Categorie existing = optional.get();
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());

        Categorie updated = categorieRepository.save(existing);
        return categorieMapper.toDto(updated);
    }

    @Override
    public Object deleteById(Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new EntityNotFoundException(Categorie.class.getSimpleName(), id);
        }

        // Suppression définitive (ou soft-delete si besoin)
        categorieRepository.deleteById(id);

        return true;
    }
}
