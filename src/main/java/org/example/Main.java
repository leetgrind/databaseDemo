package org.example;

import java.sql.*;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        // try catch with resources

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.
                    getConnection("jdbc:sqlite:test.db");

            if(conn!=null) {
                System.out.println("Connected to the database");
            }
            else {
                System.out.println("Failed to connect to the database");
            }

            Statement statement = conn.createStatement();

            String createTableQuery = "CREATE TABLE AUTHOR (ID VARCHAR(100), NAME VARCHAR(20), BOOK VARCHAR(20));";
            UUID uuid = UUID.randomUUID();
            String insertQuery = "INSERT INTO AUTHOR VALUES('" + uuid.toString() +"', 'JOHN', 'LEARN PROGRAMMING');";
            // c35a066e-5eed-4a21-a33c-85d6ce3d67bc
            System.out.println(insertQuery);

            int result = statement.executeUpdate(insertQuery);
            System.out.println("Result is :" + result);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}