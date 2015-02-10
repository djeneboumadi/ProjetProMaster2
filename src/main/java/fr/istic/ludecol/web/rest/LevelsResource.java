package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.Levels;
import fr.istic.ludecol.repository.LevelsRepository;
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
 * REST controller for managing Levels.
 */
@RestController
@RequestMapping("/api")
public class LevelsResource {

    private final Logger log = LoggerFactory.getLogger(LevelsResource.class);

    @Inject
    private LevelsRepository levelsRepository;

    /**
     * POST  /levelss -> Create a new levels.
     */
    @RequestMapping(value = "/levelss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Levels levels) {
        log.debug("REST request to save Levels : {}", levels);
        levelsRepository.save(levels);
    }

    /**
     * GET  /levelss -> get all the levelss.
     */
    @RequestMapping(value = "/levelss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Levels> getAll() {
        log.debug("REST request to get all Levelss");
        return levelsRepository.findAll();
    }

    /**
     * GET  /levelss/:id -> get the "id" levels.
     */
    @RequestMapping(value = "/levelss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Levels> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Levels : {}", id);
        Levels levels = levelsRepository.findOne(id);
        if (levels == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    /**
     * DELETE  /levelss/:id -> delete the "id" levels.
     */
    @RequestMapping(value = "/levelss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Levels : {}", id);
        levelsRepository.delete(id);
    }
}
