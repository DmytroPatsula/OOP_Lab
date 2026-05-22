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

    private static final String URL = "jdbc:mysql://egov-mysql-db.mysql.database.azure.com:3306/egov?useSSL=true&requireSSL=true";
    private static final String USER = "egovadmin";
    private static final String PASSWORD = "E1234gov@pTSdy";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void addUser(User user){
        String insertUser = "INSERT INTO users (id, name, role) VALUES (?, ?, ?)";
        String insertCitizen = "INSERT INTO citizens (user_id, address, monthly_debt, current_debt) VALUES (?, ?, ?, ?)";
        String insertOfficial = "INSERT INTO officials (user_id, department) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            try (PreparedStatement pstmtUser = conn.prepareStatement(insertUser)) {
                pstmtUser.setInt(1, user.getId());
                pstmtUser.setString(2, user.getName());
                pstmtUser.setString(3, (user instanceof Citizen) ? "CITIZEN" : "OFFICIAL");
                pstmtUser.executeUpdate();
            }

            if (user instanceof Citizen citizen) {
                try (PreparedStatement pstmtCit = conn.prepareStatement(insertCitizen)) {
                    pstmtCit.setInt(1, citizen.getId());
                    pstmtCit.setString(2, citizen.getAddress());
                    pstmtCit.setInt(3, citizen.getMonthlyDebt());
                    pstmtCit.setInt(4, citizen.getCurrentDebt());
                    pstmtCit.executeUpdate();
                }
            } else if (user instanceof Official official) {
                try (PreparedStatement pstmtOff = conn.prepareStatement(insertOfficial)) {
                    pstmtOff.setInt(1, official.getId());
                    pstmtOff.setString(2, official.getDepartment());
                    pstmtOff.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Помилка бази даних при додаванні: " + e.getMessage());
        }
    }

    @Override
    public User findUserById(int id){
        String sql = "SELECT u.id, u.name, u.role, c.address, c.monthly_debt, c.current_debt, o.department " +
                "FROM users u " +
                "LEFT JOIN citizens c ON u.id = c.user_id " +
                "LEFT JOIN officials o ON u.id = o.user_id " +
                "WHERE u.id = ?";

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

    @Override
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
        String sql = "SELECT u.id, u.name, u.role, c.address, c.monthly_debt, c.current_debt, o.department " +
                "FROM users u " +
                "LEFT JOIN citizens c ON u.id = c.user_id " +
                "LEFT JOIN officials o ON u.id = o.user_id";

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
        String sql = "UPDATE citizens SET current_debt = ?, address = ? WHERE user_id = ?";
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