package test.java.com.krnelx.databasedataprocessingwithdao.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;
import main.java.com.krnelx.dataprocessingwithdao.dao.DAOFactory;
import main.java.com.krnelx.dataprocessingwithdao.dao.EmployeeDAO;
import main.java.com.krnelx.dataprocessingwithdao.model.Employee;
import org.junit.Before;
import org.junit.Test;

public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO;

    @Before
    public void setUp() {
        // Using DAOFactory to create EmployeeDAO
        employeeDAO = DAOFactory.getInstance().createEmployeeDAO();
    }

    @Test
    public void testAddEmployee() {
        // Test adding an employee to the database
        UUID id = UUID.randomUUID(); // Generate a random UUID for the employee
        Employee employee = Employee.builder()
            .id(id)
            .name("John Doe")
            .position("Manager")
            .salary(50000.0)
            .build();

        employeeDAO.addEmployee(employee);

        Employee retrievedEmployee = employeeDAO.getEmployeeById(id);
        assertNotNull(retrievedEmployee);
        assertEquals("John Doe", retrievedEmployee.getName());
        assertEquals("Manager", retrievedEmployee.getPosition());
        assertEquals(50000.0, retrievedEmployee.getSalary(), 0.01);
    }

    @Test
    public void testGetAllEmployees() {
        // Test retrieving all employees from the database
        List<Employee> employees = employeeDAO.getAllEmployees();
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testGetEmployeeById() {
        // Create a test employee
        UUID id = UUID.randomUUID();
        String name = "Test Employee";
        String position = "Test Position";
        double salary = 1000.0;
        Employee testEmployee = Employee.builder()
            .id(id)
            .name(name)
            .position(position)
            .salary(salary)
            .build();

        // Add the employee to the database
        employeeDAO.addEmployee(testEmployee);

        // Retrieve the employee by its id from the database
        Employee retrievedEmployee = employeeDAO.getEmployeeById(id);

        // Check if the retrieved employee has the same id as the test employee
        assertEquals(testEmployee.getId(), retrievedEmployee.getId());
    }

    @Test
    public void testUpdateEmployee() {
        // Create a new test employee
        UUID id = UUID.randomUUID();
        String initialName = "Initial Name";
        String position = "Position";
        double salary = 50000.0;
        Employee testEmployee = Employee.builder()
            .id(id)
            .name(initialName)
            .position(position)
            .salary(salary)
            .build();

        // Add the test employee to the database
        employeeDAO.addEmployee(testEmployee);

        // Retrieve the test employee from the database
        Employee retrievedEmployee = employeeDAO.getEmployeeById(id);

        // Update the name of the test employee
        String updatedName = "Updated Name";
        retrievedEmployee.setName(updatedName);
        employeeDAO.updateEmployee(retrievedEmployee);

        // Retrieve the updated employee from the database
        Employee updatedEmployee = employeeDAO.getEmployeeById(id);

        // Check if the name of the employee has been updated
        assertEquals(updatedName, updatedEmployee.getName());
    }

    @Test
    public void testDeleteEmployee() {
        // Create a test employee
        UUID id = UUID.randomUUID();
        String name = "Test Employee";
        String position = "Test Position";
        double salary = 1000.0; // Just an example value
        Employee testEmployee = Employee.builder()
            .id(id)
            .name(name)
            .position(position)
            .salary(salary)
            .build();

        // Add the employee to the database
        employeeDAO.addEmployee(testEmployee);

        // Retrieve the UUID of the added employee
        UUID employeeId = testEmployee.getId();

        // Delete the employee from the database
        employeeDAO.deleteEmployee(employeeId);

        // Attempt to retrieve the deleted employee
        Employee deletedEmployee = employeeDAO.getEmployeeById(employeeId);

        // Check if the employee has been successfully deleted
        assertNull(deletedEmployee);
    }
}