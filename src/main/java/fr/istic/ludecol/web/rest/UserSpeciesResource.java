package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.UserSpecies;
import fr.istic.ludecol.repository.UserSpeciesRepository;
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
 * REST controller for managing UserSpecies.
 */
@RestController
@RequestMapping("/api")
public class UserSpeciesResource {

    private final Logger log = LoggerFactory.getLogger(UserSpeciesResource.class);

    @Inject
    private UserSpeciesRepository userSpeciesRepository;

    /**
     * POST  /userSpeciess -> Create a new userSpecies.
     */
    @RequestMapping(value = "/userSpeciess",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody UserSpecies userSpecies) {
        log.debug("REST request to save UserSpecies : {}", userSpecies);
        userSpeciesRepository.save(userSpecies);
    }

    /**
     * GET  /userSpeciess -> get all the userSpeciess.
     */
    @RequestMapping(value = "/userSpeciess",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserSpecies> getAll() {
        log.debug("REST request to get all UserSpeciess");
        return userSpeciesRepository.findAll();
    }

    /**
     * GET  /userSpeciess/:id -> get the "id" userSpecies.
     */
    @RequestMapping(value = "/userSpeciess/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserSpecies> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get UserSpecies : {}", id);
        UserSpecies userSpecies = userSpeciesRepository.findOne(id);
        if (userSpecies == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userSpecies, HttpStatus.OK);
    }

    /**
     * DELETE  /userSpeciess/:id -> delete the "id" userSpecies.
     */
    @RequestMapping(value = "/userSpeciess/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserSpecies : {}", id);
        userSpeciesRepository.delete(id);
    }
}
