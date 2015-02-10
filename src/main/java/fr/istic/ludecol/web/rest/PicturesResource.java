package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.Pictures;
import fr.istic.ludecol.repository.PicturesRepository;
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
 * REST controller for managing Pictures.
 */
@RestController
@RequestMapping("/api")
public class PicturesResource {

    private final Logger log = LoggerFactory.getLogger(PicturesResource.class);

    @Inject
    private PicturesRepository picturesRepository;

    /**
     * POST  /picturess -> Create a new pictures.
     */
    @RequestMapping(value = "/picturess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Pictures pictures) {
        log.debug("REST request to save Pictures : {}", pictures);
        picturesRepository.save(pictures);
    }

    /**
     * GET  /picturess -> get all the picturess.
     */
    @RequestMapping(value = "/picturess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pictures> getAll() {
        log.debug("REST request to get all Picturess");
        return picturesRepository.findAll();
    }

    /**
     * GET  /picturess/:id -> get the "id" pictures.
     */
    @RequestMapping(value = "/picturess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pictures> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Pictures : {}", id);
        Pictures pictures = picturesRepository.findOne(id);
        if (pictures == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    /**
     * DELETE  /picturess/:id -> delete the "id" pictures.
     */
    @RequestMapping(value = "/picturess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Pictures : {}", id);
        picturesRepository.delete(id);
    }
}
