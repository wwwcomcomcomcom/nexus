package avengers.nexus.user.repository;

import avengers.nexus.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findById(long id);
    Optional<User> findByName(String name);
    Optional<User> findByGithubId(long githubId);
}
