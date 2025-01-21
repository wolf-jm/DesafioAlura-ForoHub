package ForoHub.ByWolf.models.dto;

import ForoHub.ByWolf.models.Course;
import ForoHub.ByWolf.models.Status;
import ForoHub.ByWolf.models.Topic;

import java.time.LocalDateTime;

public record TopicDataDetails(

        String title,
        String message,
        LocalDateTime creationDate,
        Status status,
        String author,
        Course course
) {
    public TopicDataDetails(Topic topic) {
        this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(),
                topic.getStatus(), topic.getAuthor(), topic.getCourse());
    }
}
