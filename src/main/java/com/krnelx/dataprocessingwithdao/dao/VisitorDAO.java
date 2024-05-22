package main.java.com.krnelx.dataprocessingwithdao.dao;

import main.java.com.krnelx.dataprocessingwithdao.model.Visitor;
import main.java.com.krnelx.dataprocessingwithdao.dao.exceptions.DatabaseConnectionException;
import main.java.com.krnelx.dataprocessingwithdao.dao.exceptions.RecordNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.VisitorValidator;

public class VisitorDAO {

    // Private static instance of VisitorDAO
    private static final VisitorDAO instance = new VisitorDAO();

    // Private constructor to prevent instantiation outside of this class
    private VisitorDAO() {}

    // Method to get the singleton instance of VisitorDAO
    public static VisitorDAO getInstance() {
        return instance;
    }

    // Establish connection with the SQLite database
    private Connection connect() throws DatabaseConnectionException {
        String url = "jdbc:sqlite:src/main/resources/db/zoo_database";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to the database: " + e.getMessage(), e);
        }
        return conn;
    }

    // Add a new visitor to the database
    public void addVisitor(Visitor visitor) {
        VisitorValidator validator = new VisitorValidator()
            .name(visitor.getName())
            .age(visitor.getAge());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Visitor data is invalid: " + validationErrors);
        }

        String sql = "INSERT INTO Visitor(id, name, age) VALUES(?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(visitor.getId()));
            pstmt.setString(2, visitor.getName());
            pstmt.setInt(3, visitor.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add visitor to the database: " + e.getMessage(), e);
        }
    }

    // Retrieve a list of all visitors from the database
    public List<Visitor> getAllVisitors() throws DatabaseConnectionException {
        String sql = "SELECT * FROM Visitor";
        List<Visitor> visitors = new ArrayList<>();

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            // Iterate through the query results and create Visitor objects
            while (rs.next()) {
                Visitor visitor = new Visitor();
                visitor.setId(UUID.fromString(rs.getString("id")));
                visitor.setName(rs.getString("name"));
                visitor.setAge(rs.getInt("age"));
                visitors.add(visitor);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve visitors from the database: " + e.getMessage(), e);
        }
        return visitors;
    }

    // Retrieve a visitor by their identifier
    public Visitor getVisitorById(UUID id) throws DatabaseConnectionException, RecordNotFoundException {
        String sql = "SELECT * FROM Visitor WHERE id = ?";
        Visitor visitor = null;

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");

                visitor = Visitor.builder()
                    .id(id)
                    .name(name)
                    .age(age)
                    .build();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve visitor from the database: " + e.getMessage(), e);
        }
        return visitor;
    }

    // Update a visitor
    public void updateVisitor(Visitor visitor) {
        VisitorValidator validator = new VisitorValidator()
            .name(visitor.getName())
            .age(visitor.getAge());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Visitor data is invalid: " + validationErrors);
        }

        String sql = "UPDATE Visitor SET name = ?, age = ? WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, visitor.getName());
            pstmt.setInt(2, visitor.getAge());
            pstmt.setString(3, visitor.getId().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update visitor in the database: " + e.getMessage(), e);
        }
    }

    // Delete a visitor
    public void deleteVisitor(UUID id) throws DatabaseConnectionException {
        String sql = "DELETE FROM Visitor WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to delete visitor from the database: " + e.getMessage(), e);
        }
    }
}
