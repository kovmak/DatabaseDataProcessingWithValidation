package main.java.com.krnelx.dataprocessingwithdao.model;

import java.util.UUID;

public class Visitor {

    private UUID id;
    private String name;
    private int age;

    // Private constructor to enforce the use of the builder
    private Visitor(VisitorBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
    }

    // Default constructor
    public Visitor() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Builder class
    public static VisitorBuilder builder() {
        return new VisitorBuilder();
    }

    public static class VisitorBuilder {

        private UUID id;
        private String name;
        private int age;

        public VisitorBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public VisitorBuilder name(String name) {
            this.name = name;
            return this;
        }

        public VisitorBuilder age(int age) {
            this.age = age;
            return this;
        }

        public Visitor build() {
            return new Visitor(this);
        }
    }
}