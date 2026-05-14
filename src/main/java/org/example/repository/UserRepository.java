package org.example.repository;

import org.example.business.Citizen;
import org.example.business.User;

import java.util.List;

public interface UserRepository {

    void addUser(User user);
    User findUserById(int id);
    boolean ifExists(int id);
    void deleteUser(int id);
    List<User> getAllUsers();
    void updateCitizen(Citizen citizen);
}
