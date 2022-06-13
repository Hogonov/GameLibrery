package AlexTh.repository;

import AlexTh.models.DevReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DevReviewRepository extends JpaRepository<DevReview, Long> {
    //GamesReview findAllByGame_idAndAndUser_id(Long game_id, Long user_id);
    DevReview findByDevIdAndUserId(Long devId, Long userId);
    List<DevReview> findAllByDevId(Long devId);

    @Transactional
    @Modifying
    @Query("update DevReview devR set devR.rank=:rank where devR.devId=:devId and devR.userId=:userId")
    void updateRank(@Param("userId") Long userId, @Param("devId") Long devId, @Param("rank") Integer rank);
}
