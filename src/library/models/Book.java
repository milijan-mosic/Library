package library.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private String ownerId;
    private int releaseDate;

    // Constructor
    public Book(String id, String title, String author, String category, String ownerId, int releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.ownerId = ownerId;
        this.releaseDate = releaseDate;
    }

    // Getters
    public String getId() {
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

    // Setters
    public void setId(String id) {
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

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
    

    public static List<Object[]> getAllBooks() {
        List<Object[]> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Object[] book = new Object[4];
                book[0] = rs.getInt("id");
                book[1] = rs.getString("title");
                book[2] = rs.getString("author");
                book[3] = rs.getDate("release_date");
                books.add(book);            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Database.closeConnection(conn);
        return books;
    }
}
