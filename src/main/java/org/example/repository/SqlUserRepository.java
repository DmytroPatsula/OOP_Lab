package org.example.repository;

import org.example.business.User;
import org.example.business.Citizen;
import org.example.business.Official;
import org.example.business.UserFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Component
public class SqlUserRepository implements UserRepository {
    private final UserFactory userFactory;

    public SqlUserRepository(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    private static final String URL = "jdbc:mysql://localhost:3306/egov";
    private static final String USER = "root";
    private static final String PASSWORD = "PtsDYSQL1234";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void addUser(User user){
        String sql = "INSERT INTO users (id, name, role, address, monthly_debt, department) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());

            if (user instanceof Citizen citizen) {
                pstmt.setString(3, "CITIZEN");
                pstmt.setString(4, citizen.getAddress());
                pstmt.setInt(5, citizen.getMonthlyDebt());
                pstmt.setNull(6, Types.VARCHAR);
            } else if (user instanceof Official official) {
                pstmt.setString(3, "OFFICIAL");
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setString(6, official.getDepartment());
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Помилка бази даних при додаванні: " + e.getMessage());
        }
    }

    @Override
    public User findUserById(int id){
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String role = rs.getString("role");
                String address = rs.getString("address");
                int monthlyDebt = rs.getInt("monthly_debt");
                int currentDebt = rs.getInt("current_debt");
                String department = rs.getString("department");

                return userFactory.createUser(role, name, id, address, monthlyDebt, currentDebt, department, this);
            }
        } catch (SQLException e) {
            System.out.println("Помилка бази даних при пошуку: " + e.getMessage());
        }
        return null;
    }

    public boolean ifExists(int id) {
        String sql = "SELECT 1 FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Помилка перевірки існування: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void deleteUser(int id){
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Помилка при видаленні: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String address = rs.getString("address");
                int monthlyDebt = rs.getInt("monthly_debt");
                int currentDebt = rs.getInt("current_debt");
                String department = rs.getString("department");

                User user = userFactory.createUser(role, name, id, address, monthlyDebt, currentDebt, department, this);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Помилка при отриманні списку: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void updateCitizen(Citizen citizen) {
        String sql = "UPDATE users SET current_debt = ?, address = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, citizen.getCurrentDebt());
            pstmt.setString(2, citizen.getAddress());
            pstmt.setInt(3, citizen.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Помилка оновлення: " + e.getMessage());
        }
    }
}
