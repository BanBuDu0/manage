package service;

import dao.RecipelDao;
import model.Recipel;

import java.sql.SQLException;
import java.util.List;

public class RecipelService {
    private final RecipelDao recipelDao = new RecipelDao();

    public boolean delete(int id) {
        try {
            recipelDao.delete(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Recipel> queryAll() {
        try {
            return recipelDao.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Recipel> queryLike(String key, String pattern) {
        try {
            return recipelDao.queryLike(key, pattern);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(Recipel item) {
        try {
            recipelDao.add(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean update(Recipel item) {
        try {
            recipelDao.update(item);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
