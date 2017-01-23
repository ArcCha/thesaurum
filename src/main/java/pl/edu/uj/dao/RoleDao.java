package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Role;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class RoleDao extends AbstractDao {
    private static final Set<String> roleNames = Stream.of("ADMIN", "JUDGE", "SECRETARY", "APPLICANT")
            .collect(Collectors.toSet());

    @Transactional
    public Optional<Role> getByName(String name) {
        Session session = getCurrentSession();
        Query<Role> query = session.createQuery("from Role where name = :name", Role.class)
                .setParameter("name", name);
        return query.uniqueResultOptional();
    }

    @Transactional
    void ensureRolesExist() {
        Session session = getCurrentSession();
        Query<Role> query = session.createQuery("from Role where name = :name", Role.class);
        for (String roleName : roleNames) {
            query.setParameter("name", roleName);
            Optional<Role> role = query.uniqueResultOptional();
            if (!role.isPresent()) {
                Role newRole = new Role(roleName);
                insert(newRole);
            }
        }
    }

    @Transactional
    private void insert(Role role) {
        Session session = getCurrentSession();
        session.save(role);
    }
}
