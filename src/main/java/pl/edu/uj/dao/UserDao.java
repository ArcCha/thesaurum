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
        Query query = session.createQuery("from User order by id");
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

    public void enable(User user) {
        Session session = getCurrentSession();
        Query query = session.createQuery("update User set enabled=True where id=?")
                .setParameter(0, user.getId());
        query.executeUpdate();
    }

    public void disable(User user) {
        Session session = getCurrentSession();
        Query query = session.createQuery("update User set enabled=False where id=?")
                .setParameter(0, user.getId());
        query.executeUpdate();
    }
}
