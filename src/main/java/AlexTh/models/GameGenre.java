package AlexTh.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="game_genre")
public class GameGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "game_id")
    Long gameId;
    @NotBlank
    @Column(name = "genre")
    String genre;

    public GameGenre(Long gameId, String genre) {
        this.gameId = gameId;
        this.genre = genre;
    }
}
