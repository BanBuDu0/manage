package service;

import dao.PatientDao;
import model.Patient;

import java.sql.SQLException;
import java.util.List;

public class PatientService {
    private final PatientDao patientDao = new PatientDao();

    public boolean delete(int id) {
        try {
            patientDao.delete(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Patient> queryAll() {
        try {
            return patientDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(Patient item) {
        try {
            patientDao.add(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean update(Patient item) {
        try {
            patientDao.update(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Patient> queryLike(String key, String pattern) {
        try {
            return patientDao.queryLike(key, pattern);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
