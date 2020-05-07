package dao;

import model.Patient;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {
    public void add(Patient patient) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into patient values(null,?,?,?,?,?,null,null,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, patient.getName());
        ps.setString(2, patient.getAddress());
        ps.setInt(3, patient.getAge());
        ps.setInt(4, patient.getGender());
        ps.setString(5, patient.getPhone());
        ps.setString(6,patient.getPinyin());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void update(Patient patient) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "update patient set name=?,  address=?, age=?,gender=?, visit_time=?, last_time=?, phone=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, patient.getName());
        ps.setString(2, patient.getAddress());
        ps.setInt(3, patient.getAge());
        ps.setInt(4, patient.getGender());
        ps.setInt(5, patient.getVisit_time());
        if (null != patient.getLast_time()) {
            ps.setTimestamp(6, new Timestamp(patient.getLast_time().getTime()));
        } else {
            ps.setTimestamp(6, null);
        }
        if (null != patient.getPhone()) {
            ps.setString(7, patient.getPhone());
        } else {
            ps.setString(7, null);
        }

        ps.setInt(8, patient.getId());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void delete(int id) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "delete from patient where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }


    public List<Patient> queryAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from patient order by last_time desc";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Patient patient = new Patient();
            patient.setId(rs.getInt(1));
            patient.setName(rs.getString(2));
            patient.setAddress(rs.getString(3));
            patient.setAge(rs.getInt(4));
            patient.setGender(rs.getInt(5));
            patient.setPhone(rs.getString(6));
            patient.setVisit_time(rs.getInt(7));
            patient.setLast_time(rs.getTimestamp(8));
            patient.setPinyin(rs.getString(9));
            patients.add(patient);
        }
        JDBCUtils.release(conn);
        return patients;
    }

    public List<Patient> queryLike(String key, String pattern) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from patient where " + key + " like '%" + pattern + "%' order by last_time desc";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Patient patient = new Patient();
            patient.setId(rs.getInt(1));
            patient.setName(rs.getString(2));
            patient.setAddress(rs.getString(3));
            patient.setAge(rs.getInt(4));
            patient.setGender(rs.getInt(5));
            patient.setPhone(rs.getString(6));
            patient.setVisit_time(rs.getInt(7));
            patient.setLast_time(rs.getTimestamp(8));
            patient.setPinyin(rs.getString(9));
            patients.add(patient);
        }
        JDBCUtils.release(conn);
        return patients;
    }
}
