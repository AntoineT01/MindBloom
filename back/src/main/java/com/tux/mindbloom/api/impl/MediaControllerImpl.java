package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.MediaController;
import com.tux.mindbloom.api.models.MediaDto;
import com.tux.mindbloom.business.MediaService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link MediaController}.
 */
@Slf4j
@RestController
public class MediaControllerImpl extends AbstractRestController implements MediaController {

    private final MediaService mediaService;

    public MediaControllerImpl(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public List<MediaDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.MEDIA);
        return logResponse(log, "GET", mediaService.findAll());
    }

    @Override
    public MediaDto create(MediaDto dto) {
        log.info("Rest : POST - {}", Constants.Api.MEDIA);
        return logResponse(log, "POST", mediaService.create(dto));
    }

    @Override
    public MediaDto updateById(Long id, MediaDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.MEDIA);
        return logResponse(log, "PUT", mediaService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.MEDIA);
        return logResponse(log, "DELETE", mediaService.deleteById(id));
    }
}
