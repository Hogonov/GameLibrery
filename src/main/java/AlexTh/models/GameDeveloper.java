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
@Table(name="games_developers")
public class GameDeveloper {
    @Id
    @Column(name = "game_id")
    Long gameId;
    @Column(name = "developer_id")
    Long devId;


    public GameDeveloper(Long gameId,Long devId) {
        this.gameId = gameId;
        this.devId = devId;
    }
}
