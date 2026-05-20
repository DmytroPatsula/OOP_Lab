package org.example;

import org.example.business.Citizen;
import org.example.business.strategy.FullPaymentStrategy;
import org.example.business.strategy.PartialPaymentStrategy;
import org.example.business.strategy.PaymentStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentStrategyTest {

    @Test
    void fullPaymentStrategy_ShouldSetDebtToZero() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(500);

        PaymentStrategy strategy = new FullPaymentStrategy();
        strategy.processPayment(citizen, 0);

        assertEquals(0, citizen.getCurrentDebt(), "Debt should be 0 after full payment strategy");
    }

    @Test
    void partialPaymentStrategy_ShouldReduceDebtByAmount() {
        Citizen citizen = new Citizen("Ivan", 1, "Lviv", 1000);
        citizen.setCurrentDebt(500);

        PaymentStrategy strategy = new PartialPaymentStrategy();
        strategy.processPayment(citizen, 200);

        assertEquals(300, citizen.getCurrentDebt(), "Debt should be reduced by 200");
    }
}