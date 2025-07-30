package factories;

import api.models.User;
import java.util.UUID;

/**
 * Factory for creating User test data.
 */
public final class UserFactory {

    private UserFactory() {}

    /**
     * Builds a new User with unique email and default credentials.
     */
    public static User createRandomUserForRegister() {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        User user = new User();
        user.setEmail("qauser+" + unique + "@qubika.com");
        user.setPassword("Password123!");
        user.setFirstName("QA");
        user.setLastName("BIKA" + unique);
        user.setRoles(new String[]{"ROLE_ADMIN"});
        return user;
    }
}