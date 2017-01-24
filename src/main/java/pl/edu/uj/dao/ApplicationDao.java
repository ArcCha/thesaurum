package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Application;

import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationDao extends AbstractDao {
    @Transactional
    public void insert(Application application) {
        Session session = getCurrentSession();
        session.save(application);
    }

    @Transactional
    public void update(Application application) {
        Session session = getCurrentSession();
        session.update(application);
    }

    @Transactional
    public Optional<Application> getById(Integer id) {
        Session session = getCurrentSession();
        Query<Application> query = session.createQuery("from Application where id = :id", Application.class)
                .setParameter("id", id);
        return query.uniqueResultOptional();
    }

    @Transactional
    public void submit(Application application) {
        application.setState(Application.State.SUBMITTED);
        update(application);
    }

    @Transactional
    public List<Application> getSubmitted() {
        Session session = getCurrentSession();
        // TODO FIX STATE IT'S HORRIBLE
        Query<Application> query = session.createQuery("from Application where state = 1", Application.class);
        return query.list();
    }

    @Transactional
    public void schedule(Application application) {
        application.setState(Application.State.SCHEDULED);
        update(application);
    }
}
