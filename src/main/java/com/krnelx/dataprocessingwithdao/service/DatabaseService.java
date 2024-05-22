package main.java.com.krnelx.dataprocessingwithdao.service;

import java.util.List;
import java.util.UUID;

import main.java.com.krnelx.dataprocessingwithdao.dao.exceptions.ValidationException;
import main.java.com.krnelx.dataprocessingwithdao.model.Animal;
import main.java.com.krnelx.dataprocessingwithdao.model.Employee;
import main.java.com.krnelx.dataprocessingwithdao.model.Enclosure;
import main.java.com.krnelx.dataprocessingwithdao.model.Visitor;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.AnimalValidator;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.EmployeeValidator;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.EnclosureValidator;
import main.java.com.krnelx.dataprocessingwithdao.service.validation.VisitorValidator;
import main.java.com.krnelx.dataprocessingwithdao.dao.AnimalDAO;
import main.java.com.krnelx.dataprocessingwithdao.dao.DAOFactory;
import main.java.com.krnelx.dataprocessingwithdao.dao.EmployeeDAO;
import main.java.com.krnelx.dataprocessingwithdao.dao.EnclosureDAO;
import main.java.com.krnelx.dataprocessingwithdao.dao.VisitorDAO;

public class DatabaseService {

    private final AnimalDAO animalDAO;
    private final EmployeeDAO employeeDAO;
    private final EnclosureDAO enclosureDAO;
    private final VisitorDAO visitorDAO;

    public DatabaseService() {
        DAOFactory factory = DAOFactory.getInstance();
        this.animalDAO = factory.createAnimalDAO();
        this.employeeDAO = factory.createEmployeeDAO();
        this.enclosureDAO = factory.createEnclosureDAO();
        this.visitorDAO = factory.createVisitorDAO();
    }

    // Animal CRUD operations
    public void addAnimal(UUID id, String name, String species, int age, int enclosureId) {
        AnimalValidator validator = new AnimalValidator()
            .name(name)
            .species(species)
            .age(age)
            .enclosureId(enclosureId);

        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Animal data is invalid: " + validationErrors);
        }

        Animal animal = Animal.builder()
            .id(id)
            .name(name)
            .species(species)
            .age(age)
            .enclosureId(enclosureId)
            .build();
        animalDAO.addAnimal(animal);
    }

    public List<Animal> getAllAnimals() {
        return animalDAO.getAllAnimals();
    }

    public Animal getAnimalById(UUID id) {
        return animalDAO.getAnimalById(id);
    }

    public void updateAnimal(UUID id, String name, String species, int age, int enclosureId) {
        AnimalValidator validator = new AnimalValidator()
            .name(name)
            .species(species)
            .age(age)
            .enclosureId(enclosureId);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Animal data is invalid: " + validationErrors);
        }

        Animal animal = Animal.builder()
            .id(id)
            .name(name)
            .species(species)
            .age(age)
            .enclosureId(enclosureId)
            .build();
        animalDAO.updateAnimal(animal);
    }

    public void deleteAnimal(UUID id) {
        animalDAO.deleteAnimal(id);
    }

    // Employee CRUD operations
    public void addEmployee(UUID id, String name, String position, double salary) {
        EmployeeValidator validator = new EmployeeValidator()
            .name(name)
            .position(position)
            .salary(salary);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Employee data is invalid: " + validationErrors);
        }

        Employee employee = Employee.builder()
            .id(id)
            .name(name)
            .position(position)
            .salary(salary)
            .build();

        employeeDAO.addEmployee(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    public Employee getEmployeeById(UUID id) {
        return employeeDAO.getEmployeeById(id);
    }

    public void updateEmployee(UUID id, String name, String position, double salary) {
        EmployeeValidator validator = new EmployeeValidator()
            .name(name)
            .position(position)
            .salary(salary);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Employee data is invalid: " + validationErrors);
        }

        Employee employee = Employee.builder()
            .id(id)
            .name(name)
            .position(position)
            .salary(salary)
            .build();

        employeeDAO.updateEmployee(employee);
    }

    public void deleteEmployee(UUID id) {
        employeeDAO.deleteEmployee(id);
    }

    // Enclosure CRUD operations
    public void addEnclosure(UUID id, String name, String type, int capacity) {
        EnclosureValidator validator = new EnclosureValidator()
            .name(name)
            .type(type)
            .capacity(capacity);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Enclosure data is invalid: " + validationErrors);
        }

        Enclosure enclosure = Enclosure.builder()
            .id(id)
            .name(name)
            .type(type)
            .capacity(capacity)
            .build();

        enclosureDAO.addEnclosure(enclosure);
    }

    public List<Enclosure> getAllEnclosures() {
        return enclosureDAO.getAllEnclosures();
    }

    public Enclosure getEnclosureById(UUID id) {
        return enclosureDAO.getEnclosureById(id);
    }

    public void updateEnclosure(UUID id, String name, String type, int capacity) {
        EnclosureValidator validator = new EnclosureValidator()
            .name(name)
            .type(type)
            .capacity(capacity);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Enclosure data is invalid: " + validationErrors);
        }

        Enclosure enclosure = Enclosure.builder()
            .id(id)
            .name(name)
            .type(type)
            .capacity(capacity)
            .build();

        enclosureDAO.updateEnclosure(enclosure);
    }

    public void deleteEnclosure(UUID id) {
        enclosureDAO.deleteEnclosure(id);
    }

    // Visitor CRUD operations
    public void addVisitor(UUID id, String name, int age) {
        VisitorValidator validator = new VisitorValidator()
            .name(name)
            .age(age);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Visitor data is invalid: " + validationErrors);
        }

        Visitor visitor = Visitor.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();

        visitorDAO.addVisitor(visitor);
    }

    public List<Visitor> getAllVisitors() {
        return visitorDAO.getAllVisitors();
    }

    public Visitor getVisitorById(UUID id) {
        return visitorDAO.getVisitorById(id);
    }

    public void updateVisitor(UUID id, String name, int age) {
        VisitorValidator validator = new VisitorValidator()
            .name(name)
            .age(age);
        List<String> validationErrors = validator.validate();
        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Visitor data is invalid: " + validationErrors);
        }

        Visitor visitor = Visitor.builder()
            .id(id)
            .name(name)
            .age(age)
            .build();

        visitorDAO.updateVisitor(visitor);
    }

    public void deleteVisitor(UUID id) {
        visitorDAO.deleteVisitor(id);
    }
}