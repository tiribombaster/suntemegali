package ro.suntem.egali.web.rest;

import ro.suntem.egali.Application;
import ro.suntem.egali.domain.JobCategory;
import ro.suntem.egali.repository.JobCategoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobCategoryResource REST controller.
 *
 * @see JobCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobCategoryResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private JobCategoryRepository jobCategoryRepository;

    private MockMvc restJobCategoryMockMvc;

    private JobCategory jobCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobCategoryResource jobCategoryResource = new JobCategoryResource();
        ReflectionTestUtils.setField(jobCategoryResource, "jobCategoryRepository", jobCategoryRepository);
        this.restJobCategoryMockMvc = MockMvcBuilders.standaloneSetup(jobCategoryResource).build();
    }

    @Before
    public void initTest() {
        jobCategory = new JobCategory();
        jobCategory.setName(DEFAULT_NAME);
        jobCategory.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createJobCategory() throws Exception {
        int databaseSizeBeforeCreate = jobCategoryRepository.findAll().size();

        // Create the JobCategory
        restJobCategoryMockMvc.perform(post("/api/jobCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobCategory)))
                .andExpect(status().isCreated());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategorys = jobCategoryRepository.findAll();
        assertThat(jobCategorys).hasSize(databaseSizeBeforeCreate + 1);
        JobCategory testJobCategory = jobCategorys.get(jobCategorys.size() - 1);
        assertThat(testJobCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobCategoryRepository.findAll()).hasSize(0);
        // set the field null
        jobCategory.setName(null);

        // Create the JobCategory, which fails.
        restJobCategoryMockMvc.perform(post("/api/jobCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobCategory)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<JobCategory> jobCategorys = jobCategoryRepository.findAll();
        assertThat(jobCategorys).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllJobCategorys() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategorys
        restJobCategoryMockMvc.perform(get("/api/jobCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getJobCategory() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get the jobCategory
        restJobCategoryMockMvc.perform(get("/api/jobCategorys/{id}", jobCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobCategory() throws Exception {
        // Get the jobCategory
        restJobCategoryMockMvc.perform(get("/api/jobCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobCategory() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

		int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();

        // Update the jobCategory
        jobCategory.setName(UPDATED_NAME);
        jobCategory.setDescription(UPDATED_DESCRIPTION);
        restJobCategoryMockMvc.perform(put("/api/jobCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobCategory)))
                .andExpect(status().isOk());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategorys = jobCategoryRepository.findAll();
        assertThat(jobCategorys).hasSize(databaseSizeBeforeUpdate);
        JobCategory testJobCategory = jobCategorys.get(jobCategorys.size() - 1);
        assertThat(testJobCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteJobCategory() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

		int databaseSizeBeforeDelete = jobCategoryRepository.findAll().size();

        // Get the jobCategory
        restJobCategoryMockMvc.perform(delete("/api/jobCategorys/{id}", jobCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobCategory> jobCategorys = jobCategoryRepository.findAll();
        assertThat(jobCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
