package main.java.com.krnelx.dataprocessingwithdao.dao;

public class DAOFactory {

    // Private static instance of DAOFactory
    private static final DAOFactory instance = new DAOFactory();

    // Private constructor to prevent instantiation from outside
    private DAOFactory() {
    }

    // Static method to get the instance of DAOFactory
    public static DAOFactory getInstance() {
        return instance;
    }

    // Factory method to create AnimalDAO object
    public AnimalDAO createAnimalDAO() {
        return AnimalDAO.getInstance();
    }

    // Factory method to create EmployeeDAO object
    public EmployeeDAO createEmployeeDAO() {
        return EmployeeDAO.getInstance();
    }

    // Factory method to create EnclosureDAO object
    public EnclosureDAO createEnclosureDAO() {
        return EnclosureDAO.getInstance();
    }

    // Factory method to create VisitorDAO object
    public VisitorDAO createVisitorDAO() {
        return VisitorDAO.getInstance();
    }
}
