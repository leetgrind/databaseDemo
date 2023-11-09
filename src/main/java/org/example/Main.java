package org.example;

import com.github.javafaker.Faker;

import java.sql.*;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        // try catch with resources

        try {
            Class.forName("org.postgresql.Driver");
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

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            String createTableQuery = "CREATE TABLE AUTHOR (ID VARCHAR(100), NAME VARCHAR(20), BOOK VARCHAR(20));";

            String selectQuery = "SELECT * FROM AUTHOR";

            Faker faker = new Faker();

            ResultSet rs = statement.executeQuery(selectQuery);

            String name = faker.name().firstName();
            String bookName = faker.book().title();

            rs.moveToInsertRow();
            rs.updateString("ID", UUID.randomUUID().toString());
            rs.updateString("NAME", name);
            rs.updateString("BOOK", bookName);
            rs.insertRow();

            System.out.println("Row inserted with book name " + bookName);

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}