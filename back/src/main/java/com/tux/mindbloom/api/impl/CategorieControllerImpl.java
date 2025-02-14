package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.CategorieController;
import com.tux.mindbloom.api.models.CategorieDto;
import com.tux.mindbloom.business.CategorieService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link CategorieController}.
 */
@Slf4j
@RestController
public class CategorieControllerImpl extends AbstractRestController implements CategorieController {

    private final CategorieService categorieService;

    public CategorieControllerImpl(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public List<CategorieDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.CATEGORIES);
        return logResponse(log, "GET", categorieService.findAll());
    }

    @Override
    public CategorieDto create(CategorieDto dto) {
        log.info("Rest : POST - {}", Constants.Api.CATEGORIES);
        return logResponse(log, "POST", categorieService.create(dto));
    }

    @Override
    public CategorieDto updateById(Long id, CategorieDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.CATEGORIES);
        return logResponse(log, "PUT", categorieService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.CATEGORIES);
        return logResponse(log, "DELETE", categorieService.deleteById(id));
    }
}
