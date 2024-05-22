package main.java.com.krnelx.dataprocessingwithdao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import main.java.com.krnelx.dataprocessingwithdao.model.Employee;
import main.java.com.krnelx.dataprocessingwithdao.dao.exceptions.DatabaseConnectionException;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.EmployeeValidator;

public class EmployeeDAO {

    // Private static instance of EmployeeDAO
    private static final EmployeeDAO instance = new EmployeeDAO();

    // Private constructor to prevent instantiation outside of this class
    private EmployeeDAO() {}

    // Method to get the singleton instance of EmployeeDAO
    public static EmployeeDAO getInstance() {
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

    // Add a new employee to the database
    public void addEmployee(Employee employee) {
        EmployeeValidator validator = new EmployeeValidator()
            .name(employee.getName())
            .position(employee.getPosition())
            .salary(employee.getSalary());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Employee data is invalid: " + validationErrors);
        }

        String sql = "INSERT INTO Employee(id, name, position, salary) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(employee.getId()));
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getPosition());
            pstmt.setDouble(4, employee.getSalary());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add employee to the database: " + e.getMessage(), e);
        }
    }

    // Retrieve a list of all employees from the database
    public List<Employee> getAllEmployees() throws DatabaseConnectionException {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            // Iterate through the query results and create Employee objects
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(UUID.fromString(rs.getString("id")));
                employee.setName(rs.getString("name"));
                employee.setPosition(rs.getString("position"));
                employee.setSalary(rs.getDouble("salary"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve all employees from the database: " + e.getMessage(), e);
        }
        return employees;
    }

    // Retrieve an employee by its UUID from the database
    public Employee getEmployeeById(UUID id) throws DatabaseConnectionException {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        Employee employee = null;

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String position = rs.getString("position");
                double salary = rs.getDouble("salary");

                employee = Employee.builder()
                    .id(id)
                    .name(name)
                    .position(position)
                    .salary(salary)
                    .build();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to retrieve employee by id from the database: " + e.getMessage(), e);
        }
        return employee;
    }

    // Update an existing employee in the database
    public void updateEmployee(Employee employee) {
        EmployeeValidator validator = new EmployeeValidator()
            .name(employee.getName())
            .position(employee.getPosition())
            .salary(employee.getSalary());

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Employee data is invalid: " + validationErrors);
        }

        String sql = "UPDATE Employee SET name = ?, position = ?, salary = ? WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getPosition());
            pstmt.setDouble(3, employee.getSalary());
            pstmt.setString(4, employee.getId().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employee in the database: " + e.getMessage(), e);
        }
    }

    // Delete an employee from the database by its UUID
    public void deleteEmployee(UUID id) throws DatabaseConnectionException {
        String sql = "DELETE FROM Employee WHERE id = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to delete employee from the database: " + e.getMessage(), e);
        }
    }
}