package library.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:test.db";

    public static Connection getConnection() {
        Connection connection = null;
        
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return connection;
    }
    
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Failed to close the connection");
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        String createBooksTable = """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                category TEXT NOT NULL,
                owner_id TEXT,
                release_date INTEGER
            );
        """;

        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL,
                phone_number TEXT,
                note TEXT
            );
        """;

        String createTransactionsTable = """
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                book_id TEXT NOT NULL,
                owner_id TEXT NOT NULL,
                lent_date INTEGER,
                return_date INTEGER,
                FOREIGN KEY (book_id) REFERENCES books(id),
                FOREIGN KEY (owner_id) REFERENCES users(id)
            );
        """;

        Connection conn = Database.getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(createBooksTable);
            stmt.execute(createUsersTable);
            stmt.execute(createTransactionsTable);
            closeConnection(conn);
        } catch (Exception e) {
            closeConnection(conn);
            e.printStackTrace();
        }
    }
    
    public static void insertDummyData() {
        String insertUsers = """
            INSERT INTO users (id, name, email, phone_number, note) VALUES
            ('1', 'Admin User', 'admin@example.com', '1234567890', ''),
            ('2', 'Guest User', 'guest@example.com', '0987654321', '');
        """;
        
        String insertBooks = """
            INSERT INTO books (id, title, author, category, owner_id, release_date) VALUES
            ('1', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', '1', 1925),
            ('2', '1984', 'George Orwell', 'Dystopian', '1', 1949),
            ('3', 'To Kill a Mockingbird', 'Harper Lee', 'Fiction', '1', 1960);
        """;

        Connection conn = Database.getConnection();
        try {
            PreparedStatement stmtBooks = conn.prepareStatement(insertBooks);
            PreparedStatement stmtUsers = conn.prepareStatement(insertUsers);
                    
            stmtBooks.executeUpdate();
            stmtUsers.executeUpdate();

            closeConnection(conn);
            System.out.println("Dummy data inserted successfully");
        } catch (SQLException e) {
            closeConnection(conn);
            e.printStackTrace();
        }
    }
    
    public static boolean checkIfTableExists(String tableName) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?;";
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();

            closeConnection(conn);
            return rs.next(); 
        } catch (SQLException e) {
            closeConnection(conn);
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean checkTablesExistence(String[] tableNames) {
        for (String tableName : tableNames) {
            if (checkIfTableExists(tableName)) {
                System.out.println("Table " + tableName + " already exists");
                return true;
            } else {
                System.out.println("Table " + tableName + " does not exist");
            }
        }
        
        return false;
    }
    
    public static boolean doesDataExist() {
        String checkBooksQuery = "SELECT COUNT(*) FROM books";
        String checkUsersQuery = "SELECT COUNT(*) FROM users";
        Connection conn = getConnection();
        
        try {
            PreparedStatement stmtBooks = conn.prepareStatement(checkBooksQuery);
            PreparedStatement stmtUsers = conn.prepareStatement(checkUsersQuery);
            
            ResultSet rsBooks = stmtBooks.executeQuery();
            int booksCount = rsBooks.getInt(1);
            
            ResultSet rsUsers = stmtUsers.executeQuery();
            int usersCount = rsUsers.getInt(1);

            closeConnection(conn);
            return booksCount > 0 && usersCount > 0;
        } catch (SQLException e) {
            closeConnection(conn);
            e.printStackTrace();
            return false;
        }
    }
}
