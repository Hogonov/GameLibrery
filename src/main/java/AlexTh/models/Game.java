package AlexTh.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    @NotBlank
    String name;

    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "games_developers",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    String developer;

    @Column(name = "year")
    @Min(value = 1, message = "Too old")
    Integer year;



    // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre"))
    String genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "games_review",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "rank"))
    List<GamesReview> rankList;

    @Column(name = "rank")
    @Min(value = 0, message = "Min value is 0")
    @Max(value = 10, message = "Max value is 10")
    Double rank;
    @Column(name = "description")
    @NotBlank(message = "Empty field")
    String description;





    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", developer=" + developer +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", rank=" + rank +
                ", description='" + description + '\'' +
                '}';
    }

}
