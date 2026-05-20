package org.example.business.strategy;

import org.example.business.Citizen;

public interface PaymentStrategy {
    void processPayment(Citizen citizen, int amount);
}
