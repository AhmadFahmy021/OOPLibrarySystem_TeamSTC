package app.model;

import data.Book;
import database.Koneksi;

import java.sql.*;
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

    public List<Book> getBookById(int id){
        List<Book> bookList = new ArrayList<>();
        Connection conn = Koneksi.getConnection();

        try {
            String SQL = "SELECT * FROM "+table+ " WHERE id_book = "+id;
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

    public boolean create(Book book) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = Koneksi.getConnection();
            String SQL = "INSERT INTO " + table + " (isbn, title, author, quantity, cover) VALUES (?, ?, ?, ?, ?)";
            //              Kolom ke-1  2     3        4         5
            statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());          // Masuk ke 'isbn'
            statement.setString(2, book.getTitle());         // Masuk ke 'title'
            statement.setString(3, book.getAuthor());        // Masuk ke 'author'
            statement.setString(4, book.getQuantity());         // <-- PERBAIKAN: Gunakan setInt(), masuk ke 'quantity'
            statement.setString(5, book.getCoverImagePath()); // Masuk ke 'cover'

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Ambil ID yang di-generate oleh database (jika id_book adalah AUTO_INCREMENT)
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getInt(1)); // Mengupdate ID objek Book dengan ID dari DB
                    }
                }
                return true; // Berhasil menyimpan
            }
        } catch (SQLException e) { // Gunakan SQLException
            System.err.println("Error saat menambahkan buku: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
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
