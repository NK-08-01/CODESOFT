import java.util.*;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public boolean registerStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }
}

class Student {
    String studentID;
    String name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for " + course.title);
        } else {
            System.out.println("Registration failed: Course full or already registered.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course) && course.dropStudent()) {
            registeredCourses.remove(course);
            System.out.println("Successfully dropped " + course.title);
        } else {
            System.out.println("Cannot drop course: Not registered or error occurred.");
        }
    }
}

public class Task5{
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();
        System.out.println("Welcome to the Student Course Registration System");
        studentMenu();
    }

    private static void initializeCourses() {
        courses.add(new Course("CS205", "OOP&M", "Basic programming concepts", 30, "Mon-Wed 10 AM"));
        courses.add(new Course("CS203", "Data Structures", "Introduction to data structures", 25, "Tue-Thu 2 PM"));
    }

    private static void studentMenu() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.next();
        System.out.print("Enter Student Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        Student student = new Student(studentID, name);
        students.add(student);
        
        while (true) {
            System.out.println("\n1. View Courses\n2. Register Course\n3. Drop Course\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCourses();
                    break;
                case 2:
                    registerForCourse(student);
                    break;
                case 3:
                    dropCourse(student);
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void displayCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course c : courses) {
            System.out.println(c.courseCode + " - " + c.title + " (" + c.enrolledStudents + "/" + c.capacity + ")");
            System.out.println("  Schedule: " + c.schedule);
            System.out.println("  Description: " + c.description);
        }
    }

    private static void registerForCourse(Student student) {
        System.out.print("Enter course code to register: ");
        String courseCode = scanner.next();
        Course course = findCourse(courseCode);
        if (course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void dropCourse(Student student) {
        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.next();
        Course course = findCourse(courseCode);
        if (course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static Course findCourse(String courseCode) {
        for (Course c : courses) {
            if (c.courseCode.equalsIgnoreCase(courseCode)) {
                return c;
            }
        }
        return null;
    }
}

