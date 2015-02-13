package fr.istic.ludecol.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import fr.istic.ludecol.Application;
import fr.istic.ludecol.domain.UserStars;
import fr.istic.ludecol.repository.UserStarsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserStarsResource REST controller.
 *
 * @see UserStarsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserStarsResourceTest {


    private static final Boolean DEFAULT_LEVEL_PLAYED = false;
    private static final Boolean UPDATED_LEVEL_PLAYED = true;

    private static final Integer DEFAULT_NB_STARS = 0;
    private static final Integer UPDATED_NB_STARS = 1;

    @Inject
    private UserStarsRepository userStarsRepository;

    private MockMvc restUserStarsMockMvc;

    private UserStars userStars;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserStarsResource userStarsResource = new UserStarsResource();
        ReflectionTestUtils.setField(userStarsResource, "userStarsRepository", userStarsRepository);
        this.restUserStarsMockMvc = MockMvcBuilders.standaloneSetup(userStarsResource).build();
    }

    @Before
    public void initTest() {
        userStars = new UserStars();
        userStars.setNb_stars(DEFAULT_NB_STARS);
    }

    @Test
    @Transactional
    public void createUserStars() throws Exception {
        // Validate the database is empty
        assertThat(userStarsRepository.findAll()).hasSize(0);

        // Create the UserStars
        restUserStarsMockMvc.perform(post("/api/userStarss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userStars)))
                .andExpect(status().isOk());

        // Validate the UserStars in the database
        List<UserStars> userStarss = userStarsRepository.findAll();
        assertThat(userStarss).hasSize(1);
        UserStars testUserStars = userStarss.iterator().next();
        assertThat(testUserStars.getNb_stars()).isEqualTo(DEFAULT_NB_STARS);
    }

    @Test
    @Transactional
    public void getAllUserStarss() throws Exception {
        // Initialize the database
        userStarsRepository.saveAndFlush(userStars);

        // Get all the userStarss
        restUserStarsMockMvc.perform(get("/api/userStarss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userStars.getId().intValue()))
                .andExpect(jsonPath("$.[0].level_played").value(DEFAULT_LEVEL_PLAYED.booleanValue()))
                .andExpect(jsonPath("$.[0].nb_stars").value(DEFAULT_NB_STARS));
    }

    @Test
    @Transactional
    public void getUserStars() throws Exception {
        // Initialize the database
        userStarsRepository.saveAndFlush(userStars);

        // Get the userStars
        restUserStarsMockMvc.perform(get("/api/userStarss/{id}", userStars.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userStars.getId().intValue()))
            .andExpect(jsonPath("$.level_played").value(DEFAULT_LEVEL_PLAYED.booleanValue()))
            .andExpect(jsonPath("$.nb_stars").value(DEFAULT_NB_STARS));
    }

    @Test
    @Transactional
    public void getNonExistingUserStars() throws Exception {
        // Get the userStars
        restUserStarsMockMvc.perform(get("/api/userStarss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserStars() throws Exception {
        // Initialize the database
        userStarsRepository.saveAndFlush(userStars);

        // Update the userStars
        userStars.setNb_stars(UPDATED_NB_STARS);
        restUserStarsMockMvc.perform(post("/api/userStarss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userStars)))
                .andExpect(status().isOk());

        // Validate the UserStars in the database
        List<UserStars> userStarss = userStarsRepository.findAll();
        assertThat(userStarss).hasSize(1);
        UserStars testUserStars = userStarss.iterator().next();
        assertThat(testUserStars.getNb_stars()).isEqualTo(UPDATED_NB_STARS);
    }

    @Test
    @Transactional
    public void deleteUserStars() throws Exception {
        // Initialize the database
        userStarsRepository.saveAndFlush(userStars);

        // Get the userStars
        restUserStarsMockMvc.perform(delete("/api/userStarss/{id}", userStars.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserStars> userStarss = userStarsRepository.findAll();
        assertThat(userStarss).hasSize(0);
    }
}
