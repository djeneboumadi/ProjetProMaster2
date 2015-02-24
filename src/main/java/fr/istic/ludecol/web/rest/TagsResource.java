package fr.istic.ludecol.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import fr.istic.ludecol.domain.Tags;
import fr.istic.ludecol.repository.TagsRepository;

/**
 * REST controller for managing Tags.
 */
@RestController
@RequestMapping("/api")
public class TagsResource {

    private final Logger log = LoggerFactory.getLogger(TagsResource.class);

    @Inject
    private TagsRepository tagsRepository;

    /**
     * POST  /tagss -> Create a new tags.
     */
    @RequestMapping(value = "/tagss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Tags tags) {
        log.debug("REST request to save Tags : {}", tags);
        tagsRepository.save(tags);
    }

    /**
     * GET  /tagss -> get all the tagss.
     */
    @RequestMapping(value = "/tagss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Tags> getAll() {
        log.debug("REST request to get all Tagss");
        return tagsRepository.findAll();
    }

    /**
     * GET  /tagss/:id -> get the "id" tags.
     */
    @RequestMapping(value = "/tagss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tags> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Tags : {}", id);
        Tags tags = tagsRepository.findOne(id);
        if (tags == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * DELETE  /tagss/:id -> delete the "id" tags.
     */
    @RequestMapping(value = "/tagss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Tags : {}", id);
        tagsRepository.delete(id);
    }
    
    @RequestMapping(value = "/tags/push",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void get(@RequestBody List<fr.istic.ludecol.service.util.Annotation> annots) {
    	System.err.println("get annotations");
    	for (fr.istic.ludecol.service.util.Annotation a : annots){
    		System.err.println(a.getText() + " : " +a.getX() + " , " + a.getY()  + " , " + a.getSpecies().getName() );
    		
    		Tags t = new Tags();
    		t.setSpecies(a.getSpecies());
    		t.setPos_x(a.getX());
    		t.setPos_y(a.getY());
    		this.create(t);
    	} 	
    }
}
