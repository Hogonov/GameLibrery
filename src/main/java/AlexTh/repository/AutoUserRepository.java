package AlexTh.repository;

import AlexTh.models.AutoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AutoUserRepository extends JpaRepository<AutoUser, Long> {

    AutoUser findByUsername(String username);

}
