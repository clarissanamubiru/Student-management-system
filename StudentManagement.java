class StudentManagement {
    //array to store student objects
    private Student[] students = new Student[100];

    //keep track of the number of students
    private int count = 0;

  
// method to add a student
public int addStudent(Student student) {

    // Check whether the student ID already exists
    if (searchStudent(student.getId()) != null) {
        return -1; // -1 means Duplicate ID found
    }

    // Check whether the array has space
    if (count < students.length) {
        students[count] = student;
        count++;

        System.out.println("Student added successfully");
        return 1; // 1 means Success

    } else {
        System.out.println("Student list is full!");
        return -2; // -2 means Array is full
    }
}


    
    //mthd to search for a student
    public Student searchStudent(String id) {
        for (int i = 0; i < count; i++ ) {
            if (students[i].getId().equals(id)) {
                return students[i];
            }
        }
        return null;
    }

    // to delete students
    public boolean deleteStudent(String id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId().equals(id)) {
                for (int j = i; j < count-1; j++) {
                    students[j] = students[j + 1];
                }
                students[count - 1] = null;
                count--;

            return true;
            }
        }
        return false;
    }

    //display all students
    public void displayStudents() {
        if (count == 0) {
            System.out.println("No students registered.");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.println("ID: " + students[i].getId());
                System.out.println("Name: " + students[i].getName());
                System.out.println("Course: " + students[i].getCourse());
                System.out.println("Age: " + students[i].getAge());
                System.out.println(" ");
            }
        }
    }
}
