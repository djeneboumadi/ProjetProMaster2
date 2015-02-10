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
import fr.istic.ludecol.domain.Pictures;
import fr.istic.ludecol.repository.PicturesRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PicturesResource REST controller.
 *
 * @see PicturesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PicturesResourceTest {


    private static final Integer DEFAULT_ID_PICTURE = 0;
    private static final Integer UPDATED_ID_PICTURE = 1;
    private static final String DEFAULT_URL_PICTURE = "SAMPLE_TEXT";
    private static final String UPDATED_URL_PICTURE = "UPDATED_TEXT";

    private static final Long DEFAULT_HEIGHT = 0L;
    private static final Long UPDATED_HEIGHT = 1L;

    private static final Long DEFAULT_WIDTH = 0L;
    private static final Long UPDATED_WIDTH = 1L;
    private static final String DEFAULT_MATRIX_POSITION = "SAMPLE_TEXT";
    private static final String UPDATED_MATRIX_POSITION = "UPDATED_TEXT";

    @Inject
    private PicturesRepository picturesRepository;

    private MockMvc restPicturesMockMvc;

    private Pictures pictures;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PicturesResource picturesResource = new PicturesResource();
        ReflectionTestUtils.setField(picturesResource, "picturesRepository", picturesRepository);
        this.restPicturesMockMvc = MockMvcBuilders.standaloneSetup(picturesResource).build();
    }

    @Before
    public void initTest() {
        pictures = new Pictures();
        pictures.setId_picture(DEFAULT_ID_PICTURE);
        pictures.setUrl_picture(DEFAULT_URL_PICTURE);
        pictures.setHeight(DEFAULT_HEIGHT);
        pictures.setWidth(DEFAULT_WIDTH);
        pictures.setMatrix_position(DEFAULT_MATRIX_POSITION);
    }

    @Test
    @Transactional
    public void createPictures() throws Exception {
        // Validate the database is empty
        assertThat(picturesRepository.findAll()).hasSize(0);

        // Create the Pictures
        restPicturesMockMvc.perform(post("/api/picturess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pictures)))
                .andExpect(status().isOk());

        // Validate the Pictures in the database
        List<Pictures> picturess = picturesRepository.findAll();
        assertThat(picturess).hasSize(1);
        Pictures testPictures = picturess.iterator().next();
        assertThat(testPictures.getId_picture()).isEqualTo(DEFAULT_ID_PICTURE);
        assertThat(testPictures.getUrl_picture()).isEqualTo(DEFAULT_URL_PICTURE);
        assertThat(testPictures.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPictures.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testPictures.getMatrix_position()).isEqualTo(DEFAULT_MATRIX_POSITION);
    }

    @Test
    @Transactional
    public void getAllPicturess() throws Exception {
        // Initialize the database
        picturesRepository.saveAndFlush(pictures);

        // Get all the picturess
        restPicturesMockMvc.perform(get("/api/picturess"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(pictures.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_picture").value(DEFAULT_ID_PICTURE))
                .andExpect(jsonPath("$.[0].url_picture").value(DEFAULT_URL_PICTURE.toString()))
                .andExpect(jsonPath("$.[0].height").value(DEFAULT_HEIGHT.intValue()))
                .andExpect(jsonPath("$.[0].width").value(DEFAULT_WIDTH.intValue()))
                .andExpect(jsonPath("$.[0].matrix_position").value(DEFAULT_MATRIX_POSITION.toString()));
    }

    @Test
    @Transactional
    public void getPictures() throws Exception {
        // Initialize the database
        picturesRepository.saveAndFlush(pictures);

        // Get the pictures
        restPicturesMockMvc.perform(get("/api/picturess/{id}", pictures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pictures.getId().intValue()))
            .andExpect(jsonPath("$.id_picture").value(DEFAULT_ID_PICTURE))
            .andExpect(jsonPath("$.url_picture").value(DEFAULT_URL_PICTURE.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.intValue()))
            .andExpect(jsonPath("$.matrix_position").value(DEFAULT_MATRIX_POSITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPictures() throws Exception {
        // Get the pictures
        restPicturesMockMvc.perform(get("/api/picturess/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePictures() throws Exception {
        // Initialize the database
        picturesRepository.saveAndFlush(pictures);

        // Update the pictures
        pictures.setId_picture(UPDATED_ID_PICTURE);
        pictures.setUrl_picture(UPDATED_URL_PICTURE);
        pictures.setHeight(UPDATED_HEIGHT);
        pictures.setWidth(UPDATED_WIDTH);
        pictures.setMatrix_position(UPDATED_MATRIX_POSITION);
        restPicturesMockMvc.perform(post("/api/picturess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pictures)))
                .andExpect(status().isOk());

        // Validate the Pictures in the database
        List<Pictures> picturess = picturesRepository.findAll();
        assertThat(picturess).hasSize(1);
        Pictures testPictures = picturess.iterator().next();
        assertThat(testPictures.getId_picture()).isEqualTo(UPDATED_ID_PICTURE);
        assertThat(testPictures.getUrl_picture()).isEqualTo(UPDATED_URL_PICTURE);
        assertThat(testPictures.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPictures.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPictures.getMatrix_position()).isEqualTo(UPDATED_MATRIX_POSITION);
    }

    @Test
    @Transactional
    public void deletePictures() throws Exception {
        // Initialize the database
        picturesRepository.saveAndFlush(pictures);

        // Get the pictures
        restPicturesMockMvc.perform(delete("/api/picturess/{id}", pictures.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pictures> picturess = picturesRepository.findAll();
        assertThat(picturess).hasSize(0);
    }
}
