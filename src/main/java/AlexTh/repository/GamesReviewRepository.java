package AlexTh.repository;

import AlexTh.models.GamesReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GamesReviewRepository extends JpaRepository<GamesReview, Long> {
    //GamesReview findAllByGame_idAndAndUser_id(Long game_id, Long user_id);
    GamesReview findByGameIdAndUserId(Long gameId, Long userId);
    List<GamesReview> findAllByGameId(Long gameId);

    @Transactional
    @Modifying
    @Query("update GamesReview gr set gr.rank=:rank where gr.gameId=:gameId and gr.userId=:userId")
    void updateRank(@Param("userId") Long userId, @Param("gameId") Long gameId, @Param("rank") Integer rank);
}
