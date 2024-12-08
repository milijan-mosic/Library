package library;

import java.awt.EventQueue;
import javax.swing.JFrame;
import library.models.Database;

public class Library {
	private JFrame frame;

	/*
	 * Launch the application.
	 */
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

	/*
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
