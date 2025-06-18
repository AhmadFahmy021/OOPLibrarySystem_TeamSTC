package app.model;

import data.Book;
import database.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Book_Model {
    private static Connection conn ;
    private static String table = "book";

    public List<Book> all(){
        List<Book> bookList = new ArrayList<>();
        Connection conn = Koneksi.getConnection();

        try {
            String SQL = "SELECT * FROM "+table;
            PreparedStatement statement = conn.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
            System.out.println(rs);
                bookList.add(new Book(
                        rs.getInt("id_book"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("quantity"),
                        rs.getString("cover")
                ));
            }
        } catch (Exception e){
            System.out.println("Error saat pengambilan data "+e);
        }
        return bookList;
    }

    public int count(String query){
        List<Book> bookList = new ArrayList<>();
        Connection conn = Koneksi.getConnection();
        int count = 0;

        try {
            String SQL = "SELECT SUM(quantity) as total_book FROM "+table;
            PreparedStatement statement = conn.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                count = rs.getInt("total_book");
            }

        } catch (Exception e){
            System.out.println("Error saat pengambilan data "+e);
        }
        return count;
    }
}
