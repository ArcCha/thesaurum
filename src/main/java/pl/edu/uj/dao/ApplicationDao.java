package pl.edu.uj.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Application;

@Repository
public class ApplicationDao extends AbstractDao {
    @Transactional
    public void insert(Application application) {
        Session session = getCurrentSession();
        session.save(application);
    }
}
