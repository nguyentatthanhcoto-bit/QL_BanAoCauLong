/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author mailx
 */
public class db_connect {
    private String database = "QLAO";
    private String user = "sa";
    private String password = "123";
    private String url = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=" + database;
    private Connection connection;
    public db_connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testConnection() {
        db_connect db = new db_connect();
        Connection connection = db.getConnection();
        System.out.println("Connected to database");
    }
}
