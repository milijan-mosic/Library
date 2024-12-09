package library;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import library.models.Book;
import library.models.Database;
import library.models.User;
import library.utils.WindowUtils;
import library.windows.BookWindow;
import library.windows.UserWindow;

public class Library {
    private JFrame frame;
    //
    private static JList<String> bookList;
    private JButton btnAddBook;
    private JButton btnEditBook;
    private JButton btnDeleteBook;
    //
    private static JList<String> userList;
    private JButton btnAddUser;
    private JButton btnEditUser;
    private JButton btnDeleteUser;
    //
    private JTable transactionTable;
    private JButton btnReturnBook;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    test_database();

                    Library window = new Library();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void test_database() {
        String[] tableNames = {"books", "users", "transactions"};
        boolean tableExists = Database.checkTablesExistence(tableNames);
        if (!tableExists) {
            Database.initializeDatabase();
        }
        if (!Database.doesDataExist()) {
            Database.insertDummyData();
        }
    }

    public Library() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Milijan Mosic - MIT 5/24");
        frame.setBounds(100, 100, 1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    
        // BOOKS
        bookList = new JList<>();
        bookList.setBounds(12, 12, 492, 357);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Check if an item is selected
                boolean isSelected = !bookList.isSelectionEmpty();
                btnEditBook.setEnabled(isSelected);
                btnDeleteBook.setEnabled(isSelected);
            }
        });
        frame.getContentPane().add(bookList);
    
        btnAddBook = new JButton("Add book");
        btnAddBook.setBounds(12, 381, 128, 27);
        btnAddBook.addActionListener(e -> {
            WindowUtils.openWindowWithButtonControl(btnAddBook, new BookWindow());
        });
        frame.getContentPane().add(btnAddBook);
    
        btnEditBook = new JButton("Edit book");
        btnEditBook.setBounds(152, 381, 106, 27);
        btnEditBook.setEnabled(false);  // Initially disabled
        frame.getContentPane().add(btnEditBook);
    
        btnDeleteBook = new JButton("Delete book");
        btnDeleteBook.setBounds(270, 381, 118, 27);
        btnDeleteBook.setEnabled(false);  // Initially disabled
        frame.getContentPane().add(btnDeleteBook);
    
        // USERS
        userList = new JList<>();
        userList.setBounds(516, 12, 492, 357);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Check if an item is selected
                boolean isSelected = !userList.isSelectionEmpty();
                btnEditUser.setEnabled(isSelected);
                btnDeleteUser.setEnabled(isSelected);
            }
        });
        frame.getContentPane().add(userList);
    
        btnAddUser = new JButton("Add user");
        btnAddUser.setBounds(516, 381, 128, 27);
        btnAddUser.addActionListener(e -> {
            WindowUtils.openWindowWithButtonControl(btnAddUser, new UserWindow());
        });
        frame.getContentPane().add(btnAddUser);
    
        btnEditUser = new JButton("Edit user");
        btnEditUser.setBounds(656, 381, 106, 27);
        btnEditUser.setEnabled(false);  // Initially disabled
        frame.getContentPane().add(btnEditUser);
    
        btnDeleteUser = new JButton("Delete user");
        btnDeleteUser.setBounds(774, 381, 106, 27);
        btnDeleteUser.setEnabled(false);  // Initially disabled
        frame.getContentPane().add(btnDeleteUser);
    
        // TRANSACTIONS
        transactionTable = new JTable();
        transactionTable.setEnabled(false);
        transactionTable.setBounds(12, 420, 996, 268);
        frame.getContentPane().add(transactionTable);
    
        btnReturnBook = new JButton("Return book");
        btnReturnBook.setBounds(12, 700, 128, 27);
        frame.getContentPane().add(btnReturnBook);
    
        // Load data into lists
        LoadBooksIntoList();
        LoadUsersIntoList();
    }

    public static void LoadBooksIntoList() {
        List<Object[]> books = Book.getAllBooks();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Object[] book : books) {
            String bookInfo = String.format("%s - %s | %s", book[1], book[2], book[3]);
            listModel.addElement(bookInfo);
        }

        bookList.setModel(listModel);
    }

    public static void LoadUsersIntoList() {
        List<Object[]> users = User.getAllUsers();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Object[] user : users) {
            String userInfo = String.format("%s (%s) - %s", user[1], user[2], user[3]);
            listModel.addElement(userInfo);
        }

        userList.setModel(listModel);
    }
}
