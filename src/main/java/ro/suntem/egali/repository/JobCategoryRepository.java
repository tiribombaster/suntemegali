package ro.suntem.egali.repository;

import ro.suntem.egali.domain.JobCategory;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobCategory entity.
 */
public interface JobCategoryRepository extends JpaRepository<JobCategory,Long> {

}
