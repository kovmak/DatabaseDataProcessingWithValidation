package main.java.com.krnelx.dataprocessingwithdao.model;

import java.util.Objects;
import java.util.UUID;

public class Animal {

    private UUID id;
    private String name;
    private String species;
    private int age;
    private int enclosureId;

    // Private constructor to enforce the use of the builder
    private Animal(AnimalBuilder animalBuilder) {
        this.id = animalBuilder.id;
        this.name = animalBuilder.name;
        this.species = animalBuilder.species;
        this.age = animalBuilder.age;
        this.enclosureId = animalBuilder.enclosureId;
    }

    // Default constructor
    public Animal() {
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age &&
            enclosureId == animal.enclosureId &&
            Objects.equals(id, animal.id) &&
            Objects.equals(name, animal.name) &&
            Objects.equals(species, animal.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, species, age, enclosureId);
    }

    // Builder class
    public static AnimalBuilder builder() {
        return new AnimalBuilder();
    }

    public static class AnimalBuilder {
        private UUID id;
        private String name;
        private String species;
        private int age;
        private int enclosureId;

        public AnimalBuilder() {
            this.id = UUID.randomUUID();
        }

        public AnimalBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AnimalBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AnimalBuilder species(String species) {
            this.species = species;
            return this;
        }

        public AnimalBuilder age(int age) {
            this.age = age;
            return this;
        }

        public AnimalBuilder enclosureId(int enclosureId) {
            this.enclosureId = enclosureId;
            return this;
        }

        public Animal build() {
            return new Animal(this);
        }
    }
}