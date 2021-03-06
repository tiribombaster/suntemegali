/*
package ro.suntem.egali.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ro.suntem.egali.Application;
import ro.suntem.egali.domain.PersistentToken;
import ro.suntem.egali.domain.User;
import ro.suntem.egali.repository.PersistentTokenRepository;
import ro.suntem.egali.repository.UserRepository;
import ro.suntem.egali.service.util.RandomUtil;

*/
/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class UserServiceTest {

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Test
    public void testRemoveOldPersistentTokens() {
        User admin = userRepository.findOneByLogin("admin");
        int existingCount = persistentTokenRepository.findByUser(admin).size();
        generateUserToken(admin, "1111-1111", new LocalDate());
        LocalDate now = new LocalDate();
        generateUserToken(admin, "2222-2222", now.minusDays(32));
        assertThat(persistentTokenRepository.findByUser(admin)).hasSize(existingCount + 2);
        userService.removeOldPersistentTokens();
        assertThat(persistentTokenRepository.findByUser(admin)).hasSize(existingCount + 1);
    }

    @Test
    public void assertThatUserMustExistToResetPassword() {

        User user = userService.requestPasswordReset("Marius@localhost");
        assertThat(user).isNull();

        user = userService.requestPasswordReset("admin@localhost");
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("admin@localhost");
        assertThat(user.getResetDate()).isNotNull();
        assertThat(user.getResetKey()).isNotNull();

    }

    @Test
    @Ignore
    public void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");
        User maybeUser = userService.requestPasswordReset("john.doe@localhost");
        assertThat(maybeUser).isNull();
        userRepository.delete(user);
    }

    @Test
    @Ignore
    public void assertThatResetKeyMustNotBeOlderThan24Hours() {

        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        DateTime daysAgo = DateTime.now().minusHours(25);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);

        userRepository.save(user);

        User maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

        assertThat(maybeUser).isNull();

        userRepository.delete(user);

    }

    @Test
    @Ignore
    public void assertThatResetKeyMustBeValid() {

        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        DateTime daysAgo = DateTime.now().minusHours(25);
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey("1234");

        userRepository.save(user);

        User maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

        assertThat(maybeUser).isNull();

        userRepository.delete(user);

    }

    @Test
    @Ignore
    public void assertThatUserCanResetPassword() {

        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        String oldPassword = user.getPassword();

        DateTime daysAgo = DateTime.now().minusHours(2);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);

        userRepository.save(user);

        User maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

        assertThat(maybeUser).isNotNull();
        assertThat(maybeUser.getResetDate()).isNull();
        assertThat(maybeUser.getResetKey()).isNull();
        assertThat(maybeUser.getPassword()).isNotEqualTo(oldPassword);

        userRepository.delete(user);

    }

    @Test
    public void testFindNotActivatedUsersByCreationDateBefore() {
        userService.removeNotActivatedUsers();
        DateTime now = new DateTime();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        assertThat(users).isEmpty();
    }

    private void generateUserToken(User user, String tokenSeries, LocalDate localDate) {
        PersistentToken token = new PersistentToken();
        token.setSeries(tokenSeries);
        token.setUser(user);
        token.setTokenValue(tokenSeries + "-data");
        token.setTokenDate(localDate);
        token.setIpAddress("127.0.0.1");
        token.setUserAgent("Test agent");
        persistentTokenRepository.saveAndFlush(token);
    }
}
*/
