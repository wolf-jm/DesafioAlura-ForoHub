package ForoHub.ByWolf.error;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(Long id) {
        super("Topico con ID: " + id + "no encontrado.");
    }
}
