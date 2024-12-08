package library;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;

import library.models.Database;
import library.utils.WindowUtils;
import library.windows.BookWindow;
import library.windows.UserWindow;

public class Library {
	private JFrame frame;
	private JTable bookTable;
	private JTable userTable;
	private JTable transactionTable;
	private JButton btnAddBook;
	private JButton btnAddUser;
	private JButton btnReturnBook;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String[] tableNames = {"books", "users", "transactions"};
					boolean tableExists = Database.checkTablesExistence(tableNames);
					if (!tableExists) {
			            Database.initializeDatabase();
					}
		            if (!Database.doesDataExist()) {
		                Database.insertDummyData();
		            }
		            
					Library window = new Library();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the application.
	 */
	public Library() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 1033);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//
		
		bookTable = new JTable();
		bookTable.setBounds(12, 12, 492, 357);
		frame.getContentPane().add(bookTable);

		btnAddBook = new JButton("Add book");
		btnAddBook.setBounds(516, 381, 128, 27);
		btnAddBook.addActionListener(e -> {
		    WindowUtils.openWindowWithButtonControl(btnAddBook, new BookWindow());
		});
		frame.getContentPane().add(btnAddBook);
		
		//
		
		userTable = new JTable();
		userTable.setBounds(516, 12, 492, 357);
		frame.getContentPane().add(userTable);

		btnAddUser = new JButton("Add user");
		btnAddUser.setBounds(12, 381, 128, 27);
		btnAddUser.addActionListener(e -> {
		    WindowUtils.openWindowWithButtonControl(btnAddUser, new UserWindow());
		});
		frame.getContentPane().add(btnAddUser);
		
		//
		
		transactionTable = new JTable();
		transactionTable.setBounds(12, 420, 996, 268);
		frame.getContentPane().add(transactionTable);
		
		btnReturnBook = new JButton("Return book");
		btnReturnBook.setBounds(12, 700, 128, 27);
		frame.getContentPane().add(btnReturnBook);
	}
}
