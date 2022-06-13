package AlexTh.repository;

import AlexTh.models.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Page<Developer> findAllByName(String name, Pageable pageable);

    Page<Developer> findAll(Pageable pageable); 

    Developer findOneByName(String devName);

    @Transactional
    @Modifying
    @Query("update Developer d set d.rank=:rank where d.id=:devId")
    void updateDevRank(@Param("devId") Long devId, @Param("rank") Double rank);
}
