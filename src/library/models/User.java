package library.models;

import java.sql.Connection;
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

    // Constructor
    public User(String id, String name, String email, String phone_number) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
    
    public static List<Object[]> getAllUsers() {
        List<Object[]> users = new ArrayList<>();
        String query = "SELECT id, name, email, phone_number FROM users";
        
        Connection conn = Database.getConnection(); 
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Object[] user = new Object[4];
                user[0] = rs.getInt("id");
                user[1] = rs.getString("name");
                user[2] = rs.getString("email");
                user[3] = rs.getString("phone_number");
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Database.closeConnection(conn);
        return users;
    }
}
