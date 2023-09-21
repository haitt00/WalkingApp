package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Step;
import com.mycompany.myapp.domain.WalkingUser;
import com.mycompany.myapp.repository.WalkingUserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.WalkingUserService;
import com.mycompany.myapp.service.dto.WalkingTotalStepsDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.WalkingUser}.
 */
@RestController
@RequestMapping("/api")
public class WalkingUserResource {

    private final Logger log = LoggerFactory.getLogger(WalkingUserResource.class);

    private static final String ENTITY_NAME = "walkingUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WalkingUserRepository walkingUserRepository;

    private final WalkingUserService walkingUserService;

    public WalkingUserResource(WalkingUserRepository walkingUserRepository, WalkingUserService walkingUserService) {
        this.walkingUserRepository = walkingUserRepository;
        this.walkingUserService = walkingUserService;
    }

    @PostMapping("steps")
    public ResponseEntity<Step> updateSteps(@RequestBody Step step) {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();
        WalkingUser walkingUser = walkingUserRepository.findByUserId(login).orElseGet(() -> {
            WalkingUser wu = new WalkingUser();
            wu.setUserId(login);
            wu.setSteps(new ArrayList<>());
            return wu;
        });
        List<Step> steps = Stream.concat(walkingUser.getSteps().stream().filter(s -> !s.getDate().isEqual(step.getDate())), Stream.of(step)).toList();
        walkingUser.setSteps(steps);
        walkingUserRepository.save(walkingUser);
        return ResponseEntity.ok(step);

    }

    @PostMapping("steps/ranking")
    public ResponseEntity<List<WalkingTotalStepsDTO>> getStepsRankingByDay() {
        return ResponseEntity.ok(walkingUserService.getTotalStepsRanking());

    }

    @GetMapping("steps/weekly")
    public ResponseEntity<Long> getTotalStepsByWeekly() {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();

        LocalDate end = LocalDateTime.now().toLocalDate();
        LocalDate start = end.minusDays(end.getDayOfWeek().getValue() - 1);
        return ResponseEntity.ok(walkingUserService.findTotalStepsByUserIdAndDate(login, start, end));
    }

    @GetMapping("steps/monthly")
    public ResponseEntity<Long> getTotalStepsMonthly() {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow();

        LocalDate end = LocalDateTime.now().toLocalDate();
        LocalDate start = end.minusDays(end.getDayOfMonth() - 1);
        return ResponseEntity.ok(walkingUserService.findTotalStepsByUserIdAndDate(login, start, end));
    }
}
