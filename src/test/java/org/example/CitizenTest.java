package org.example;

import org.example.business.Citizen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CitizenTest {

    @Test
    void payTax_PartialAmount_ShouldReduceDebt() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(2000);

        citizen.payTax(500);

        assertEquals(1500, citizen.getCurrentDebt(), "Debt should be reduced (2000 - 500 = 1500)");
    }

    @Test
    void payTax_FullAmount_ShouldReduceDebt() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(2000);

        citizen.payTax(2000);

        assertEquals(0, citizen.getCurrentDebt(), "Debt should be reduced (2000 - 2000 = 0)");
    }

    @Test
    void payTax_FullAmountZero_ShouldReduceDebt() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(2000);

        citizen.payTax();

        assertEquals(0, citizen.getCurrentDebt(), "Debt should be reduced (2000 - full = 0)");
    }

    @Test
    void payTax_FullAmountAndChange_ShouldReduceDebt() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(2000);

        citizen.payTax(3000);

        assertEquals(0, citizen.getCurrentDebt(), "Debt should be reduced (2000 - 3000 = 0 + change 1000)");
    }

    @Test
    void payTax_NegativeAmount_DebtShouldStaySame() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(2000);

        citizen.payTax(-1000);

        assertEquals(2000, citizen.getCurrentDebt(), "Debt should be same");
    }

    @Test
    void payTax_Zero_DebtShouldStaySame() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(2000);

        citizen.payTax(0);

        assertEquals(2000, citizen.getCurrentDebt(), "Debt should be same");
    }

    @Test
    void payTax_WhenNoDebt_DebtShouldRemainZero() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(0);

        citizen.payTax(500);

        assertEquals(0, citizen.getCurrentDebt(), "Debt should remain 0 when already paid");
    }

    @Test
    void monthSimulation_WithoutDebt () {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(0);

        citizen.monthSimulation();

        assertEquals(1000, citizen.getCurrentDebt(), "Debt should be raised by 1000");
    }

    @Test
    void monthSimulation_WithDebt() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(1000);

        citizen.monthSimulation();

        assertEquals(2000, citizen.getCurrentDebt(), "Debt should be raised by 1000, and = 2000");
    }

    @Test
    void monthSimulation_WithZeroMonthlyDebt() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 0);
        citizen.setCurrentDebt(0);

        citizen.monthSimulation();

        assertEquals(0, citizen.getCurrentDebt(), "Debt should remain 0 if monthly tax is 0");
    }

    @Test
    void setAddress_ValidString_AddressShouldChange() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);

        citizen.setAddress("Kyiv");

        assertEquals("Kyiv", citizen.getAddress(), "Address should be updated to Kyiv");
    }

    @Test
    void getUserInfo_WithDebt_ShouldReturnCorrectString() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(500);

        String info = citizen.getUserInfo();

        assertTrue(info.contains("Address: Lviv"));
        assertTrue(info.contains("Taxes: Debt - 500"), "Taxes should be (amount)");
    }

    @Test
    void getUserInfo_NoDebt_ShouldReturnPaidStatus() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(0);

        String info = citizen.getUserInfo();

        assertTrue(info.contains("Taxes: Paid"), "Taxes should be (paid)");
    }

}