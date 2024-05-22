package main.java.com.krnelx.dataprocessingwithdao.model;

import java.util.UUID;

public class Enclosure {

    private UUID id;
    private String name;
    private String type;
    private int capacity;

    // Private constructor to enforce the use of the builder
    private Enclosure(EnclosureBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.capacity = builder.capacity;
    }

    // Default constructor
    public Enclosure() {
        this.id = UUID.randomUUID();
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Builder class
    public static EnclosureBuilder builder() {
        return new EnclosureBuilder();
    }

    public static class EnclosureBuilder {

        // Fields to be set by the builder
        private UUID id;
        private String name;
        private String type;
        private int capacity;

        // Setter methods for each field
        public EnclosureBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public EnclosureBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EnclosureBuilder type(String type) {
            this.type = type;
            return this;
        }

        public EnclosureBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        // Build method to create an instance of Enclosure
        public Enclosure build() {
            return new Enclosure(this);
        }
    }
}