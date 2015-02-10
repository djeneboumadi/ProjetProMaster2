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
import fr.istic.ludecol.domain.Species;
import fr.istic.ludecol.repository.SpeciesRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SpeciesResource REST controller.
 *
 * @see SpeciesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SpeciesResourceTest {


    private static final Integer DEFAULT_ID_SPECIES = 0;
    private static final Integer UPDATED_ID_SPECIES = 1;
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CATEGORY = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORY = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_URL_PICTURE_SPECIES = "SAMPLE_TEXT";
    private static final String UPDATED_URL_PICTURE_SPECIES = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_IN_ENCYCLO = false;
    private static final Boolean UPDATED_IS_IN_ENCYCLO = true;

    @Inject
    private SpeciesRepository speciesRepository;

    private MockMvc restSpeciesMockMvc;

    private Species species;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpeciesResource speciesResource = new SpeciesResource();
        ReflectionTestUtils.setField(speciesResource, "speciesRepository", speciesRepository);
        this.restSpeciesMockMvc = MockMvcBuilders.standaloneSetup(speciesResource).build();
    }

    @Before
    public void initTest() {
        species = new Species();
        species.setId_species(DEFAULT_ID_SPECIES);
        species.setName(DEFAULT_NAME);
        species.setCategory(DEFAULT_CATEGORY);
        species.setDescription(DEFAULT_DESCRIPTION);
        species.setUrl_picture_species(DEFAULT_URL_PICTURE_SPECIES);
        species.setIs_in_encyclo(DEFAULT_IS_IN_ENCYCLO);
    }

    @Test
    @Transactional
    public void createSpecies() throws Exception {
        // Validate the database is empty
        assertThat(speciesRepository.findAll()).hasSize(0);

        // Create the Species
        restSpeciesMockMvc.perform(post("/api/speciess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(species)))
                .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciess = speciesRepository.findAll();
        assertThat(speciess).hasSize(1);
        Species testSpecies = speciess.iterator().next();
        assertThat(testSpecies.getId_species()).isEqualTo(DEFAULT_ID_SPECIES);
        assertThat(testSpecies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpecies.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSpecies.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSpecies.getUrl_picture_species()).isEqualTo(DEFAULT_URL_PICTURE_SPECIES);
        assertThat(testSpecies.getIs_in_encyclo()).isEqualTo(DEFAULT_IS_IN_ENCYCLO);
    }

    @Test
    @Transactional
    public void getAllSpeciess() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get all the speciess
        restSpeciesMockMvc.perform(get("/api/speciess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(species.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_species").value(DEFAULT_ID_SPECIES))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].category").value(DEFAULT_CATEGORY.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].url_picture_species").value(DEFAULT_URL_PICTURE_SPECIES.toString()))
                .andExpect(jsonPath("$.[0].is_in_encyclo").value(DEFAULT_IS_IN_ENCYCLO.booleanValue()));
    }

    @Test
    @Transactional
    public void getSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get the species
        restSpeciesMockMvc.perform(get("/api/speciess/{id}", species.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(species.getId().intValue()))
            .andExpect(jsonPath("$.id_species").value(DEFAULT_ID_SPECIES))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.url_picture_species").value(DEFAULT_URL_PICTURE_SPECIES.toString()))
            .andExpect(jsonPath("$.is_in_encyclo").value(DEFAULT_IS_IN_ENCYCLO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecies() throws Exception {
        // Get the species
        restSpeciesMockMvc.perform(get("/api/speciess/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Update the species
        species.setId_species(UPDATED_ID_SPECIES);
        species.setName(UPDATED_NAME);
        species.setCategory(UPDATED_CATEGORY);
        species.setDescription(UPDATED_DESCRIPTION);
        species.setUrl_picture_species(UPDATED_URL_PICTURE_SPECIES);
        species.setIs_in_encyclo(UPDATED_IS_IN_ENCYCLO);
        restSpeciesMockMvc.perform(post("/api/speciess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(species)))
                .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciess = speciesRepository.findAll();
        assertThat(speciess).hasSize(1);
        Species testSpecies = speciess.iterator().next();
        assertThat(testSpecies.getId_species()).isEqualTo(UPDATED_ID_SPECIES);
        assertThat(testSpecies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpecies.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSpecies.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpecies.getUrl_picture_species()).isEqualTo(UPDATED_URL_PICTURE_SPECIES);
        assertThat(testSpecies.getIs_in_encyclo()).isEqualTo(UPDATED_IS_IN_ENCYCLO);
    }

    @Test
    @Transactional
    public void deleteSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get the species
        restSpeciesMockMvc.perform(delete("/api/speciess/{id}", species.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Species> speciess = speciesRepository.findAll();
        assertThat(speciess).hasSize(0);
    }
}
