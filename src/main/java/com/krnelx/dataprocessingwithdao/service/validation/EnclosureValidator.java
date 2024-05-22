package main.java.com.krnelx.dataprocessingwithdao.service.validation;

import java.util.ArrayList;
import java.util.List;

public class EnclosureValidator {

    private String name;
    private String type;
    private int capacity;
    private List<String> errors;

    public EnclosureValidator() {
        this.errors = new ArrayList<>();
    }

    public EnclosureValidator name(String name) {
        this.name = name;
        return this;
    }

    public EnclosureValidator type(String type) {
        this.type = type;
        return this;
    }

    public EnclosureValidator capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public List<String> validate() {
        errors.clear();

        validateName();
        validateType();
        validateCapacity();

        return errors;
    }

    private void validateName() {
        if (name == null || name.trim().isEmpty()) {
            errors.add("Name cannot be empty");
        }
    }

    private void validateType() {
        if (type == null || type.trim().isEmpty()) {
            errors.add("Type cannot be empty");
        }
    }

    private void validateCapacity() {
        if (capacity <= 0) {
            errors.add("Capacity must be a positive number");
        }
    }
}