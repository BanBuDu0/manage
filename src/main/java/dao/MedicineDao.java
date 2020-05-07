package dao;

import model.Medicine;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDao {
    public void add(Medicine medicine) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into medicine values(null,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, medicine.getName());
        ps.setDouble(2, medicine.getNum());
        ps.setBigDecimal(3, medicine.getPurchase_price());
        ps.setBigDecimal(4, medicine.getSelling_price());
        ps.setString(5,medicine.getPinyin());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void delete(int id) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "delete from medicine where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void update(Medicine medicine) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "update medicine set name=?, num=?, purchase_price=?, selling_price=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, medicine.getName());
        ps.setDouble(2, medicine.getNum());
        ps.setBigDecimal(3, medicine.getPurchase_price());
        ps.setBigDecimal(4,medicine.getSelling_price());
        ps.setInt(5, medicine.getId());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public List<Medicine> queryAll() throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from medicine";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Medicine medicine = new Medicine();
            medicine.setId(rs.getInt(1));
            medicine.setName(rs.getString(2));
            medicine.setNum(rs.getDouble(3));
            medicine.setPurchase_price(rs.getBigDecimal(4));
            medicine.setSelling_price(rs.getBigDecimal(5));
            medicine.setPinyin(rs.getString(6));
            medicines.add(medicine);
        }
        JDBCUtils.release(conn);
        return medicines;
    }


    public Medicine queryByName(String n) throws SQLException {
        Medicine medicine = new Medicine();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from medicine where name='" + n + "'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            medicine.setId(rs.getInt(1));
            medicine.setName(rs.getString(2));
            medicine.setNum(rs.getDouble(3));
            medicine.setPurchase_price(rs.getBigDecimal(4));
            medicine.setSelling_price(rs.getBigDecimal(5));
            medicine.setPinyin(rs.getString(6));
        }
        JDBCUtils.release(conn);
        return medicine;
    }

    public List<Medicine> queryLike(String key, String pattern) throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from medicine where " + key + " like '%" + pattern + "%'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Medicine medicine = new Medicine();
            medicine.setId(rs.getInt(1));
            medicine.setName(rs.getString(2));
            medicine.setNum(rs.getDouble(3));
            medicine.setPurchase_price(rs.getBigDecimal(4));
            medicine.setSelling_price(rs.getBigDecimal(5));
            medicine.setPinyin(rs.getString(6));
            medicines.add(medicine);
        }
        JDBCUtils.release(conn);
        return medicines;
    }

}
