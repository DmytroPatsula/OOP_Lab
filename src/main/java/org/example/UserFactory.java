package org.example;

import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User createUser(String role, String name, int id, String address, int monthlyDebt, int currentDebt, String department, UserRepository userRepository) {
        if ("CITIZEN".equals(role)) {
            Citizen citizen = new Citizen(name, id, address, monthlyDebt);
            citizen.setCurrentDebt(currentDebt);
            return citizen;
        } else if ("OFFICIAL".equals(role)) {
            return new Official(name, id, department, userRepository);
        }

        throw new IllegalArgumentException("Невідома роль користувача: " + role);
    }
}
