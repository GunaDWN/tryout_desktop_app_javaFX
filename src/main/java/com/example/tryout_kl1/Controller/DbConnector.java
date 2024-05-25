package com.example.tryout_kl1.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public Connection databaseLink;
    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/tryout_apk";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection (url,user,password);
        }catch (Exception e){
            e.printStackTrace();
        }
            return databaseLink;
    }
}