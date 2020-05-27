package Server;

import Client.User;

import java.sql.*;

public class Registration {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "s284775";
    private static final String PASS = "zrj839";
    private boolean success = true;

    public void toRegistration(User user) {
        user.setStatus(true);
        System.out.println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                if (user.getName().equals(resultSet.getString("name"))) {
                    user.setStatus(false);
                    success = false;
                }
            }
            if (success) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                int rows = preparedStatement.executeUpdate();
                if (rows == 0){
                    user.setStatus(false);
                }
            }
        }
        catch(SQLException e){
            System.out.println("SQLExeption fix it");
            user.setStatus(false);
        }
    }
}
