package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.Tag;
import fr.istic.ludecol.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Tag.
 */
@RestController
@RequestMapping("/api")
public class TagResource {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    @Inject
    private TagRepository tagRepository;

    /**
     * POST  /tags -> Create a new tag.
     */
    @RequestMapping(value = "/tags",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Tag tag) {
        log.debug("REST request to save Tag : {}", tag);
        tagRepository.save(tag);
    }

    /**
     * GET  /tags -> get all the tags.
     */
    @RequestMapping(value = "/tags",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Tag> getAll() {
        log.debug("REST request to get all Tags");
        return tagRepository.findAll();
    }

    /**
     * GET  /tags/:id -> get the "id" tag.
     */
    @RequestMapping(value = "/tags/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tag> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Tag : {}", id);
        Tag tag = tagRepository.findOne(id);
        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * DELETE  /tags/:id -> delete the "id" tag.
     */
    @RequestMapping(value = "/tags/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Tag : {}", id);
        tagRepository.delete(id);
    }
}
