package ro.suntem.egali.repository;

import ro.suntem.egali.domain.JobToJobCategory;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobToJobCategory entity.
 */
public interface JobToJobCategoryRepository extends JpaRepository<JobToJobCategory,Long> {

}
