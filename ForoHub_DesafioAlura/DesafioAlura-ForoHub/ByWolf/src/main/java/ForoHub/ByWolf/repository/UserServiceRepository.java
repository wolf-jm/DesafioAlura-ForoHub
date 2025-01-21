package ForoHub.ByWolf.repository;

import ForoHub.ByWolf.models.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserServiceRepository extends JpaRepository<UserService, Long> {
    UserDetails findByLogin(String username);
}
