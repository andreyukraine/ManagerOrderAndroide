package com.example.bx.myapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by BX on 06.06.2018.
 */

public class Database {

    public Connection connectionSQL(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");

            try {
                conn = DriverManager.getConnection("jdbc:mysql://timeke00.mysql.tools:3306/timeke00_gps?user=timeke00_gps&password=hvrpa9p6&encoding=utf8&useUnicode=true&characterEncoding=utf8");
                return conn;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (conn == null) {
                System.out.println("Not connect SQL BASE");
                System.exit(0);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        return null;
    }
}
