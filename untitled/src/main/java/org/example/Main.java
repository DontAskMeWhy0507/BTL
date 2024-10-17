package org.example;

import java.awt.*;
import java.net.ConnectException;
import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlite:C:/Users/T-Rabbit Boys/OneDrive/Máy tính/SQLite/mydb.db";
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "SELECT * FROM USERS";

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                System.out.println("ID: " + id + ", Name: " + name + ", Password: " + password);
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}