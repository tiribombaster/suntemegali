package ro.suntem.egali.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.suntem.egali.domain.JobCategory;
import ro.suntem.egali.repository.JobCategoryRepository;
import ro.suntem.egali.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing JobCategory.
 */
@RestController
@RequestMapping("/api")
public class JobCategoryResource {

    private final Logger log = LoggerFactory.getLogger(JobCategoryResource.class);

    @Inject
    private JobCategoryRepository jobCategoryRepository;

    /**
     * POST  /jobCategorys -> Create a new jobCategory.
     */
    @RequestMapping(value = "/jobCategorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody JobCategory jobCategory) throws URISyntaxException {
        log.debug("REST request to save JobCategory : {}", jobCategory);
        if (jobCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jobCategory cannot already have an ID").build();
        }
        jobCategoryRepository.save(jobCategory);
        return ResponseEntity.created(new URI("/api/jobCategorys/" + jobCategory.getId())).build();
    }

    /**
     * PUT  /jobCategorys -> Updates an existing jobCategory.
     */
    @RequestMapping(value = "/jobCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody JobCategory jobCategory) throws URISyntaxException {
        log.debug("REST request to update JobCategory : {}", jobCategory);
        if (jobCategory.getId() == null) {
            return create(jobCategory);
        }
        jobCategoryRepository.save(jobCategory);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /jobCategorys -> get all the jobCategorys.
     */
    @RequestMapping(value = "/jobCategorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobCategory>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<JobCategory> page = jobCategoryRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jobCategorys", offset, limit);
        return new ResponseEntity<List<JobCategory>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jobCategorys/:id -> get the "id" jobCategory.
     */
    @RequestMapping(value = "/jobCategorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobCategory> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get JobCategory : {}", id);
        JobCategory jobCategory = jobCategoryRepository.findOne(id);
        if (jobCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jobCategory, HttpStatus.OK);
    }

    /**
     * DELETE  /jobCategorys/:id -> delete the "id" jobCategory.
     */
    @RequestMapping(value = "/jobCategorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete JobCategory : {}", id);
        jobCategoryRepository.delete(id);
    }
}
