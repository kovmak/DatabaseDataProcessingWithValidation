package main.java.com.krnelx.dataprocessingwithdao.dao;

import main.java.com.krnelx.dataprocessingwithdao.model.Enclosure;
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
import main.java.com.krnelx.dataprocessingwithdao.service.validation.EnclosureValidator;

public class EnclosureDAO {

    // Private static instance of EnclosureDAO
    private static final EnclosureDAO instance = new EnclosureDAO();

    // Private constructor to prevent instantiation outside of this class
    private EnclosureDAO() {}

    // Method to get the singleton instance of EnclosureDAO
    public static EnclosureDAO getInstance() {
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

    // Add a new enclosure to the database
    public void addEnclosure(Enclosure enclosure) {
        EnclosureValidator validator = new EnclosureValidator()
            .name(enclosure.getName())
            .type(enclosure.getType())
            .capacity(enclosure.getCapacity());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Enclosure data is invalid: " + validationErrors);
        }

        String sql = "INSERT INTO Enclosure(id, name, type, capacity) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(enclosure.getId()));
            pstmt.setString(2, enclosure.getName());
            pstmt.setString(3, enclosure.getType());
            pstmt.setInt(4, enclosure.getCapacity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add enclosure to the database: " + e.getMessage(), e);
        }
    }

    // Retrieve a list of all enclosures from the database
    public List<Enclosure> getAllEnclosures() throws DatabaseConnectionException {
        String sql = "SELECT * FROM Enclosure";
        List<Enclosure> enclosures = new ArrayList<>();

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            // Iterate through the query results and create Enclosure objects
            while (rs.next()) {
                Enclosure enclosure = new Enclosure();
                enclosure.setId(UUID.fromString(rs.getString("id")));
                enclosure.setName(rs.getString("name"));
                enclosure.setType(rs.getString("type"));
                enclosure.setCapacity(rs.getInt("capacity"));
                enclosures.add(enclosure);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve enclosures from the database: " + e.getMessage(), e);
        }
        return enclosures;
    }

    // Retrieve an enclosure by its UUID from the database
    public Enclosure getEnclosureById(UUID id) throws DatabaseConnectionException, RecordNotFoundException {
        String sql = "SELECT * FROM Enclosure WHERE id = ?";
        Enclosure enclosure = null;

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String type = rs.getString("type");

                enclosure = Enclosure.builder()
                    .id(id)
                    .name(name)
                    .type(type)
                    .capacity(capacity)
                    .build();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve enclosure from the database: " + e.getMessage(), e);
        }
        return enclosure;
    }

    // Update an existing enclosure in the database
    public void updateEnclosure(Enclosure enclosure) {
        EnclosureValidator validator = new EnclosureValidator()
            .name(enclosure.getName())
            .type(enclosure.getType())
            .capacity(enclosure.getCapacity());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Enclosure data is invalid: " + validationErrors);
        }

        String sql = "UPDATE Enclosure SET name = ?, type = ?, capacity = ? WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enclosure.getName());
            pstmt.setString(2, enclosure.getType());
            pstmt.setInt(3, enclosure.getCapacity());
            pstmt.setString(4, enclosure.getId().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update enclosure in the database: " + e.getMessage(), e);
        }
    }

    // Delete an enclosure from the database by its UUID
    public void deleteEnclosure(UUID id) throws DatabaseConnectionException {
        String sql = "DELETE FROM Enclosure WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to delete enclosure from the database: " + e.getMessage(), e);
        }
    }
}