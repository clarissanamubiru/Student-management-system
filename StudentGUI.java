import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentGUI extends JFrame {

    // StudentManeger object
    StudentManagement manager = new StudentManagement();

    // GUI components
    JTextField idField, nameField, courseField, ageField;
    JTable studentTable;
    DefaultTableModel tableModel;

    //constructor
    public StudentGUI() {
        setTitle("Student Management System");
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    inputPanel.add(new JLabel("Student ID:"));
    idField = new JTextField();
    inputPanel.add(idField);

    inputPanel.add(new JLabel("Student Name:"));
    nameField = new JTextField();
    inputPanel.add(nameField);

    inputPanel.add(new JLabel("Course:"));
    courseField = new JTextField();
    inputPanel.add(courseField);

    inputPanel.add(new JLabel("Age:"));
    ageField = new JTextField();
    inputPanel.add(ageField);

    //create buttons
    JButton addButton = new JButton("Add Student");
    JButton searchButton = new JButton("Search Student");
    JButton deleteButton = new JButton("Delete Student");
    JButton clearButton = new JButton("Clear Fields");

    //create table
    String[] columns = {"ID", "Name", "Course", "Age"};

    tableModel = new DefaultTableModel(columns, 0);
    studentTable = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(studentTable);

    //Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(clearButton);

    add(inputPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

         // add student button action
    addButton.addActionListener(e -> {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String course = courseField.getText().trim();
            
            // 1. Basic check to ensure fields aren't completely blank
            if(id.isEmpty() || name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!");
                return;
            }
            
            int age = Integer.parseInt(ageField.getText().trim());

            // 2. Age Validation Check (Refuses zero or negative ages)
            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "Error: Age must be a positive number greater than 0!");
                return; // Stops the method here so the student isn't added
            }

            Student student = new Student(id, name, course, age);
            
            // Capture the integer result code from our backend method
            int result = manager.addStudent(student);

            // Handle each specific error code distinctly
            if (result == 1) {
                tableModel.addRow(new Object[]{id, name, course, age});
                clearFields();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } else if (result == -1) {
                JOptionPane.showMessageDialog(this, "Error: Student ID '" + id + "' already exists!");
            } else if (result == -2) {
                JOptionPane.showMessageDialog(this, "Error: The student list is full (Max 100 students)!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number!");
        }
    });


        //search student button action
        searchButton.addActionListener(e -> {
        Student student = manager.searchStudent(idField.getText());
        if (student != null) {
            JOptionPane.showMessageDialog(this,
                "Student Found!\n" +
                "Name: " + student.getName() +
                "\nCourse: " + student.getCourse() +
                "\nAge: " + student.getAge());
            } else {
                JOptionPane.showMessageDialog(this,
                    "Student not found!");
                }
    });

    // delete student button action
deleteButton.addActionListener(e -> {
    // 1. Get the index of the highlighted row
    int selectedRow = studentTable.getSelectedRow(); 
    
    // 2. Check if the user actually highlighted a row
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row from the table first!");
        return;
    }
    
    // 3. Get the ID directly from column 0 of that highlighted row
    String id = tableModel.getValueAt(selectedRow, 0).toString();
    
    // 4. Remove from backend and update the table view
    if (manager.deleteStudent(id)) {
        tableModel.removeRow(selectedRow);
        clearFields();
        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
    } else {
        JOptionPane.showMessageDialog(this, "Student not found!");
    }
});


    // Clear fields button action
clearButton.addActionListener(e -> {
    clearFields();
});

    setLocationRelativeTo(null);
    setVisible(true);
}

//mthd to clear input fields
private void clearFields() {
    idField.setText("");
    nameField.setText("");
    courseField.setText("");
    ageField.setText("");
}

//main method
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new StudentGUI());
}
    }
