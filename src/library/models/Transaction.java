package library.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class Transaction {
    private String id;
    private String bookId;
    private String ownerId;
    private int lentDate;
    private int returnDate;

    public Transaction(String id, String bookId, String ownerId, int lentDate, int returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.ownerId = ownerId;
        this.lentDate = lentDate;
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public int getLentDate() {
        return lentDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setOwnerId(String ownerId) {
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

    private static int getBookIdByName(String bookName) {
        String query = "SELECT id FROM books WHERE title = ?";
        int bookId = 0;

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bookName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bookId = Integer.parseInt(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
        
        return bookId;
    }
    
    private static int getUserIdByName(String userName) {
        String query = "SELECT id FROM users WHERE name = ?";
        int userId = 0;

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = Integer.parseInt(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
        
        return userId;
    }
        
    public static void makeTransaction(String bookName, String userName) {
        int bookId = getBookIdByName(bookName);
        int userId = getUserIdByName(userName);

        if (bookId == 0 || userId == 0) {
            System.out.println("Book or User not found in the database.");
        }

        int lentDate = (int) Instant.now().getEpochSecond();
        int returnDate = 0;

        String query = "INSERT INTO transactions (book_id, owner_id, lent_date, return_date) VALUES (?, ?, ?, ?)";

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, String.valueOf(bookId));
            stmt.setString(2, String.valueOf(userId));
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
}
