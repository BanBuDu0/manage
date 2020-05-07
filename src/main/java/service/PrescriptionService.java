package service;

import dao.PrescriptionDao;
import model.Patient;
import model.Prescription;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionService {
    private PrescriptionDao prescriptionDao = new PrescriptionDao();

    public boolean delete(int id) {
        try {
            prescriptionDao.delete(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Prescription> queryAll() {
        try {
            return prescriptionDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(Prescription item) {
        try {
            prescriptionDao.add(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean update(Prescription item) {
        try {
            prescriptionDao.update(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Prescription> queryByID(int id) {
        try {
            return prescriptionDao.queryByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
