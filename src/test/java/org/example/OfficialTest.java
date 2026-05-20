package org.example;

import org.example.business.Official;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OfficialTest {

    @Test
    void getUserInfo_ShouldReturnDepartmentString() {
        Official official = new Official("Oleg", 2, "Education", null);

        String info = official.getUserInfo();

        assertEquals("Department: Education", info);
    }

    @Test
    void getters_ShouldReturnCorrectValues() {
        Official official = new Official("Oleg", 2, "Education", null);

        assertEquals("Oleg", official.getName());
        assertEquals(2, official.getId());
        assertEquals("Education", official.getDepartment());
    }
}
