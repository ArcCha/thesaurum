package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.bo.User;

import java.util.List;

@Repository
public class UserDao extends AbstractDao {
    public void insert(User user) {
        Session session = getCurrentSession();
        session.save(user);
    }

    public List<User> getAll() {
        Session session = getCurrentSession();
        Query query = session.createQuery("from users");
        List<User> users = query.list();
        return users;
    }

    public User findByUsername(String username) {
        Session session = getCurrentSession();
        Query query = session.createQuery("from User where username=?").setParameter(0, username);
        List<User> users = query.list();
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
}
