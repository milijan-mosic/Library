package library.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book extends LibraryItem {
    private int id;
    private String title;
    private String author;
    private String category;
    private String ownerId;
    private int releaseDate;
    private int rating;

    public Book(int id, String title, String author, String category, String ownerId, int releaseDate, int rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.ownerId = ownerId;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public int getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String getDisplayName() {
        return title + " by " + author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                '}';
    }

    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Object[]> getAllBooks() {
        List<Object[]> books = new ArrayList<>();

        String query = "SELECT * FROM books";
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] book = new Object[7];
                book[0] = rs.getInt("id");
                book[1] = rs.getString("title");
                book[2] = rs.getString("author");
                book[3] = rs.getString("category");
                book[4] = rs.getString("owner_id");
                book[5] = rs.getString("release_date");
                book[6] = rs.getInt("rating");
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }

        return books;
    }
    
    public static Book getBook(int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        Book selectedBook = null;
        
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Integer bookId = Integer.parseInt(rs.getString("id"));
                String title = rs.getString("title");
                String author = rs.getString("author");
                String category = rs.getString("category");
                String ownerId = rs.getString("owner_id");
                int releaseDate = Integer.parseInt(rs.getString("release_date"));
                int rating = Integer.parseInt(rs.getString("rating"));
                
                selectedBook = new Book(bookId, title, author, category, ownerId, releaseDate, rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
        
        return selectedBook;
    }
    
    public static void insertBook(String title, String author, String category, String ownerId, int releaseDate, int rating) {
        String query = "INSERT INTO books (title, author, category, owner_id, release_date) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);
            stmt.setString(4, ownerId);
            stmt.setInt(5, releaseDate);
            stmt.setInt(6, rating);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }

    public static void updateBook(int id, String title, String author, String category, String ownerId, int releaseDate, int rating) {
        String query = "UPDATE books SET title = ?, author = ?, category = ?, release_date = ?, rating = ? WHERE id = ? AND owner_id = ?";
    
        Connection conn = Database.getConnection();
    
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
    
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);
            stmt.setInt(4, releaseDate);
            stmt.setInt(5, rating);
            stmt.setInt(6, id);
            stmt.setString(7, ownerId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }

    public static void deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?";
        
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully");
            } else {
                System.out.println("Book not found or already deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }
}
