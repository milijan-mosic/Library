package library.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone_number;
    private String note;

    // Constructor
    public User(String id, String name, String email, String phone_number, String note) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.note = note;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getNote() {
        return note;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
    
    public static List<Object[]> getAllUsers() {
        List<Object[]> users = new ArrayList<>();
        String query = "SELECT id, name, email, phone_number, note FROM users";
        
        Connection conn = Database.getConnection(); 
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Object[] user = new Object[5];
                user[0] = rs.getInt("id");
                user[1] = rs.getString("name");
                user[2] = rs.getString("email");
                user[3] = rs.getString("phone_number");
                user[4] = rs.getString("note");
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Database.closeConnection(conn);
        return users;
    }
    
    public static void insertUser(String name, String email, String phoneNumber, String note) {
        String query = "INSERT INTO users (name, email, phone_number, note) VALUES (?, ?, ?, ?)";
        
        Connection conn = Database.getConnection();
        
        try {
        	PreparedStatement stmt = conn.prepareStatement(query);
        	
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, note);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }
}
