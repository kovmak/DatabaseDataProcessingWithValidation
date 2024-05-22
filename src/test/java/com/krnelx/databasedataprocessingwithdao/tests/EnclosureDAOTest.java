package test.java.com.krnelx.databasedataprocessingwithdao.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;
import main.java.com.krnelx.dataprocessingwithdao.dao.DAOFactory;
import main.java.com.krnelx.dataprocessingwithdao.dao.EnclosureDAO;
import main.java.com.krnelx.dataprocessingwithdao.model.Enclosure;
import org.junit.Before;
import org.junit.Test;

public class EnclosureDAOTest {

    private EnclosureDAO enclosureDAO;

    @Before
    public void setUp() {
        // Using DAOFactory to create EnclosureDAO
        enclosureDAO = DAOFactory.getInstance().createEnclosureDAO();
    }

    @Test
    public void testAddEnclosure() {
        // Create a new test enclosure
        UUID id = UUID.randomUUID();
        String name = "Test Enclosure";
        String type = "Test Type";
        int capacity = 10;
        Enclosure testEnclosure = Enclosure.builder()
            .id(id)
            .name(name)
            .type(type)
            .capacity(capacity)
            .build();

        // Add the test enclosure to the database
        enclosureDAO.addEnclosure(testEnclosure);

        // Retrieve the added enclosure from the database
        Enclosure addedEnclosure = enclosureDAO.getEnclosureById(id);

        // Check if the enclosure was added successfully
        assertNotNull(addedEnclosure);
        assertEquals(name, addedEnclosure.getName());
        assertEquals(type, addedEnclosure.getType());
        assertEquals(capacity, addedEnclosure.getCapacity());
    }

    @Test
    public void testGetAllEnclosures() {
        // Retrieve all enclosures from the database
        List<Enclosure> enclosures = enclosureDAO.getAllEnclosures();

        // Check if the returned list of enclosures is not empty
        assertFalse(enclosures.isEmpty());
    }

    @Test
    public void testGetEnclosureById() {
        // Create a new test enclosure
        UUID id = UUID.randomUUID();
        String name = "Test Enclosure";
        String type = "Test Type";
        int capacity = 10;
        Enclosure testEnclosure = Enclosure.builder()
            .id(id)
            .name(name)
            .type(type)
            .capacity(capacity)
            .build();

        // Add the test enclosure to the database
        enclosureDAO.addEnclosure(testEnclosure);

        // Retrieve the test enclosure by its id
        Enclosure retrievedEnclosure = enclosureDAO.getEnclosureById(id);

        // Check if the retrieved enclosure matches the added enclosure
        assertNotNull(retrievedEnclosure);
        assertEquals(name, retrievedEnclosure.getName());
        assertEquals(type, retrievedEnclosure.getType());
        assertEquals(capacity, retrievedEnclosure.getCapacity());
    }

    @Test
    public void testUpdateEnclosure() {
        // Create a new test enclosure
        UUID id = UUID.randomUUID();
        String name = "Test Enclosure";
        String type = "Test Type";
        int capacity = 10;
        Enclosure testEnclosure = Enclosure.builder()
            .id(id)
            .name(name)
            .type(type)
            .capacity(capacity)
            .build();

        // Add the test enclosure to the database
        enclosureDAO.addEnclosure(testEnclosure);

        // Update the name of the test enclosure
        String updatedName = "Updated Enclosure";
        testEnclosure.setName(updatedName);
        enclosureDAO.updateEnclosure(testEnclosure);

        // Retrieve the updated enclosure from the database
        Enclosure updatedEnclosure = enclosureDAO.getEnclosureById(id);

        // Check if the name of the enclosure has been updated
        assertNotNull(updatedEnclosure);
        assertEquals(updatedName, updatedEnclosure.getName());
    }

    @Test
    public void testDeleteEnclosure() {
        // Create a new test enclosure
        UUID id = UUID.randomUUID();
        String name = "Test Enclosure";
        String type = "Test Type";
        int capacity = 10;
        Enclosure testEnclosure = Enclosure.builder()
            .id(id)
            .name(name)
            .type(type)
            .capacity(capacity)
            .build();

        // Add the test enclosure to the database
        enclosureDAO.addEnclosure(testEnclosure);

        // Delete the test enclosure from the database
        enclosureDAO.deleteEnclosure(id);

        // Retrieve the deleted enclosure from the database
        Enclosure deletedEnclosure = enclosureDAO.getEnclosureById(id);

        // Check if the enclosure has been successfully deleted
        assertNull(deletedEnclosure);
    }
}