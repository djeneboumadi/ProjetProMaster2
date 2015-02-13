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
import fr.istic.ludecol.domain.Tags;
import fr.istic.ludecol.repository.TagsRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TagsResource REST controller.
 *
 * @see TagsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TagsResourceTest {


    private static final Integer DEFAULT_ID_TAG = 0;
    private static final Integer UPDATED_ID_TAG = 1;

    private static final Long DEFAULT_POS_X = 0L;
    private static final Long UPDATED_POS_X = 1L;

    private static final Long DEFAULT_POS_Y = 0L;
    private static final Long UPDATED_POS_Y = 1L;

    @Inject
    private TagsRepository tagsRepository;

    private MockMvc restTagsMockMvc;

    private Tags tags;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TagsResource tagsResource = new TagsResource();
        ReflectionTestUtils.setField(tagsResource, "tagsRepository", tagsRepository);
        this.restTagsMockMvc = MockMvcBuilders.standaloneSetup(tagsResource).build();
    }

    @Before
    public void initTest() {
        tags = new Tags();
        tags.setPos_x(DEFAULT_POS_X);
        tags.setPos_y(DEFAULT_POS_Y);
    }

    @Test
    @Transactional
    public void createTags() throws Exception {
        // Validate the database is empty
        assertThat(tagsRepository.findAll()).hasSize(0);

        // Create the Tags
        restTagsMockMvc.perform(post("/api/tagss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tags)))
                .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagss = tagsRepository.findAll();
        assertThat(tagss).hasSize(1);
        Tags testTags = tagss.iterator().next();
        assertThat(testTags.getPos_x()).isEqualTo(DEFAULT_POS_X);
        assertThat(testTags.getPos_y()).isEqualTo(DEFAULT_POS_Y);
    }

    @Test
    @Transactional
    public void getAllTagss() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        // Get all the tagss
        restTagsMockMvc.perform(get("/api/tagss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(tags.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_tag").value(DEFAULT_ID_TAG))
                .andExpect(jsonPath("$.[0].pos_x").value(DEFAULT_POS_X.intValue()))
                .andExpect(jsonPath("$.[0].pos_y").value(DEFAULT_POS_Y.intValue()));
    }

    @Test
    @Transactional
    public void getTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        // Get the tags
        restTagsMockMvc.perform(get("/api/tagss/{id}", tags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tags.getId().intValue()))
            .andExpect(jsonPath("$.id_tag").value(DEFAULT_ID_TAG))
            .andExpect(jsonPath("$.pos_x").value(DEFAULT_POS_X.intValue()))
            .andExpect(jsonPath("$.pos_y").value(DEFAULT_POS_Y.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTags() throws Exception {
        // Get the tags
        restTagsMockMvc.perform(get("/api/tagss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        // Update the tags
        tags.setPos_x(UPDATED_POS_X);
        tags.setPos_y(UPDATED_POS_Y);
        restTagsMockMvc.perform(post("/api/tagss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tags)))
                .andExpect(status().isOk());

        // Validate the Tags in the database
        List<Tags> tagss = tagsRepository.findAll();
        assertThat(tagss).hasSize(1);
        Tags testTags = tagss.iterator().next();
        assertThat(testTags.getPos_x()).isEqualTo(UPDATED_POS_X);
        assertThat(testTags.getPos_y()).isEqualTo(UPDATED_POS_Y);
    }

    @Test
    @Transactional
    public void deleteTags() throws Exception {
        // Initialize the database
        tagsRepository.saveAndFlush(tags);

        // Get the tags
        restTagsMockMvc.perform(delete("/api/tagss/{id}", tags.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tags> tagss = tagsRepository.findAll();
        assertThat(tagss).hasSize(0);
    }
}
