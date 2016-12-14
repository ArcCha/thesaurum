package pl.edu.uj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.User;
import pl.edu.uj.dao.UserDao;

import java.util.List;

@Component
public class UserService {
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void add(User user) {
        getUserDao().insert(user);
    }

    public List<User> fetchAllUsers() {
        return getUserDao().getAll();
    }
}
