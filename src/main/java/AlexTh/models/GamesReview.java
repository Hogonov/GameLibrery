package AlexTh.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="games_review")
public class GamesReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "game_id")
    Long gameId;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "rank")
    Integer rank;

    public GamesReview(Long gameId, Long userId, Integer rank) {
        this.gameId = gameId;
        this.userId = userId;
        this.rank = rank;
    }
}
