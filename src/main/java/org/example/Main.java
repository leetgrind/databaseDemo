package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        // try catch with resources

        try(Connection conn = DriverManager.
                getConnection("jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "123456")) {

            if(conn!=null) {
                System.out.println("Connected to the database");
            }
            else {
                System.out.println("Failed to connect to the database");
            }

            Statement statement = conn.createStatement();

            //String query = "create table student(id int primary key, name varchar(20));";

            //String query = "INSERT INTO STUDENT VALUES(2, 'Amy');";

            String query = "SELECT * FROM STUDENT;";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                int id = rs.getInt("ID");
                String name = rs.getString("NAME");

                System.out.println("ID: " + id + ", Name: " + name);

            }

            //int result = statement.executeUpdate(query);

            //System.out.println("Query executed with result: " + result);

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}