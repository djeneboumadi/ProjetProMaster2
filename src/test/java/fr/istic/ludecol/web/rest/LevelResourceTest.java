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
import fr.istic.ludecol.domain.Level;
import fr.istic.ludecol.repository.LevelRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LevelResource REST controller.
 *
 * @see LevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LevelResourceTest {


    private static final Integer DEFAULT_ID_LEVEL = 0;
    private static final Integer UPDATED_ID_LEVEL = 1;
    private static final String DEFAULT_QUESTION = "SAMPLE_TEXT";
    private static final String UPDATED_QUESTION = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIES_LIST = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIES_LIST = "UPDATED_TEXT";

    @Inject
    private LevelRepository levelRepository;

    private MockMvc restLevelMockMvc;

    private Level level;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LevelResource levelResource = new LevelResource();
        ReflectionTestUtils.setField(levelResource, "levelRepository", levelRepository);
        this.restLevelMockMvc = MockMvcBuilders.standaloneSetup(levelResource).build();
    }

    @Before
    public void initTest() {
        level = new Level();
        level.setId_level(DEFAULT_ID_LEVEL);
        level.setQuestion(DEFAULT_QUESTION);
        level.setSpecies_list(DEFAULT_SPECIES_LIST);
    }

    @Test
    @Transactional
    public void createLevel() throws Exception {
        // Validate the database is empty
        assertThat(levelRepository.findAll()).hasSize(0);

        // Create the Level
        restLevelMockMvc.perform(post("/api/levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(level)))
                .andExpect(status().isOk());

        // Validate the Level in the database
        List<Level> levels = levelRepository.findAll();
        assertThat(levels).hasSize(1);
        Level testLevel = levels.iterator().next();
        assertThat(testLevel.getId_level()).isEqualTo(DEFAULT_ID_LEVEL);
        assertThat(testLevel.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testLevel.getSpecies_list()).isEqualTo(DEFAULT_SPECIES_LIST);
    }

    @Test
    @Transactional
    public void getAllLevels() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levels
        restLevelMockMvc.perform(get("/api/levels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(level.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_level").value(DEFAULT_ID_LEVEL))
                .andExpect(jsonPath("$.[0].question").value(DEFAULT_QUESTION.toString()))
                .andExpect(jsonPath("$.[0].species_list").value(DEFAULT_SPECIES_LIST.toString()));
    }

    @Test
    @Transactional
    public void getLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get the level
        restLevelMockMvc.perform(get("/api/levels/{id}", level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(level.getId().intValue()))
            .andExpect(jsonPath("$.id_level").value(DEFAULT_ID_LEVEL))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.species_list").value(DEFAULT_SPECIES_LIST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLevel() throws Exception {
        // Get the level
        restLevelMockMvc.perform(get("/api/levels/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Update the level
        level.setId_level(UPDATED_ID_LEVEL);
        level.setQuestion(UPDATED_QUESTION);
        level.setSpecies_list(UPDATED_SPECIES_LIST);
        restLevelMockMvc.perform(post("/api/levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(level)))
                .andExpect(status().isOk());

        // Validate the Level in the database
        List<Level> levels = levelRepository.findAll();
        assertThat(levels).hasSize(1);
        Level testLevel = levels.iterator().next();
        assertThat(testLevel.getId_level()).isEqualTo(UPDATED_ID_LEVEL);
        assertThat(testLevel.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testLevel.getSpecies_list()).isEqualTo(UPDATED_SPECIES_LIST);
    }

    @Test
    @Transactional
    public void deleteLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get the level
        restLevelMockMvc.perform(delete("/api/levels/{id}", level.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Level> levels = levelRepository.findAll();
        assertThat(levels).hasSize(0);
    }
}
