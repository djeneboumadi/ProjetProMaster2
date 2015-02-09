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
import fr.istic.ludecol.domain.Picture;
import fr.istic.ludecol.repository.PictureRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PictureResource REST controller.
 *
 * @see PictureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PictureResourceTest {


    private static final Integer DEFAULT_ID_PICTURE = 0;
    private static final Integer UPDATED_ID_PICTURE = 1;
    private static final String DEFAULT_URL_PICTURE = "SAMPLE_TEXT";
    private static final String UPDATED_URL_PICTURE = "UPDATED_TEXT";

    private static final Long DEFAULT_HEIGHT = 0L;
    private static final Long UPDATED_HEIGHT = 1L;

    private static final Long DEFAULT_WIDTH = 0L;
    private static final Long UPDATED_WIDTH = 1L;
    private static final String DEFAULT_POS_MATRIX = "SAMPLE_TEXT";
    private static final String UPDATED_POS_MATRIX = "UPDATED_TEXT";

    @Inject
    private PictureRepository pictureRepository;

    private MockMvc restPictureMockMvc;

    private Picture picture;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PictureResource pictureResource = new PictureResource();
        ReflectionTestUtils.setField(pictureResource, "pictureRepository", pictureRepository);
        this.restPictureMockMvc = MockMvcBuilders.standaloneSetup(pictureResource).build();
    }

    @Before
    public void initTest() {
        picture = new Picture();
        picture.setId_picture(DEFAULT_ID_PICTURE);
        picture.setUrl_picture(DEFAULT_URL_PICTURE);
        picture.setHeight(DEFAULT_HEIGHT);
        picture.setWidth(DEFAULT_WIDTH);
        picture.setPos_matrix(DEFAULT_POS_MATRIX);
    }

    @Test
    @Transactional
    public void createPicture() throws Exception {
        // Validate the database is empty
        assertThat(pictureRepository.findAll()).hasSize(0);

        // Create the Picture
        restPictureMockMvc.perform(post("/api/pictures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(picture)))
                .andExpect(status().isOk());

        // Validate the Picture in the database
        List<Picture> pictures = pictureRepository.findAll();
        assertThat(pictures).hasSize(1);
        Picture testPicture = pictures.iterator().next();
        assertThat(testPicture.getId_picture()).isEqualTo(DEFAULT_ID_PICTURE);
        assertThat(testPicture.getUrl_picture()).isEqualTo(DEFAULT_URL_PICTURE);
        assertThat(testPicture.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPicture.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testPicture.getPos_matrix()).isEqualTo(DEFAULT_POS_MATRIX);
    }

    @Test
    @Transactional
    public void getAllPictures() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        // Get all the pictures
        restPictureMockMvc.perform(get("/api/pictures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(picture.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_picture").value(DEFAULT_ID_PICTURE))
                .andExpect(jsonPath("$.[0].url_picture").value(DEFAULT_URL_PICTURE.toString()))
                .andExpect(jsonPath("$.[0].height").value(DEFAULT_HEIGHT.intValue()))
                .andExpect(jsonPath("$.[0].width").value(DEFAULT_WIDTH.intValue()))
                .andExpect(jsonPath("$.[0].pos_matrix").value(DEFAULT_POS_MATRIX.toString()));
    }

    @Test
    @Transactional
    public void getPicture() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        // Get the picture
        restPictureMockMvc.perform(get("/api/pictures/{id}", picture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(picture.getId().intValue()))
            .andExpect(jsonPath("$.id_picture").value(DEFAULT_ID_PICTURE))
            .andExpect(jsonPath("$.url_picture").value(DEFAULT_URL_PICTURE.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.intValue()))
            .andExpect(jsonPath("$.pos_matrix").value(DEFAULT_POS_MATRIX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPicture() throws Exception {
        // Get the picture
        restPictureMockMvc.perform(get("/api/pictures/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePicture() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        // Update the picture
        picture.setId_picture(UPDATED_ID_PICTURE);
        picture.setUrl_picture(UPDATED_URL_PICTURE);
        picture.setHeight(UPDATED_HEIGHT);
        picture.setWidth(UPDATED_WIDTH);
        picture.setPos_matrix(UPDATED_POS_MATRIX);
        restPictureMockMvc.perform(post("/api/pictures")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(picture)))
                .andExpect(status().isOk());

        // Validate the Picture in the database
        List<Picture> pictures = pictureRepository.findAll();
        assertThat(pictures).hasSize(1);
        Picture testPicture = pictures.iterator().next();
        assertThat(testPicture.getId_picture()).isEqualTo(UPDATED_ID_PICTURE);
        assertThat(testPicture.getUrl_picture()).isEqualTo(UPDATED_URL_PICTURE);
        assertThat(testPicture.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPicture.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testPicture.getPos_matrix()).isEqualTo(UPDATED_POS_MATRIX);
    }

    @Test
    @Transactional
    public void deletePicture() throws Exception {
        // Initialize the database
        pictureRepository.saveAndFlush(picture);

        // Get the picture
        restPictureMockMvc.perform(delete("/api/pictures/{id}", picture.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Picture> pictures = pictureRepository.findAll();
        assertThat(pictures).hasSize(0);
    }
}
