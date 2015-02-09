package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.Level;
import fr.istic.ludecol.repository.LevelRepository;
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
 * REST controller for managing Level.
 */
@RestController
@RequestMapping("/api")
public class LevelResource {

    private final Logger log = LoggerFactory.getLogger(LevelResource.class);

    @Inject
    private LevelRepository levelRepository;

    /**
     * POST  /levels -> Create a new level.
     */
    @RequestMapping(value = "/levels",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Level level) {
        log.debug("REST request to save Level : {}", level);
        levelRepository.save(level);
    }

    /**
     * GET  /levels -> get all the levels.
     */
    @RequestMapping(value = "/levels",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Level> getAll() {
        log.debug("REST request to get all Levels");
        return levelRepository.findAll();
    }

    /**
     * GET  /levels/:id -> get the "id" level.
     */
    @RequestMapping(value = "/levels/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Level> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Level : {}", id);
        Level level = levelRepository.findOne(id);
        if (level == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(level, HttpStatus.OK);
    }

    /**
     * DELETE  /levels/:id -> delete the "id" level.
     */
    @RequestMapping(value = "/levels/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Level : {}", id);
        levelRepository.delete(id);
    }
}
