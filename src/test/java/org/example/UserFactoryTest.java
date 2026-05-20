package org.example;

import org.example.business.Citizen;
import org.example.business.Official;
import org.example.business.User;
import org.example.business.UserFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void createUser_WithRoleCitizen_ShouldReturnCitizenObject() {
        UserFactory factory = new UserFactory();

        User user = factory.createUser("CITIZEN", "Anna", 10, "Kyiv", 1000, 500, null, null);

        assertTrue(user instanceof Citizen, "Factory should return instance of Citizen");
        assertEquals("Anna", user.getName());
        assertEquals(10, user.getId());
        assertEquals(500, ((Citizen) user).getCurrentDebt(), "Current debt should be mapped correctly");
    }

    @Test
    void createUser_WithRoleOfficial_ShouldReturnOfficialObject() {
        UserFactory factory = new UserFactory();

        User user = factory.createUser("OFFICIAL", "Petro", 20, null, 0, 0, "Taxes", null);

        assertTrue(user instanceof Official, "Factory should return instance of Official");
        assertEquals("Taxes", ((Official) user).getDepartment());
    }

    @Test
    void createUser_WithUnknownRole_ShouldThrowException() {
        UserFactory factory = new UserFactory();

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> factory.createUser("ADMIN", "A", 99, null, 0, 0, null, null),
                "Expected createUser() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Невідома роль"), "Exception message should contain expected text");
    }
}