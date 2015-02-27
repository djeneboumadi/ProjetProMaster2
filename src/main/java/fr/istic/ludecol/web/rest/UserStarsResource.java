package fr.istic.ludecol.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.istic.ludecol.domain.Tags;
import fr.istic.ludecol.domain.User;
import fr.istic.ludecol.domain.UserStars;
import fr.istic.ludecol.repository.UserRepository;
import fr.istic.ludecol.repository.UserStarsRepository;

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
 * REST controller for managing UserStars.
 */
@RestController
@RequestMapping("/api")
public class UserStarsResource {

	@Inject
	private UserRepository userRepository;
	

	private final Logger log = LoggerFactory.getLogger(UserStarsResource.class);

	@Inject
	private UserStarsRepository userStarsRepository;

	/**
	 * POST  /userStarss -> Create a new userStars.
	 */
	@RequestMapping(value = "/userStarss",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void create(@RequestBody UserStars userStars) {
		log.debug("REST request to save UserStars : {}", userStars);
		userStarsRepository.save(userStars);
	}

	/**
	 * GET  /userStarss -> get all the userStarss.
	 */
	@RequestMapping(value = "/userStarss",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<UserStars> getAll() {
		log.debug("REST request to get all UserStarss");
		return userStarsRepository.findAll();
	}

	/**
	 * GET  /userStarss/:id -> get the "id" userStars.
	 */
	@RequestMapping(value = "/userStarss/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<UserStars> get(@PathVariable Long id, HttpServletResponse response) {
		log.debug("REST request to get UserStars : {}", id);
		UserStars userStars = userStarsRepository.findOne(id);
		if (userStars == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userStars, HttpStatus.OK);
	}

	/**
	 * DELETE  /userStarss/:id -> delete the "id" userStars.
	 */
	@RequestMapping(value = "/userStarss/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void delete(@PathVariable Long id) {
		log.debug("REST request to delete UserStars : {}", id);
		userStarsRepository.delete(id);
	}

	@RequestMapping(value = "/userStarss/push",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void get(@RequestBody fr.istic.ludecol.service.util.Star userstars) {
		User user = userRepository.findOneByLogin(userstars.getUser());
		UserStars us = new UserStars();
		
		userStarsRepository.findAll();
		/*if(userStarsRepository.findOneByLevel_idUser_id(userstars.getLevel().getId(), user.getId())==null){
			System.err.println("zzzzzzzzzzzzzzzzzz");
			
		}else{*/
			System.err.println(userstars.getLevel() + " : " +userstars.getNbStar()+ " , " +userstars.getUser() );
			us.setLevel(userstars.getLevel());
			us.setNb_stars(userstars.getNbStar());
			us.setUser(user);
			this.create(us);
		/*}*/

	}
}
