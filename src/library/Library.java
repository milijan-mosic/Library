package library;

import java.awt.EventQueue;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import library.models.Book;
import library.models.Database;
import library.models.Transaction;
import library.models.User;
import library.utils.WindowUtils;
import library.windows.BookWindow;
import library.windows.LendingBookWindow;
import library.windows.UserWindow;

public class Library {
    private JFrame frame;
    //
    private static JList<String> bookList;
    private static List<Object[]> books;
    private JButton btnAddBook;
    private JButton btnEditBook;
    private JButton btnDeleteBook;
    private JButton btnLendBook;
    //
    private static JList<String> userList;
    private static List<Object[]> users;
    private JButton btnAddUser;
    private JButton btnEditUser;
    private JButton btnDeleteUser;
    //
    private static JList<String> transactionList;
    private JButton btnReturnBook;
    //
    public static Book bookForUpdating;
    public static User userForUpdating;

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
        frame.setResizable(false);
        
        // ---------------------------------------------------------------- *** ----------------------------------------------------------------
        
        JLabel lblBooks = new JLabel("BOOKS");
        lblBooks.setBounds(12, 12, 60, 17);
        frame.getContentPane().add(lblBooks);

        bookList = new JList<>();
        bookList.setBounds(12, 41, 492, 328);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isSelected = !bookList.isSelectionEmpty();

                btnEditBook.setEnabled(isSelected);
                btnDeleteBook.setEnabled(isSelected);
                btnLendBook.setEnabled(isSelected);
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
        btnEditBook.setBounds(152, 381, 92, 27);
        btnEditBook.setEnabled(false);
        btnEditBook.addActionListener(e -> {
            getSelectedBook();
            if (bookForUpdating != null) {
                WindowUtils.openWindowWithButtonControl(btnEditBook, new BookWindow(bookForUpdating));
            }
        });
        frame.getContentPane().add(btnEditBook);
    
        btnDeleteBook = new JButton("Delete book");
        btnDeleteBook.setBounds(256, 381, 118, 27);
        btnDeleteBook.setEnabled(false);
        btnDeleteBook.addActionListener(e -> {
            int selectedIndex = bookList.getSelectedIndex() + 1;

            Library.showDeleteDialog(selectedIndex, "book");
        });
        frame.getContentPane().add(btnDeleteBook);

        btnLendBook = new JButton("Lend book");
        btnLendBook.setBounds(386, 381, 118, 27);
        btnLendBook.setEnabled(false);
        btnLendBook.addActionListener(e -> {
            int selectedBookIndex = bookList.getSelectedIndex();

            if (selectedBookIndex != -1) {
                String selectedBookInfo = bookList.getModel().getElementAt(selectedBookIndex);
                String selectedBookName = selectedBookInfo.split(" - ")[0];
                List<String> userNames = users.stream().map(user -> (String) user[1]).toList();

                WindowUtils.openWindowWithButtonControl(btnLendBook, new LendingBookWindow(selectedBookName, userNames));
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to lend.", "No Book Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        frame.getContentPane().add(btnLendBook);
    
        // ---------------------------------------------------------------- *** ----------------------------------------------------------------

        JLabel lblUsers = new JLabel("USERS");
        lblUsers.setBounds(516, 12, 60, 17);
        frame.getContentPane().add(lblUsers);

        userList = new JList<>();
        userList.setBounds(516, 41, 492, 328);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isSelected = !userList.isSelectionEmpty();
                btnEditUser.setEnabled(isSelected);
                btnDeleteUser.setEnabled(isSelected);
            }
        });
        frame.getContentPane().add(userList);
    
        btnAddUser = new JButton("Add user");
        btnAddUser.setBounds(644, 381, 128, 27);
        btnAddUser.addActionListener(e -> {
            WindowUtils.openWindowWithButtonControl(btnAddUser, new UserWindow());
        });
        frame.getContentPane().add(btnAddUser);
    
        btnEditUser = new JButton("Edit user");
        btnEditUser.setBounds(784, 381, 106, 27);
        btnEditUser.setEnabled(false);
        btnEditUser.addActionListener(e -> {
            getSelectedUser();
            if (userForUpdating != null) {
                WindowUtils.openWindowWithButtonControl(btnEditUser, new UserWindow(userForUpdating));
            }
        });
        frame.getContentPane().add(btnEditUser);
    
        btnDeleteUser = new JButton("Delete user");
        btnDeleteUser.setBounds(902, 381, 106, 27);
        btnDeleteUser.setEnabled(false);
        btnDeleteUser.addActionListener(e -> {
            int selectedIndex = userList.getSelectedIndex() + 1;

            Library.showDeleteDialog(selectedIndex, "user");
        });
        frame.getContentPane().add(btnDeleteUser);
    
        // ---------------------------------------------------------------- *** ----------------------------------------------------------------

        JLabel lblTransactions = new JLabel("TRANSACTIONS");
        lblTransactions.setBounds(12, 420, 106, 17);
        frame.getContentPane().add(lblTransactions);

        transactionList = new JList<>();
        transactionList.setBounds(12, 449, 996, 239);
        transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean isSelected = !transactionList.isSelectionEmpty();
                btnReturnBook.setEnabled(isSelected);
            }
        });
        frame.getContentPane().add(transactionList);
    
        btnReturnBook = new JButton("Return book");
        btnReturnBook.setBounds(12, 700, 128, 27);
        btnReturnBook.setEnabled(false);
        frame.getContentPane().add(btnReturnBook);
    
        LoadBooksIntoList();
        LoadUsersIntoList();
        LoadTransactionsIntoList();
    }

    public static void LoadBooksIntoList() {
        books = Book.getAllBooks();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Object[] book : books) {
            String bookInfo = String.format("%s - %s | %s", book[1], book[2], book[3]);
            listModel.addElement(bookInfo);
        }

        bookList.setModel(listModel);
    }

    public static void LoadUsersIntoList() {
        users = User.getAllUsers();

        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Object[] user : users) {
            String userInfo = String.format("%s (%s) - %s", user[1], user[2], user[3]);
            listModel.addElement(userInfo);
        }

        userList.setModel(listModel);
    }

    public static void LoadTransactionsIntoList() {
        List<Object[]> transactions = Transaction.getAllTransactions();
    
        DefaultListModel<String> listModel = new DefaultListModel<>();
    
        for (Object[] transaction : transactions) {
            int lentDateStr = (int) transaction[3];
            String finalLentDate = convertUnixTimestampToSerbianFormat(lentDateStr);
    
            int returnDate = (int) transaction[4];
            String finalReturnDate = "";
            if (returnDate != 0) {
                finalReturnDate = convertUnixTimestampToSerbianFormat(returnDate);
            } else {
                finalReturnDate = "Nije vracena...";
            }
    
            String transactionInfo = String.format("%s -> %s | Izdata: %s, Status: %s", transaction[1], transaction[2], finalLentDate, finalReturnDate);
            listModel.addElement(transactionInfo);
        }
    
        transactionList.setModel(listModel);
    }

    public void getSelectedBook() {
        int selectedIndex = bookList.getSelectedIndex() + 1;

        bookForUpdating = Book.getBook(selectedIndex);
    }

    public void getSelectedUser() {
        int selectedIndex = userList.getSelectedIndex() + 1;

        userForUpdating = User.getUser(selectedIndex);
    }

    public static void showDeleteDialog(int itemId, String itemType) {
        int option = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this " + itemType + "?",
                "Delete " + itemType,
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
        
        if (option == JOptionPane.YES_OPTION) {
            if (itemType == "user") {
                User.deleteUser(itemId);
                LoadUsersIntoList();
            } else {
                Book.deleteBook(itemId);
                LoadBooksIntoList();
            }
        }
    }
    
    public static String convertUnixTimestampToSerbianFormat(int timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.of("Europe/Belgrade"));

        return formatter.format(instant);
    }
}
