package org.example.ui;

import org.example.business.Citizen;
import org.example.business.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppFacade {
    private final UserRepository userRepository;

    public AppFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public String simulateMonth() {
        List<User> allUsers = userRepository.getAllUsers();
        int affectedCount = 0;
        for (User u : allUsers) {
            if (u instanceof Citizen citizen) {
                citizen.monthSimulation();
                userRepository.updateCitizen(citizen);
                affectedCount++;
            }
        }
        return "1 month passed. Taxes added for " + affectedCount + " citizens.";
    }

    public String payCitizenTax(int id, int amount) {
        User user = userRepository.findUserById(id);
        if (user instanceof Citizen citizen) {
            citizen.payTax(amount);
            userRepository.updateCitizen(citizen);
            return "Tax operation completed for " + citizen.getName() + ". Current debt: " + citizen.getCurrentDebt();
        }
        return "Error: Citizen not found or is an Official.";
    }

    public String addCitizen(String name, int id, String address, int monthlyDebt) {
        if (userRepository.ifExists(id)) {
            return "Error: User with ID " + id + " already exists.";
        }
        Citizen newCitizen = new Citizen(name, id, address, monthlyDebt);
        userRepository.addUser(newCitizen);
        return "Citizen " + name + " successfully registered!";
    }

    public String deleteUser(int id) {
        if (userRepository.ifExists(id)) {
            userRepository.deleteUser(id);
            return "User with ID " + id + " successfully deleted.";
        }
        return "Error: User with ID " + id + " not found.";
    }

    public String changeAddress(int id, String newAddress) {
        User user = userRepository.findUserById(id);
        if (user instanceof Citizen citizen) {
            citizen.setAddress(newAddress);
            userRepository.updateCitizen(citizen);
            return "Address for " + citizen.getName() + " updated to: " + newAddress;
        }
        return "Error: Citizen with ID " + id + " not found.";
    }


}
