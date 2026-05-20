package org.example.business.strategy;

import org.example.business.Citizen;

public class PartialPaymentStrategy implements PaymentStrategy{
    @Override
    public void processPayment(Citizen citizen, int amount) {
        citizen.payTax(amount);
    }
}
