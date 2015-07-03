package ro.suntem.egali.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.suntem.egali.domain.UserJob;
import ro.suntem.egali.repository.UserJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing UserJob.
 */
@RestController
@RequestMapping("/api")
public class UserJobResource {

    private final Logger log = LoggerFactory.getLogger(UserJobResource.class);

    @Inject
    private UserJobRepository userJobRepository;

    /**
     * POST  /userJobs -> Create a new userJob.
     */
    @RequestMapping(value = "/userJobs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody UserJob userJob) throws URISyntaxException {
        log.debug("REST request to save UserJob : {}", userJob);
        if (userJob.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new userJob cannot already have an ID").build();
        }
        userJobRepository.save(userJob);
        return ResponseEntity.created(new URI("/api/userJobs/" + userJob.getId())).build();
    }

    /**
     * PUT  /userJobs -> Updates an existing userJob.
     */
    @RequestMapping(value = "/userJobs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody UserJob userJob) throws URISyntaxException {
        log.debug("REST request to update UserJob : {}", userJob);
        if (userJob.getId() == null) {
            return create(userJob);
        }
        userJobRepository.save(userJob);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /userJobs -> get all the userJobs.
     */
    @RequestMapping(value = "/userJobs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserJob> getAll() {
        log.debug("REST request to get all UserJobs");
        return userJobRepository.findAll();
    }

    /**
     * GET  /userJobs/:id -> get the "id" userJob.
     */
    @RequestMapping(value = "/userJobs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserJob> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get UserJob : {}", id);
        UserJob userJob = userJobRepository.findOne(id);
        if (userJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userJob, HttpStatus.OK);
    }

    /**
     * DELETE  /userJobs/:id -> delete the "id" userJob.
     */
    @RequestMapping(value = "/userJobs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete UserJob : {}", id);
        userJobRepository.delete(id);
    }
}
