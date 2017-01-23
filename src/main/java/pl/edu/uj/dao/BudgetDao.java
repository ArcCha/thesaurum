package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Budget;

import java.util.List;

@Repository
public class BudgetDao extends AbstractDao {
//    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void insert(Budget budget) {
        Session session = getCurrentSession();
        session.save(budget);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public List<Budget> getAll() {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Budget order by id");
        return query.list();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Budget getByName(String name) {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Budget where name = :name")
                .setParameter("name", name);
        return (Budget) query.getSingleResult();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void update(Budget budget) {
        Session session = getCurrentSession();
        session.update(budget);
    }
}
