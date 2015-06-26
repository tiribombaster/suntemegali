package ro.suntem.egali.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.suntem.egali.domain.JobToJobCategory;
import ro.suntem.egali.repository.JobToJobCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing JobToJobCategory.
 */
@RestController
@RequestMapping("/api")
public class JobToJobCategoryResource {

    private final Logger log = LoggerFactory.getLogger(JobToJobCategoryResource.class);

    @Inject
    private JobToJobCategoryRepository jobToJobCategoryRepository;

    /**
     * POST  /jobToJobCategorys -> Create a new jobToJobCategory.
     */
    @RequestMapping(value = "/jobToJobCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody JobToJobCategory jobToJobCategory) throws URISyntaxException {
        log.debug("REST request to save JobToJobCategory : {}", jobToJobCategory);
        if (jobToJobCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobToJobCategory cannot already have an ID").build();
        }
        jobToJobCategoryRepository.save(jobToJobCategory);
        return ResponseEntity.created(new URI("/api/jobToJobCategorys/" + jobToJobCategory.getId())).build();
    }

    /**
     * PUT  /jobToJobCategorys -> Updates an existing jobToJobCategory.
     */
    @RequestMapping(value = "/jobToJobCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody JobToJobCategory jobToJobCategory) throws URISyntaxException {
        log.debug("REST request to update JobToJobCategory : {}", jobToJobCategory);
        if (jobToJobCategory.getId() == null) {
            return create(jobToJobCategory);
        }
        jobToJobCategoryRepository.save(jobToJobCategory);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /jobToJobCategorys -> get all the jobToJobCategorys.
     */
    @RequestMapping(value = "/jobToJobCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JobToJobCategory> getAll() {
        log.debug("REST request to get all JobToJobCategorys");
        return jobToJobCategoryRepository.findAll();
    }

    /**
     * GET  /jobToJobCategorys/:id -> get the "id" jobToJobCategory.
     */
    @RequestMapping(value = "/jobToJobCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobToJobCategory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get JobToJobCategory : {}", id);
        JobToJobCategory jobToJobCategory = jobToJobCategoryRepository.findOne(id);
        if (jobToJobCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jobToJobCategory, HttpStatus.OK);
    }

    /**
     * DELETE  /jobToJobCategorys/:id -> delete the "id" jobToJobCategory.
     */
    @RequestMapping(value = "/jobToJobCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete JobToJobCategory : {}", id);
        jobToJobCategoryRepository.delete(id);
    }
}
