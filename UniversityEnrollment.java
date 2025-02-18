import java.util.*;

class CourseFullException extends Exception {
    public CourseFullException(String message) {
        super(message);
    }
}

class PrerequisiteNotMetException extends Exception {
    public PrerequisiteNotMetException(String message) {
        super(message);
    }
}

class Course {
    private String name;
    private String prerequisite;
    private int capacity;
    private Set<String> enrolledStudents;
    
    public Course(String name, String prerequisite, int capacity) {
        this.name = name;
        this.prerequisite = prerequisite;
        this.capacity = capacity;
        this.enrolledStudents = new HashSet<>();
    }
    
    public String getName() {
        return name;
    }
    
    public String getPrerequisite() {
        return prerequisite;
    }
    
    public void enrollStudent(String studentName, Set<String> completedCourses) throws CourseFullException, PrerequisiteNotMetException {
        if (enrolledStudents.size() >= capacity) {
            throw new CourseFullException("Course " + name + " is full.");
        }
        if (!prerequisite.isEmpty() && !completedCourses.contains(prerequisite)) {
            throw new PrerequisiteNotMetException("Complete " + prerequisite + " before enrolling in " + name + ".");
        }
        enrolledStudents.add(studentName);
        System.out.println("Successfully enrolled " + studentName + " in " + name + ".");
    }
}

public class UniversityEnrollment {
    public static void main(String[] args) {
        Map<String, Course> courses = new HashMap<>();
        courses.put("Advanced Java", new Course("Advanced Java", "Core Java", 2));
        courses.put("Core Java", new Course("Core Java", "", 3));
        
        Map<String, Set<String>> studentRecords = new HashMap<>();
        studentRecords.put("Alice", new HashSet<>(Arrays.asList("Core Java")));
        studentRecords.put("Bob", new HashSet<>());
        
        enrollStudent("Alice", "Advanced Java", courses, studentRecords);
        enrollStudent("Bob", "Advanced Java", courses, studentRecords);
        enrollStudent("Charlie", "Advanced Java", courses, studentRecords);
    }
    
    private static void enrollStudent(String studentName, String courseName, Map<String, Course> courses, Map<String, Set<String>> studentRecords) {
        Course course = courses.get(courseName);
        if (course == null) {
            System.out.println("Error: Course not found.");
            return;
        }
        
        Set<String> completedCourses = studentRecords.getOrDefault(studentName, new HashSet<>());
        try {
            course.enrollStudent(studentName, completedCourses);
        } catch (CourseFullException | PrerequisiteNotMetException e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
