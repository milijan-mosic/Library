package library.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;

import library.utils.FileUtils;


public class Transaction {
    private int id;
    private int bookId;
    private int ownerId;
    private int lentDate;
    private int returnDate;

    public Transaction(int id, int bookId, int ownerId, int lentDate, int returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.ownerId = ownerId;
        this.lentDate = lentDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getLentDate() {
        return lentDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setLentDate(int lentDate) {
        this.lentDate = lentDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", lentDate=" + lentDate +
                ", returnDate=" + returnDate +
                '}';
    }

    public static int getBookIdByName(String bookName) {
        String query = "SELECT id FROM books WHERE title = ?";
        int bookId = 0;

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bookId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
        
        return bookId;
    }
    
    public static int getUserIdByName(String userName) {
        String query = "SELECT id FROM users WHERE name = ?";
        int userId = 0;

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
        
        return userId;
    }

    public static List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        String query = "SELECT * FROM transactions";
        Connection conn = Database.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("owner_id"),
                    rs.getInt("lent_date"),
                    rs.getInt("return_date")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }

        return transactions;
    }
    
    public static void makeTransaction(String bookName, String userName) {
        int bookId = getBookIdByName(bookName);
        int userId = getUserIdByName(userName);
    
        if (bookId == 0 || userId == 0) {
            System.out.println("Book or User not found in the database.");
            return;
        }
    
        int lentDate = (int) Instant.now().getEpochSecond();
        int returnDate = 0;
    
        String query = "INSERT INTO transactions (book_id, owner_id, lent_date, return_date) VALUES (?, ?, ?, ?)";
    
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
    
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.setInt(3, lentDate);
            stmt.setInt(4, returnDate);
            stmt.executeUpdate();
    
            System.out.println("Book: " + bookName + ", Assigned to: " + userName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }

    public static void updateTransaction(int bookId, int userId) {
        if (bookId == 0 || userId == 0) {
            System.out.println("Book or User not found.");
            return;
        }
    
        String query = "UPDATE transactions SET return_date = ? WHERE book_id = ? AND owner_id = ? AND return_date = 0";
        int returnDate = (int) Instant.now().getEpochSecond();

        Connection conn = Database.getConnection();
    
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
    
            stmt.setInt(1, returnDate);
            stmt.setInt(2, bookId);
            stmt.setInt(3, userId);
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }
    
    public static void logTransaction(String filePath, JList<String> jList) {
        FileUtils fileUtils = new FileUtils();
    
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < jList.getModel().getSize(); i++) {
            content.append(jList.getModel().getElementAt(i)).append(System.lineSeparator());
        }
    
        fileUtils.writeToFile(filePath, content.toString());
    }
}
