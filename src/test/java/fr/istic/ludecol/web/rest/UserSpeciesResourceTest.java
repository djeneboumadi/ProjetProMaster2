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
import fr.istic.ludecol.domain.UserSpecies;
import fr.istic.ludecol.repository.UserSpeciesRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserSpeciesResource REST controller.
 *
 * @see UserSpeciesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserSpeciesResourceTest {


    @Inject
    private UserSpeciesRepository userSpeciesRepository;

    private MockMvc restUserSpeciesMockMvc;

    private UserSpecies userSpecies;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserSpeciesResource userSpeciesResource = new UserSpeciesResource();
        ReflectionTestUtils.setField(userSpeciesResource, "userSpeciesRepository", userSpeciesRepository);
        this.restUserSpeciesMockMvc = MockMvcBuilders.standaloneSetup(userSpeciesResource).build();
    }

    @Before
    public void initTest() {
        userSpecies = new UserSpecies();
    }

    @Test
    @Transactional
    public void createUserSpecies() throws Exception {
        // Validate the database is empty
        assertThat(userSpeciesRepository.findAll()).hasSize(0);

        // Create the UserSpecies
        restUserSpeciesMockMvc.perform(post("/api/userSpeciess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userSpecies)))
                .andExpect(status().isOk());

        // Validate the UserSpecies in the database
        List<UserSpecies> userSpeciess = userSpeciesRepository.findAll();
        assertThat(userSpeciess).hasSize(1);
        UserSpecies testUserSpecies = userSpeciess.iterator().next();
    }

    @Test
    @Transactional
    public void getAllUserSpeciess() throws Exception {
        // Initialize the database
        userSpeciesRepository.saveAndFlush(userSpecies);

        // Get all the userSpeciess
        restUserSpeciesMockMvc.perform(get("/api/userSpeciess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(userSpecies.getId().intValue()));
    }

    @Test
    @Transactional
    public void getUserSpecies() throws Exception {
        // Initialize the database
        userSpeciesRepository.saveAndFlush(userSpecies);

        // Get the userSpecies
        restUserSpeciesMockMvc.perform(get("/api/userSpeciess/{id}", userSpecies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userSpecies.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserSpecies() throws Exception {
        // Get the userSpecies
        restUserSpeciesMockMvc.perform(get("/api/userSpeciess/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSpecies() throws Exception {
        // Initialize the database
        userSpeciesRepository.saveAndFlush(userSpecies);

        // Update the userSpecies
        restUserSpeciesMockMvc.perform(post("/api/userSpeciess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userSpecies)))
                .andExpect(status().isOk());

        // Validate the UserSpecies in the database
        List<UserSpecies> userSpeciess = userSpeciesRepository.findAll();
        assertThat(userSpeciess).hasSize(1);
        UserSpecies testUserSpecies = userSpeciess.iterator().next();
    }

    @Test
    @Transactional
    public void deleteUserSpecies() throws Exception {
        // Initialize the database
        userSpeciesRepository.saveAndFlush(userSpecies);

        // Get the userSpecies
        restUserSpeciesMockMvc.perform(delete("/api/userSpeciess/{id}", userSpecies.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserSpecies> userSpeciess = userSpeciesRepository.findAll();
        assertThat(userSpeciess).hasSize(0);
    }
}
