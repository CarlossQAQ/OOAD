package accommodation.domain.model;

import java.util.Objects;

/**
 * Domain model for Student
 */
public class Student {
    private final String id;
    private final String name;
    private final String contactInfo;
    
    public Student(String id, String name, String contactInfo) {
        this.id = Objects.requireNonNull(id, "Student ID cannot be null");
        this.name = Objects.requireNonNull(name, "Student name cannot be null");
        this.contactInfo = contactInfo;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
} 