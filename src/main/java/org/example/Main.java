package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres",
                            "123456");

            if(conn!=null) {
                System.out.println("Connected to the database");
            }
            else {
                System.out.println("Failed to connect to the database");
            }

            Statement statement = conn.createStatement();

            String query = "create table student(id int primary key, name varchar(20));";

            int result = statement.executeUpdate(query);

            System.out.println("Query executed with result: " + result);

            statement.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}