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
import fr.istic.ludecol.domain.Tag;
import fr.istic.ludecol.repository.TagRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TagResource REST controller.
 *
 * @see TagResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TagResourceTest {


    private static final Integer DEFAULT_ID_TAG = 0;
    private static final Integer UPDATED_ID_TAG = 1;

    private static final Long DEFAULT_POS_X = 0L;
    private static final Long UPDATED_POS_X = 1L;

    private static final Long DEFAULT_POS_Y = 0L;
    private static final Long UPDATED_POS_Y = 1L;

    @Inject
    private TagRepository tagRepository;

    private MockMvc restTagMockMvc;

    private Tag tag;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TagResource tagResource = new TagResource();
        ReflectionTestUtils.setField(tagResource, "tagRepository", tagRepository);
        this.restTagMockMvc = MockMvcBuilders.standaloneSetup(tagResource).build();
    }

    @Before
    public void initTest() {
        tag = new Tag();
        tag.setId_tag(DEFAULT_ID_TAG);
        tag.setPos_x(DEFAULT_POS_X);
        tag.setPos_y(DEFAULT_POS_Y);
    }

    @Test
    @Transactional
    public void createTag() throws Exception {
        // Validate the database is empty
        assertThat(tagRepository.findAll()).hasSize(0);

        // Create the Tag
        restTagMockMvc.perform(post("/api/tags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tag)))
                .andExpect(status().isOk());

        // Validate the Tag in the database
        List<Tag> tags = tagRepository.findAll();
        assertThat(tags).hasSize(1);
        Tag testTag = tags.iterator().next();
        assertThat(testTag.getId_tag()).isEqualTo(DEFAULT_ID_TAG);
        assertThat(testTag.getPos_x()).isEqualTo(DEFAULT_POS_X);
        assertThat(testTag.getPos_y()).isEqualTo(DEFAULT_POS_Y);
    }

    @Test
    @Transactional
    public void getAllTags() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tags
        restTagMockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(tag.getId().intValue()))
                .andExpect(jsonPath("$.[0].id_tag").value(DEFAULT_ID_TAG))
                .andExpect(jsonPath("$.[0].pos_x").value(DEFAULT_POS_X.intValue()))
                .andExpect(jsonPath("$.[0].pos_y").value(DEFAULT_POS_Y.intValue()));
    }

    @Test
    @Transactional
    public void getTag() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get the tag
        restTagMockMvc.perform(get("/api/tags/{id}", tag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tag.getId().intValue()))
            .andExpect(jsonPath("$.id_tag").value(DEFAULT_ID_TAG))
            .andExpect(jsonPath("$.pos_x").value(DEFAULT_POS_X.intValue()))
            .andExpect(jsonPath("$.pos_y").value(DEFAULT_POS_Y.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTag() throws Exception {
        // Get the tag
        restTagMockMvc.perform(get("/api/tags/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTag() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Update the tag
        tag.setId_tag(UPDATED_ID_TAG);
        tag.setPos_x(UPDATED_POS_X);
        tag.setPos_y(UPDATED_POS_Y);
        restTagMockMvc.perform(post("/api/tags")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tag)))
                .andExpect(status().isOk());

        // Validate the Tag in the database
        List<Tag> tags = tagRepository.findAll();
        assertThat(tags).hasSize(1);
        Tag testTag = tags.iterator().next();
        assertThat(testTag.getId_tag()).isEqualTo(UPDATED_ID_TAG);
        assertThat(testTag.getPos_x()).isEqualTo(UPDATED_POS_X);
        assertThat(testTag.getPos_y()).isEqualTo(UPDATED_POS_Y);
    }

    @Test
    @Transactional
    public void deleteTag() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get the tag
        restTagMockMvc.perform(delete("/api/tags/{id}", tag.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tag> tags = tagRepository.findAll();
        assertThat(tags).hasSize(0);
    }
}
