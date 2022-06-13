package AlexTh.repository;

import AlexTh.models.GameGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {
}
