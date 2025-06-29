package app.model;

import data.Member;
import database.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Member_Model{
    private static Connection conn;
    private static String table = "member";


    public List<Member> all(){
        List<Member> memberList = new ArrayList<>();
        Connection conn = Koneksi.getConnection();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberList.add(new Member(
                        rs.getInt("id"),
                        rs.getString("id_member"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error saat ambil data: " + e.getMessage());
        }
        return memberList;
    }
}
