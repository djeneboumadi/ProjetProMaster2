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
import fr.istic.ludecol.domain.Levels;
import fr.istic.ludecol.repository.LevelsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LevelsResource REST controller.
 *
 * @see LevelsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LevelsResourceTest {


    private static final Integer DEFAULT_ID_LEVEL = 0;
    private static final Integer UPDATED_ID_LEVEL = 1;
    private static final String DEFAULT_QUESTION = "SAMPLE_TEXT";
    private static final String UPDATED_QUESTION = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIES_LIST = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIES_LIST = "UPDATED_TEXT";

    @Inject
    private LevelsRepository levelsRepository;

    private MockMvc restLevelsMockMvc;

    private Levels levels;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LevelsResource levelsResource = new LevelsResource();
        ReflectionTestUtils.setField(levelsResource, "levelsRepository", levelsRepository);
        this.restLevelsMockMvc = MockMvcBuilders.standaloneSetup(levelsResource).build();
    }

    @Before
    public void initTest() {
        levels = new Levels();
        levels.setId_level(DEFAULT_ID_LEVEL);
        levels.setQuestion(DEFAULT_QUESTION);
        levels.setSpecies_list(DEFAULT_SPECIES_LIST);
    }

    @Test
    @Transactional
    public void createLevels() throws Exception {
        // Validate the database is empty
        assertThat(levelsRepository.findAll()).hasSize(0);

        // Create the Levels
        restLevelsMockMvc.perform(post("/api/levelss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(levels)))
                .andExpect(status().isOk());

        // Validate the Levels in the database
        List<Levels> levelss = levelsRepository.findAll();
        assertThat(levelss).hasSize(1);
        Levels testLevels = levelss.iterator().next();
        assertThat(testLevels.getId_level()).isEqualTo(DEFAULT_ID_LEVEL);
        assertThat(testLevels.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testLevels.getSpecies_list()).isEqualTo(DEFAULT_SPECIES_LIST);
    }

    @Test
    @Transactional
    public void getAllLevelss() throws Exception {
        // Initialize the database
        levelsRepository.saveAndFlush(levels);

        // Get all the levelss
        restLevelsMockMvc.perform(get("/api/levelss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(levels.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_level").value(DEFAULT_ID_LEVEL))
                .andExpect(jsonPath("$.[0].question").value(DEFAULT_QUESTION.toString()))
                .andExpect(jsonPath("$.[0].species_list").value(DEFAULT_SPECIES_LIST.toString()));
    }

    @Test
    @Transactional
    public void getLevels() throws Exception {
        // Initialize the database
        levelsRepository.saveAndFlush(levels);

        // Get the levels
        restLevelsMockMvc.perform(get("/api/levelss/{id}", levels.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(levels.getId().intValue()))
            .andExpect(jsonPath("$.id_level").value(DEFAULT_ID_LEVEL))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.species_list").value(DEFAULT_SPECIES_LIST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLevels() throws Exception {
        // Get the levels
        restLevelsMockMvc.perform(get("/api/levelss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevels() throws Exception {
        // Initialize the database
        levelsRepository.saveAndFlush(levels);

        // Update the levels
        levels.setId_level(UPDATED_ID_LEVEL);
        levels.setQuestion(UPDATED_QUESTION);
        levels.setSpecies_list(UPDATED_SPECIES_LIST);
        restLevelsMockMvc.perform(post("/api/levelss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(levels)))
                .andExpect(status().isOk());

        // Validate the Levels in the database
        List<Levels> levelss = levelsRepository.findAll();
        assertThat(levelss).hasSize(1);
        Levels testLevels = levelss.iterator().next();
        assertThat(testLevels.getId_level()).isEqualTo(UPDATED_ID_LEVEL);
        assertThat(testLevels.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testLevels.getSpecies_list()).isEqualTo(UPDATED_SPECIES_LIST);
    }

    @Test
    @Transactional
    public void deleteLevels() throws Exception {
        // Initialize the database
        levelsRepository.saveAndFlush(levels);

        // Get the levels
        restLevelsMockMvc.perform(delete("/api/levelss/{id}", levels.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Levels> levelss = levelsRepository.findAll();
        assertThat(levelss).hasSize(0);
    }
}
