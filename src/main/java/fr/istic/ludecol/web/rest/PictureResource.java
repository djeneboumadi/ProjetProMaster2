package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.ludecol.domain.Picture;
import fr.istic.ludecol.repository.PictureRepository;
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
 * REST controller for managing Picture.
 */
@RestController
@RequestMapping("/api")
public class PictureResource {

    private final Logger log = LoggerFactory.getLogger(PictureResource.class);

    @Inject
    private PictureRepository pictureRepository;

    /**
     * POST  /pictures -> Create a new picture.
     */
    @RequestMapping(value = "/pictures",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Picture picture) {
        log.debug("REST request to save Picture : {}", picture);
        pictureRepository.save(picture);
    }

    /**
     * GET  /pictures -> get all the pictures.
     */
    @RequestMapping(value = "/pictures",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Picture> getAll() {
        log.debug("REST request to get all Pictures");
        return pictureRepository.findAll();
    }

    /**
     * GET  /pictures/:id -> get the "id" picture.
     */
    @RequestMapping(value = "/pictures/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Picture> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Picture : {}", id);
        Picture picture = pictureRepository.findOne(id);
        if (picture == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(picture, HttpStatus.OK);
    }

    /**
     * DELETE  /pictures/:id -> delete the "id" picture.
     */
    @RequestMapping(value = "/pictures/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Picture : {}", id);
        pictureRepository.delete(id);
    }
}
