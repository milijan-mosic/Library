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

public class UserWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField phoneNumberTextField;

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

	public UserWindow() {
		setBounds(100, 100, 450, 300);
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
		
		JButton closeButton = new JButton("Close");
		closeButton.setBounds(12, 232, 106, 27);
		closeButton.addActionListener(e -> dispose());
		contentPane.add(closeButton);
		
		JButton confirmButton = new JButton("Insert");
		confirmButton.setBounds(330, 232, 106, 27);
		confirmButton.addActionListener(e -> insertUserIntoDatabase());
		contentPane.add(confirmButton);
	}
	
	private void insertUserIntoDatabase() {
	    String name = nameTextField.getText().trim();
	    String email = emailTextField.getText().trim();
	    String phoneNumber = phoneNumberTextField.getText().trim();

	     if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
	        System.out.println("Please fill in all fields.");
	    } else {
		    User.insertUser(name, email, phoneNumber);
	        Library.LoadUsersIntoTable();
		    System.out.println("User inserted successfully.");
	        dispose();
	    }
	}
}
