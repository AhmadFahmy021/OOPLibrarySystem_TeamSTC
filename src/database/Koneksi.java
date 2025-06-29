package database;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {
    public static Connection conn;
    public static Statement state;
//    INI DIGUNAKAN SEBAGAI KONEKSI DATABASE
    private static final String URL = "jdbc:mysql://localhost/library";
    private static final String USER = "root";
    private static final String PASS = "";
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Koneksi berhasil.");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Koneksi GAGAL: " + e.getMessage());
            }
        }
        return conn;
    }



}
