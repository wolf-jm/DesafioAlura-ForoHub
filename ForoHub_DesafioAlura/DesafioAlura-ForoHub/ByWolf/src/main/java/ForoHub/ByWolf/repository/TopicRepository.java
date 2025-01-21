package ForoHub.ByWolf.repository;

import ForoHub.ByWolf.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitleAndMessage(String title, String message);
}
