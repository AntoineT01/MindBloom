package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.LeaderboardController;
import com.tux.mindbloom.api.models.LeaderboardDto;
import com.tux.mindbloom.business.LeaderboardService;
import com.tux.mindbloom.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implémentation de {@link LeaderboardController}.
 */
@Slf4j
@RestController
public class LeaderboardControllerImpl extends AbstractRestController implements LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardControllerImpl(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Override
    public List<LeaderboardDto> findAll() {
        log.info("Rest : GET - {}", Constants.Api.LEADERBOARD);
        return logResponse(log, "GET", leaderboardService.findAll());
    }

    @Override
    public LeaderboardDto create(LeaderboardDto dto) {
        log.info("Rest : POST - {}", Constants.Api.LEADERBOARD);
        return logResponse(log, "POST", leaderboardService.create(dto));
    }

    @Override
    public LeaderboardDto updateById(Long id, LeaderboardDto dto) {
        log.info("Rest : PUT - {}", Constants.Api.LEADERBOARD);
        return logResponse(log, "PUT", leaderboardService.updateById(id, dto));
    }

    @Override
    public Object deleteById(Long id) {
        log.info("Rest : DELETE - {}", Constants.Api.LEADERBOARD);
        return logResponse(log, "DELETE", leaderboardService.deleteById(id));
    }
}
