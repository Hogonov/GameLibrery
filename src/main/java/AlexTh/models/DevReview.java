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
@Table(name="developers_review")
public class DevReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "dev_id")
    Long devId;
    @Column(name = "user_id")
    Long userId;
    @Column(name = "rank")
    Integer rank;

    public DevReview(Long devId, Long userId, Integer rank) {
        this.devId = devId;
        this.userId = userId;
        this.rank = rank;
    }
}
