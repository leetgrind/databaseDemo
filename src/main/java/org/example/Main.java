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

            int result = deleteRow(statement);

            System.out.println("Number of rows deleted: " + result);

            fakerInsertRows(conn);

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    static int deleteRow(Statement statement) throws SQLException {
        String deleteQuery = "DELETE FROM AUTHOR WHERE ID='96aeab72-af91-4067-aa3b-ac9221e9f867'";
        return statement.executeUpdate(deleteQuery);
    }

    static void fakerInsertRows(Connection connection) throws SQLException {

        Faker faker = new Faker();
        String id = UUID.randomUUID().toString();
        String name = faker.name().firstName();
        String bookName = faker.book().title();

        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO author values(?, ?, ?);");

        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, bookName);

        preparedStatement.executeUpdate();
    }

    static void insertRowResultSet(Statement statement) throws SQLException {

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

        rs.close();
    }

    static void deleteResultSet(Statement statement) throws SQLException {

        String selectQuery = "SELECT * FROM AUTHOR";

        ResultSet rs = statement.executeQuery(selectQuery);

        while (rs.next()) {
           String name = rs.getString("Name");
           if(name.equals("Shon")) {
               //rs.deleteRow();

               rs.updateString("book", "John");
           }
        }
    }
}