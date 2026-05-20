package org.example.business.strategy;

import org.example.business.Citizen;

public class FullPaymentStrategy implements PaymentStrategy{
    @Override
    public void processPayment(Citizen citizen, int amount) {
        citizen.payTax();
    }
}
