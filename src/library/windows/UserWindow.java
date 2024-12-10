package library.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import library.Library;
import library.models.User;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class UserWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField phoneNumberTextField;
    private JTextArea noteTextArea;
    private JButton closeButton;
    private JButton confirmButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserWindow frame = new UserWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserWindow(User user) {
        this();

        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        phoneNumberTextField.setText(user.getPhoneNumber());
        noteTextArea.setText(user.getNote());
    }

    public UserWindow() {
        setBounds(100, 100, 450, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(12, 12, 64, 16);
        contentPane.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(12, 40, 164, 21);
        contentPane.add(nameTextField);
        nameTextField.setColumns(10);
        
        //
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(12, 73, 64, 16);
        contentPane.add(emailLabel);
        
        emailTextField = new JTextField();
        emailTextField.setColumns(10);
        emailTextField.setBounds(12, 101, 256, 21);
        contentPane.add(emailTextField);
        
        //
        
        JLabel phoneNumberLabel = new JLabel("Phone Number");
        phoneNumberLabel.setBounds(12, 134, 106, 16);
        contentPane.add(phoneNumberLabel);
        
        phoneNumberTextField = new JTextField();
        phoneNumberTextField.setColumns(10);
        phoneNumberTextField.setBounds(12, 162, 164, 21);
        contentPane.add(phoneNumberTextField);
        
        //
        
        JLabel noteLabel = new JLabel("Note");
        noteLabel.setBounds(12, 195, 60, 17);
        contentPane.add(noteLabel);
        
        noteTextArea = new JTextArea();
        noteTextArea.setBounds(12, 224, 256, 128);
        contentPane.add(noteTextArea);
        
        //
        
        closeButton = new JButton("Close");
        closeButton.setBounds(12, 382, 106, 27);
        closeButton.addActionListener(e -> dispose());
        contentPane.add(closeButton);
        
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(330, 382, 106, 27);
        confirmButton.addActionListener(e -> {
            if (Library.userForUpdating == null) {
                insertUserIntoDatabase();
            } else {
                saveUserChanges();
            }
        });
        contentPane.add(confirmButton);
    }
    
    private void insertUserIntoDatabase() {
        String name = nameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String phoneNumber = phoneNumberTextField.getText().trim();
        String note = noteTextArea.getText().trim();

         if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            System.out.println("Please fill in all fields");
        } else {
            User.insertUser(name, email, phoneNumber, note);
            Library.LoadUsersIntoList();
            System.out.println("User inserted successfully");
            dispose();
        }
    }

    private void saveUserChanges() {
        String updatedName = nameTextField.getText();
        String updatedEmail = emailTextField.getText();
        String updatedPhoneNumber = phoneNumberTextField.getText();
        String updatedNote = noteTextArea.getText();

        User originalUser = Library.userForUpdating;
    
        if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedPhoneNumber.isEmpty()) {
            System.out.println("All fields must be filled");
        } else {
            User.updateUser(Integer.parseInt(originalUser.getId()), updatedName, updatedEmail, updatedPhoneNumber, updatedNote);
            Library.userForUpdating = null;
            Library.LoadUsersIntoList();
            System.out.println("User updated successfully");
            dispose();
        }
    }
}
