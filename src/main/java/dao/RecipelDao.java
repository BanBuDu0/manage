package dao;

import model.Medicine;
import model.Recipel;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipelDao {
    public void add(Recipel recipel) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into recipel values(null,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, recipel.getName());
        ps.setString(2, recipel.getContent());
        ps.setString(3, recipel.getPinyin());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void delete(int id) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "delete from recipel where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void update(Recipel recipel) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "update recipel set name=?, content=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, recipel.getName());
        ps.setString(2, recipel.getContent());
        ps.setInt(3, recipel.getId());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public List<Recipel> queryAll() throws SQLException {
        List<Recipel> recipels = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from recipel";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Recipel recipel = new Recipel();
            recipel.setId(rs.getInt(1));
            recipel.setName(rs.getString(2));
            recipel.setContent(rs.getString(3));
            recipels.add(recipel);
        }
        JDBCUtils.release(conn);
        return recipels;
    }

    public List<Recipel> queryLike(String key, String pattern) throws SQLException {
        List<Recipel> recipels = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from recipel where " + key + " like '%" + pattern + "%'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Recipel recipel = new Recipel();
            recipel.setId(rs.getInt(1));
            recipel.setName(rs.getString(2));
            recipel.setContent(rs.getString(3));
            recipels.add(recipel);
        }
        JDBCUtils.release(conn);
        return recipels;
    }

}
