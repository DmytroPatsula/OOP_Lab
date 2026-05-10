package org.example;

import java.util.List;

public interface UserRepository {

    void addUser(User user);
    User findUserById(int id);
    boolean ifExists(int id);
    void deleteUser(int id);
    List<User> getAllUsers();
    void updateCitizen(Citizen citizen);
}
