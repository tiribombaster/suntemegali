package ro.suntem.egali.web.rest;

import ro.suntem.egali.Application;
import ro.suntem.egali.domain.JobToJobCategory;
import ro.suntem.egali.repository.JobToJobCategoryRepository;

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
 * Test class for the JobToJobCategoryResource REST controller.
 *
 * @see JobToJobCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobToJobCategoryResourceTest {


    @Inject
    private JobToJobCategoryRepository jobToJobCategoryRepository;

    private MockMvc restJobToJobCategoryMockMvc;

    private JobToJobCategory jobToJobCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobToJobCategoryResource jobToJobCategoryResource = new JobToJobCategoryResource();
        ReflectionTestUtils.setField(jobToJobCategoryResource, "jobToJobCategoryRepository", jobToJobCategoryRepository);
        this.restJobToJobCategoryMockMvc = MockMvcBuilders.standaloneSetup(jobToJobCategoryResource).build();
    }

    @Before
    public void initTest() {
        jobToJobCategory = new JobToJobCategory();
    }

    @Test
    @Transactional
    public void createJobToJobCategory() throws Exception {
        int databaseSizeBeforeCreate = jobToJobCategoryRepository.findAll().size();

        // Create the JobToJobCategory
        restJobToJobCategoryMockMvc.perform(post("/api/jobToJobCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobToJobCategory)))
                .andExpect(status().isCreated());

        // Validate the JobToJobCategory in the database
        List<JobToJobCategory> jobToJobCategorys = jobToJobCategoryRepository.findAll();
        assertThat(jobToJobCategorys).hasSize(databaseSizeBeforeCreate + 1);
        JobToJobCategory testJobToJobCategory = jobToJobCategorys.get(jobToJobCategorys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllJobToJobCategorys() throws Exception {
        // Initialize the database
        jobToJobCategoryRepository.saveAndFlush(jobToJobCategory);

        // Get all the jobToJobCategorys
        restJobToJobCategoryMockMvc.perform(get("/api/jobToJobCategorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobToJobCategory.getId().intValue())));
    }

    @Test
    @Transactional
    public void getJobToJobCategory() throws Exception {
        // Initialize the database
        jobToJobCategoryRepository.saveAndFlush(jobToJobCategory);

        // Get the jobToJobCategory
        restJobToJobCategoryMockMvc.perform(get("/api/jobToJobCategorys/{id}", jobToJobCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobToJobCategory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJobToJobCategory() throws Exception {
        // Get the jobToJobCategory
        restJobToJobCategoryMockMvc.perform(get("/api/jobToJobCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobToJobCategory() throws Exception {
        // Initialize the database
        jobToJobCategoryRepository.saveAndFlush(jobToJobCategory);

		int databaseSizeBeforeUpdate = jobToJobCategoryRepository.findAll().size();

        // Update the jobToJobCategory
        restJobToJobCategoryMockMvc.perform(put("/api/jobToJobCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobToJobCategory)))
                .andExpect(status().isOk());

        // Validate the JobToJobCategory in the database
        List<JobToJobCategory> jobToJobCategorys = jobToJobCategoryRepository.findAll();
        assertThat(jobToJobCategorys).hasSize(databaseSizeBeforeUpdate);
        JobToJobCategory testJobToJobCategory = jobToJobCategorys.get(jobToJobCategorys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteJobToJobCategory() throws Exception {
        // Initialize the database
        jobToJobCategoryRepository.saveAndFlush(jobToJobCategory);

		int databaseSizeBeforeDelete = jobToJobCategoryRepository.findAll().size();

        // Get the jobToJobCategory
        restJobToJobCategoryMockMvc.perform(delete("/api/jobToJobCategorys/{id}", jobToJobCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobToJobCategory> jobToJobCategorys = jobToJobCategoryRepository.findAll();
        assertThat(jobToJobCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
