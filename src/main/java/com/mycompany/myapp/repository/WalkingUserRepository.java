package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.WalkingUser;
import com.mycompany.myapp.service.dto.WalkingTotalStepsDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the WalkingUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalkingUserRepository extends MongoRepository<WalkingUser, String> {
    Optional<WalkingUser> findByUserId(String userId);

    @Aggregation(
        pipeline = {
            "{ $unwind: '$steps' }",
            "{ $match: { 'steps.date': { $gte: ?1, $lte: ?2 }, 'userId': ?0  }}",
            "{ $group: { _id: null, count: { $sum: '$steps.count' } } }",
            "{ $project: { _id: 0, count: 1 } }",
        }
    )
    Long findTotalStepsWeeklyByUserIdAndDate(@Param("userId") String userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Aggregation(pipeline = {
        "{ $unwind: '$steps' }",
        "{ $match: { 'steps.date': ?0 } }",
        "{ $project: { userId: 1, count: '$steps.count' } }",
        "{ $sort: { count: -1 } }",
        "{ $limit: 5 }"
    })
    List<WalkingTotalStepsDTO> getTotalStepsRankingByDate(LocalDate date);
}
