package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Role;
import pl.edu.uj.bo.User;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDao extends AbstractDao {
    @Inject
    private RoleDao roleDao;
    @Inject
    private PasswordEncoder passwordEncoder;

    public void insert(User user) {
        Session session = getCurrentSession();
        session.save(user);
    }

    public void update(User user) {
        Session session = getCurrentSession();
        session.update(user);
    }

    public List<User> getAll() {
        Session session = getCurrentSession();
        Query<User> query = session.createQuery("from User order by id", User.class);
        return query.list();
    }

    @Transactional
    public User findByUsername(String username) {
        Session session = getCurrentSession();
        Query<User> query = session.createQuery("from User where username = :username", User.class)
                .setParameter("username", username);
        List<User> users = query.list();
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public void enable(User user) {
        Session session = getCurrentSession();
        Query query = session.createQuery("update User set enabled = True where id = :id")
                .setParameter("id", user.getId());
        query.executeUpdate();
    }

    public void disable(User user) {
        Session session = getCurrentSession();
        Query query = session.createQuery("update User set enabled = False where id = :id")
                .setParameter("id", user.getId());
        query.executeUpdate();
    }

    @Transactional
    public void ensureAdminExists() {
        Session session = getCurrentSession();
        Query<User> query = session.createQuery("from User where username = 'admin'", User.class);
        Optional<User> admin = query.uniqueResultOptional();
        if (!admin.isPresent()) {
            User newAdmin = new User();
            newAdmin.setUsername("admin");
            newAdmin.setPassword(passwordEncoder.encode("admin"));
            newAdmin.setEnabled(true);
            Optional<Role> adminRole = roleDao.getByName("ADMIN");
            if (adminRole.isPresent()) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole.get());
                newAdmin.setRoles(roles);
            } else {
                throw new RuntimeException("Admin role is not present during ensure phase.");
            }
            insert(newAdmin);
        }
    }
}
