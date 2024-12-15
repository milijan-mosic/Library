package library.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone_number;
    private String note;
    private Boolean active;

    public User(int id, String name, String email, String phone_number, String note, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.note = note;
        this.active = active;
    }

    public int getId() {
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

    public Boolean getActive() {
        return active;
    }

    public void setId(int id) {
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

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", note='" + note + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
    
    public static List<Object[]> getAllUsers() {
        List<Object[]> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        Connection conn = Database.getConnection(); 
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Object[] user = new Object[6];
                user[0] = rs.getInt("id");
                user[1] = rs.getString("name");
                user[2] = rs.getString("email");
                user[3] = rs.getString("phone_number");
                user[4] = rs.getString("note");
                user[5] = rs.getBoolean("active");
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Database.closeConnection(conn);
        return users;
    }
    
    public static User getUser(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User selectedUser = null;
    
        Connection conn = Database.getConnection();
    
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String note = rs.getString("note");
                Boolean active = rs.getBoolean("active");
    
                selectedUser = new User(id, name, email, phoneNumber, note, active);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    
        return selectedUser;
    }
    
    public static void insertUser(String name, String email, String phoneNumber, String note) {
        String query = "INSERT INTO users (name, email, phone_number, note, active) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, note);
            stmt.setBoolean(5, true);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }

    public static void updateUser(int id, String name, String email, String phoneNumber, String note, Boolean active) {
        String query = "UPDATE users SET name = ?, email = ?, phone_number = ?, note = ?, active = ? WHERE id = ?";
    
        Connection conn = Database.getConnection();
    
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, note);
            stmt.setBoolean(5, active);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }

    public static void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        
        Connection conn = Database.getConnection();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User not found or already deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Database.closeConnection(conn);
        }
    }
}
