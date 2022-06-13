package AlexTh.repository;

import AlexTh.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findAllByNameAndGenreAndDeveloper(String name, String genre, String developer, Pageable pageable);
    Page<Game> findAllByNameAndGenre(String name, String genre, Pageable pageable);
    Page<Game> findAllByGenreAndDeveloper(String genre, String developer, Pageable pageable);
    Page<Game> findAllByGenre(String genre, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Game g set g.rank=:rank where g.id=:gameId")
    void updateGameRank(@Param("gameId") Long gameId, @Param("rank") Double rank);


    @Query(value = "select game from Game game order by rank desc")
    List<Game> getGames(Pageable limit);


}
