package main.java.com.krnelx.dataprocessingwithdao.service.validation;

import java.util.ArrayList;
import java.util.List;

public class VisitorValidator {

    private String name;
    private int age;
    private List<String> errors;

    public VisitorValidator() {
        this.errors = new ArrayList<>();
    }

    public VisitorValidator name(String name) {
        this.name = name;
        return this;
    }

    public VisitorValidator age(int age) {
        this.age = age;
        return this;
    }

    public List<String> validate() {
        errors.clear();

        validateName();
        validateAge();

        return errors;
    }

    private void validateName() {
        if (name == null || name.trim().isEmpty()) {
            errors.add("Name cannot be empty");
        }
    }

    private void validateAge() {
        if (age <= 0) {
            errors.add("Age must be a positive number");
        }
    }
}