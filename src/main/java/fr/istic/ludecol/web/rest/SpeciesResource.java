package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.Species;
import fr.istic.ludecol.repository.SpeciesRepository;
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
 * REST controller for managing Species.
 */
@RestController
@RequestMapping("/api")
public class SpeciesResource {

    private final Logger log = LoggerFactory.getLogger(SpeciesResource.class);

    @Inject
    private SpeciesRepository speciesRepository;

    /**
     * POST  /speciess -> Create a new species.
     */
    @RequestMapping(value = "/speciess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Species species) {
        log.debug("REST request to save Species : {}", species);
        speciesRepository.save(species);
    }

    /**
     * GET  /speciess -> get all the speciess.
     */
    @RequestMapping(value = "/speciess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Species> getAll() {
        log.debug("REST request to get all Speciess");
        return speciesRepository.findAll();
    }

    /**
     * GET  /speciess/:id -> get the "id" species.
     */
    @RequestMapping(value = "/speciess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Species> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Species : {}", id);
        Species species = speciesRepository.findOne(id);
        if (species == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(species, HttpStatus.OK);
    }

    /**
     * DELETE  /speciess/:id -> delete the "id" species.
     */
    @RequestMapping(value = "/speciess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Species : {}", id);
        speciesRepository.delete(id);
    }
}
