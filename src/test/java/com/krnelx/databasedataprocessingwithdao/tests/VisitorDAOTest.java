package test.java.com.krnelx.databasedataprocessingwithdao.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;
import main.java.com.krnelx.dataprocessingwithdao.dao.DAOFactory;
import main.java.com.krnelx.dataprocessingwithdao.dao.VisitorDAO;
import main.java.com.krnelx.dataprocessingwithdao.model.Visitor;
import org.junit.Before;
import org.junit.Test;

public class VisitorDAOTest {

    private VisitorDAO visitorDAO;

    @Before
    public void setUp() {
        // Using DAOFactory to create VisitorDAO
        visitorDAO = DAOFactory.getInstance().createVisitorDAO();
    }

    @Test
    public void testAddVisitor() {
        // Create a new test visitor
        UUID id = UUID.randomUUID();
        String name = "Test Visitor";
        int age = 25;
        Visitor testVisitor = Visitor.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();

        // Add the test visitor to the database
        visitorDAO.addVisitor(testVisitor);

        // Retrieve the added visitor from the database
        Visitor addedVisitor = visitorDAO.getVisitorById(testVisitor.getId());

        // Check if the visitor was added successfully
        assertEquals(name, addedVisitor.getName());
        assertEquals(age, addedVisitor.getAge());
    }

    @Test
    public void testGetAllVisitors() {
        // Retrieve all visitors from the database
        List<Visitor> visitors = visitorDAO.getAllVisitors();

        // Check if the returned list of visitors is not empty
        assertFalse(visitors.isEmpty());
    }

    @Test
    public void testGetVisitorById() {
        // Create a new test visitor
        UUID id = UUID.randomUUID();
        String name = "Test Visitor";
        int age = 25;
        Visitor testVisitor = Visitor.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();

        // Add the visitor to the database
        visitorDAO.addVisitor(testVisitor);

        // Retrieve the visitor by its id from the database
        Visitor retrievedVisitor = visitorDAO.getVisitorById(id);

        // Check if the retrieved visitor has the same id as the test visitor
        assertEquals(testVisitor.getId(), retrievedVisitor.getId());
    }

    @Test
    public void testUpdateVisitor() {
        // Create a new test visitor
        UUID id = UUID.randomUUID();
        String name = "Test Visitor";
        int age = 25;
        Visitor testVisitor = Visitor.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();

        // Add the visitor to the database
        visitorDAO.addVisitor(testVisitor);

        // Update the information of the visitor
        String updatedName = "Updated Visitor";
        testVisitor.setName(updatedName);
        visitorDAO.updateVisitor(testVisitor);

        // Retrieve the visitor by its id from the database
        Visitor retrievedVisitor = visitorDAO.getVisitorById(id);

        // Check if the name of the visitor has been updated
        assertEquals(updatedName, retrievedVisitor.getName());
    }

    @Test
    public void testDeleteVisitor() {
        // Create a new test visitor
        UUID id = UUID.randomUUID();
        String name = "Test Visitor";
        int age = 25;
        Visitor testVisitor = Visitor.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();

        // Add the visitor to the database
        visitorDAO.addVisitor(testVisitor);

        // Delete the visitor from the database
        visitorDAO.deleteVisitor(id);

        // Retrieve the visitor by its id from the database
        Visitor deletedVisitor = visitorDAO.getVisitorById(id);

        // Check if the visitor has been successfully deleted
        assertNull(deletedVisitor);
    }
}