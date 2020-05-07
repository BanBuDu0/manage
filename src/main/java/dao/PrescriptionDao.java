package dao;

import model.Prescription;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDao {
    public void add(Prescription prescription) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into prescription values(null,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, prescription.getHistory());
        ps.setString(2, prescription.getSign());
        ps.setString(3, prescription.getTongue());
        ps.setString(4, prescription.getPulse_left());
        ps.setString(5, prescription.getPulse_right());
        ps.setString(6, prescription.getDiagnosis());
        ps.setString(7, prescription.getPrescribe());
        ps.setInt(8, prescription.getPatient_id());
        if (null != prescription.getCreate_time()) {
            ps.setTimestamp(9, new Timestamp(prescription.getCreate_time().getTime()));
        } else {
            ps.setTimestamp(9, null);
        }
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void delete(int id) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "delete from prescription where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public void update(Prescription prescription) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        String sql = "update prescription set history=?, sign=?, tongue=?, pulse_left=?, pulse_right=?, diagnosis=?, prescribe=?, patient_id=?, create_time=? where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, prescription.getHistory());
        ps.setString(2, prescription.getSign());
        ps.setString(3, prescription.getTongue());
        ps.setString(4, prescription.getPulse_left());
        ps.setString(5, prescription.getPulse_right());
        ps.setString(6, prescription.getDiagnosis());
        ps.setString(7, prescription.getPrescribe());
        ps.setInt(8, prescription.getPatient_id());
        if (null != prescription.getCreate_time()) {
            ps.setDate(9, new java.sql.Date(prescription.getCreate_time().getTime()));
        } else {
            ps.setDate(9, null);
        }
        ps.setInt(10, prescription.getId());
        ps.executeUpdate();
        JDBCUtils.release(conn);
    }

    public List<Prescription> queryAll() throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from prescription";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Prescription prescription = new Prescription();
            prescription.setId(rs.getInt("id"));
            prescription.setHistory(rs.getString("history"));
            prescription.setTongue(rs.getString("tongue"));
            prescription.setSign(rs.getString("sign"));
            prescription.setPulse_left(rs.getString("pulse_left"));
            prescription.setPulse_right(rs.getString("pulse_right"));
            prescription.setDiagnosis(rs.getString("diagnosis"));
            prescription.setPrescribe(rs.getString("prescribe"));
            prescription.setPatient_id(rs.getInt("patient_id"));
            prescription.setCreate_time(rs.getTimestamp("create_time"));
            prescriptions.add(prescription);
        }
        JDBCUtils.release(conn);
        return prescriptions;
    }

    public List<Prescription> queryByID(int id) throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        Connection conn = JDBCUtils.getConnection();
        String sql = "select * from prescription where patient_id=? order by create_time desc";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Prescription prescription = new Prescription();
            prescription.setId(rs.getInt("id"));
            prescription.setHistory(rs.getString("history"));
            prescription.setTongue(rs.getString("tongue"));
            prescription.setSign(rs.getString("sign"));
            prescription.setPulse_left(rs.getString("pulse_left"));
            prescription.setPulse_right(rs.getString("pulse_right"));
            prescription.setDiagnosis(rs.getString("diagnosis"));
            prescription.setPrescribe(rs.getString("prescribe"));
            prescription.setPatient_id(rs.getInt("patient_id"));
            prescription.setCreate_time(rs.getTimestamp("create_time"));
            prescriptions.add(prescription);
        }
        JDBCUtils.release(conn);
        return prescriptions;
    }

}
