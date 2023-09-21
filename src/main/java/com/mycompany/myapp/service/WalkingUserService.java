package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Step;
import com.mycompany.myapp.domain.WalkingUser;
import com.mycompany.myapp.repository.WalkingUserRepository;
import com.mycompany.myapp.service.dto.WalkingTotalStepsDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalkingUserService {
    private final WalkingUserRepository walkingUserRepository;

    public WalkingUserService(WalkingUserRepository walkingUserRepository) {
        this.walkingUserRepository = walkingUserRepository;
    }

    public Long findTotalStepsByUserIdAndDate(String userId, LocalDate start, LocalDate end) {
        return walkingUserRepository.findTotalStepsWeeklyByUserIdAndDate(userId, start, end);
    }

    public List<WalkingTotalStepsDTO> getTotalStepsRanking() {
        return walkingUserRepository.getTotalStepsRankingByDate(LocalDateTime.now().toLocalDate());
    }
}
