package ro.suntem.egali.web.rest;

import ro.suntem.egali.Application;
import ro.suntem.egali.domain.UserJob;
import ro.suntem.egali.repository.UserJobRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserJobResource REST controller.
 *
 * @see UserJobResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserJobResourceTest {


    private static final LocalDate DEFAULT_APPLY_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_APPLY_DATE = new LocalDate();

    @Inject
    private UserJobRepository userJobRepository;

    private MockMvc restUserJobMockMvc;

    private UserJob userJob;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserJobResource userJobResource = new UserJobResource();
        ReflectionTestUtils.setField(userJobResource, "userJobRepository", userJobRepository);
        this.restUserJobMockMvc = MockMvcBuilders.standaloneSetup(userJobResource).build();
    }

    @Before
    public void initTest() {
        userJob = new UserJob();
        userJob.setApplyDate(DEFAULT_APPLY_DATE);
    }

    @Test
    @Transactional
    public void createUserJob() throws Exception {
        int databaseSizeBeforeCreate = userJobRepository.findAll().size();

        // Create the UserJob
        restUserJobMockMvc.perform(post("/api/userJobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userJob)))
                .andExpect(status().isCreated());

        // Validate the UserJob in the database
        List<UserJob> userJobs = userJobRepository.findAll();
        assertThat(userJobs).hasSize(databaseSizeBeforeCreate + 1);
        UserJob testUserJob = userJobs.get(userJobs.size() - 1);
        assertThat(testUserJob.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
    }

    @Test
    @Transactional
    public void checkApplyDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(userJobRepository.findAll()).hasSize(0);
        // set the field null
        userJob.setApplyDate(null);

        // Create the UserJob, which fails.
        restUserJobMockMvc.perform(post("/api/userJobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userJob)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<UserJob> userJobs = userJobRepository.findAll();
        assertThat(userJobs).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllUserJobs() throws Exception {
        // Initialize the database
        userJobRepository.saveAndFlush(userJob);

        // Get all the userJobs
        restUserJobMockMvc.perform(get("/api/userJobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userJob.getId().intValue())))
                .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())));
    }

    @Test
    @Transactional
    public void getUserJob() throws Exception {
        // Initialize the database
        userJobRepository.saveAndFlush(userJob);

        // Get the userJob
        restUserJobMockMvc.perform(get("/api/userJobs/{id}", userJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userJob.getId().intValue()))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserJob() throws Exception {
        // Get the userJob
        restUserJobMockMvc.perform(get("/api/userJobs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserJob() throws Exception {
        // Initialize the database
        userJobRepository.saveAndFlush(userJob);

		int databaseSizeBeforeUpdate = userJobRepository.findAll().size();

        // Update the userJob
        userJob.setApplyDate(UPDATED_APPLY_DATE);
        restUserJobMockMvc.perform(put("/api/userJobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userJob)))
                .andExpect(status().isOk());

        // Validate the UserJob in the database
        List<UserJob> userJobs = userJobRepository.findAll();
        assertThat(userJobs).hasSize(databaseSizeBeforeUpdate);
        UserJob testUserJob = userJobs.get(userJobs.size() - 1);
        assertThat(testUserJob.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
    }

    @Test
    @Transactional
    public void deleteUserJob() throws Exception {
        // Initialize the database
        userJobRepository.saveAndFlush(userJob);

		int databaseSizeBeforeDelete = userJobRepository.findAll().size();

        // Get the userJob
        restUserJobMockMvc.perform(delete("/api/userJobs/{id}", userJob.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserJob> userJobs = userJobRepository.findAll();
        assertThat(userJobs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
