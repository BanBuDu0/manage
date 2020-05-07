package service;

import dao.MedicineDao;
import model.Medicine;

import java.sql.SQLException;
import java.util.List;

public class MedicineService {
    private MedicineDao medicineDao = new MedicineDao();

    public boolean delete(int id) {
        try {
            medicineDao.delete(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Medicine> queryAll() {
        try {
            return medicineDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(Medicine item) {
        try {
            medicineDao.add(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean update(Medicine item) {
        try {
            medicineDao.update(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Medicine> queryLike(String key, String pattern) {
        try {
            return medicineDao.queryLike(key, pattern);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Medicine queryByName(String n){
        try {
            return medicineDao.queryByName(n);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
