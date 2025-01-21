package ForoHub.ByWolf.service;

import ForoHub.ByWolf.error.TopicNotFoundException;
import ForoHub.ByWolf.models.Topic;
import ForoHub.ByWolf.models.dto.TopicDataDetails;
import ForoHub.ByWolf.models.dto.TopicDataList;
import ForoHub.ByWolf.models.dto.TopicRegistryData;
import ForoHub.ByWolf.models.dto.TopicUpdateData;
import ForoHub.ByWolf.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    private final PagedResourcesAssembler<TopicDataList> assembler;


    public TopicService(TopicRepository topicRepository, PagedResourcesAssembler<TopicDataList> assembler) {
        this.topicRepository = topicRepository;
        this.assembler = assembler;
    }


    public ResponseEntity<String> createTopic(TopicRegistryData topicRegistryData) {
        if (topicRegistryData.title() == null || topicRegistryData.message() == null) {
            return ResponseEntity.badRequest().body("El Título y El mensaje son requeridos.");
        }

        if (topicRepository.existsByTitleAndMessage(topicRegistryData.title(), topicRegistryData.message())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tópico con este título y mensaje ya existe");
        }

        Topic topic = new Topic();
        topic.setTitle(topicRegistryData.title());
        topic.setMessage(topicRegistryData.message());
        topic.setAuthor(topicRegistryData.author());
        topic.setCreationDate(topicRegistryData.creation_date());
        topic.setStatus(topicRegistryData.status());
        topic.setCourse(topicRegistryData.course());

        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body("El Tópico creado exitosamente");
    }
    //########################


    public ResponseEntity<Object> readTopic(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Es requerido el 'ID'");
        }

        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        TopicDataDetails topicDataDetails = new TopicDataDetails(topic);
        return ResponseEntity.ok(topicDataDetails);
    }

//##############################

    public ResponseEntity<String> updateTopic(Long id, TopicUpdateData topicUpdateData) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        if (topicRepository.existsByTitleAndMessage(topicUpdateData.title(), topicUpdateData.message())) {
            return ResponseEntity.badRequest().body("El Tópico con ese título y mensaje ya existe");
        }

        // Actualizar los campos del tópico
        topic.setTitle(topicUpdateData.title());
        topic.setMessage(topicUpdateData.message());
        topic.setStatus(topicUpdateData.status());
        topic.setAuthor(topicUpdateData.author());
        topic.setCourse(topicUpdateData.course());

        topicRepository.save(topic);
        return ResponseEntity.ok("El Tópico fue actualizado exitosamente");
    }

//##############################

    public ResponseEntity<Void> deleteTopic(Long id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//##############################

    public ResponseEntity<PagedModel<EntityModel<TopicDataList>>> listTopics(Pageable pageable) {
        Page<TopicDataList> listPage = topicRepository.findAll(pageable).map(TopicDataList::new);
        PagedModel<EntityModel<TopicDataList>> pagedModel = assembler.toModel(listPage);
        return ResponseEntity.ok(pagedModel);
    }
}
