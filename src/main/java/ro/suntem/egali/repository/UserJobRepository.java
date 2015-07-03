package ro.suntem.egali.repository;

import ro.suntem.egali.domain.UserJob;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserJob entity.
 */
public interface UserJobRepository extends JpaRepository<UserJob,Long> {

    @Query("select userJob from UserJob userJob where userJob.user.login = ?#{principal.username}")
    List<UserJob> findAllForCurrentUser();

}
