package Server;

import Client.User;

import java.io.Serializable;
import java.sql.*;

public class Authorization implements Serializable {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "s284775";
    private static final String PASS = "zrj839";

    public void exist(User user){
        user.setStatus(false);
        System.out.println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                if(user.getName().equals(resultSet.getString("name")) && user.getPassword().equals(resultSet.getString("password"))) {
                    user.setStatus(true);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
