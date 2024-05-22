package main.java.com.krnelx.dataprocessingwithdao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import main.java.com.krnelx.dataprocessingwithdao.model.Animal;
import main.java.com.krnelx.dataprocessingwithdao.dao.exceptions.DatabaseConnectionException;
import main.java.com.krnelx.dataprocessingwithdao.dao.exceptions.RecordNotFoundException;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.AnimalValidator;

public class AnimalDAO {

    // Private static instance of AnimalDAO
    private static final AnimalDAO instance = new AnimalDAO();

    // Private constructor to prevent instantiation from outside
    private AnimalDAO() {
    }

    // Static method to get the instance of AnimalDAO
    public static AnimalDAO getInstance() {
        return instance;
    }

    // Establish connection with the SQLite database
    private Connection connect() {
        String url = "jdbc:sqlite:src/main/resources/db/zoo_database";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to the database: " + e.getMessage(), e);
        }
        return conn;
    }

    // Add a new animal to the database
    public void addAnimal(Animal animal) {
        AnimalValidator validator = new AnimalValidator()
            .name(animal.getName())
            .species(animal.getSpecies())
            .age(animal.getAge())
            .enclosureId(animal.getEnclosureId());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Animal data is invalid: " + validationErrors);
        }

        String sql = "INSERT INTO Animal(id, name, species, age, enclosure_id) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(animal.getId()));
            pstmt.setString(2, animal.getName());
            pstmt.setString(3, animal.getSpecies());
            pstmt.setInt(4, animal.getAge());
            pstmt.setInt(5, animal.getEnclosureId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to add animal: " + e.getMessage(), e);
        }
    }

    // Retrieve a list of all animals from the database
    public List<Animal> getAllAnimals() {
        String sql = "SELECT * FROM Animal";
        List<Animal> animals = new ArrayList<>();

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            // Iterate through the query results and create Animal objects
            while (rs.next()) {
                Animal animal = new Animal();
                animal.setId(UUID.fromString(rs.getString("id")));
                animal.setName(rs.getString("name"));
                animal.setSpecies(rs.getString("species"));
                animal.setAge(rs.getInt("age"));
                animal.setEnclosureId(rs.getInt("enclosure_id"));
                animals.add(animal);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve animals: " + e.getMessage(), e);
        }
        return animals;
    }

    // Retrieve an animal by its UUID from the database
    public Animal getAnimalById(UUID id) {
        String sql = "SELECT * FROM Animal WHERE id = ?";
        Animal animal = null;

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String species = rs.getString("species");
                int age = rs.getInt("age");
                int enclosureId = rs.getInt("enclosure_id");

                animal = Animal.builder()
                    .id(id)
                    .name(name)
                    .species(species)
                    .age(age)
                    .enclosureId(enclosureId)
                    .build();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve animal with id " + id + ": " + e.getMessage(), e);
        }
        return animal;
    }

    // Update an existing animal in the database
    public void updateAnimal(Animal animal) {
        AnimalValidator validator = new AnimalValidator()
            .name(animal.getName())
            .species(animal.getSpecies())
            .age(animal.getAge())
            .enclosureId(animal.getEnclosureId());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Animal data is invalid: " + validationErrors);
        }

        String sql = "UPDATE Animal SET name = ?, species = ?, age = ?, enclosure_id = ? WHERE id = ?";

        try (Connection conn = this.connect()) {
            if (conn != null) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, animal.getName());
                    pstmt.setString(2, animal.getSpecies());
                    pstmt.setInt(3, animal.getAge());
                    pstmt.setInt(4, animal.getEnclosureId());
                    pstmt.setString(5, animal.getId().toString());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RecordNotFoundException("Failed to update animal with id " + animal.getId());
                }
            } else {
                throw new DatabaseConnectionException("Failed to connect to the database");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to the database: " + e.getMessage(), e);
        }
    }

    // Delete an animal from the database by its UUID
    public void deleteAnimal(UUID id) {
        String sql = "DELETE FROM Animal WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RecordNotFoundException("Failed to delete animal with id " + id);
        }
    }
}