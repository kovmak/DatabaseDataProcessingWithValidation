package main.java.com.krnelx.dataprocessingwithdao.service.validation;

import java.util.ArrayList;
import java.util.List;

public class EmployeeValidator {

    private String name;
    private String position;
    private double salary;
    private List<String> errors;

    public EmployeeValidator() {
        this.errors = new ArrayList<>();
    }

    public EmployeeValidator name(String name) {
        this.name = name;
        return this;
    }

    public EmployeeValidator position(String position) {
        this.position = position;
        return this;
    }

    public EmployeeValidator salary(double salary) {
        this.salary = salary;
        return this;
    }

    public List<String> validate() {
        errors.clear();

        validateName();
        validatePosition();
        validateSalary();

        return errors;
    }

    private void validateName() {
        if (name == null || name.trim().isEmpty()) {
            errors.add("Name cannot be empty");
        }
    }

    private void validatePosition() {
        if (position == null || position.trim().isEmpty()) {
            errors.add("Position cannot be empty");
        }
    }

    private void validateSalary() {
        if (salary <= 0) {
            errors.add("Salary must be a positive number");
        }
    }
}