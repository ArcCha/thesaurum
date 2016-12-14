package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.bo.Budget;

import java.util.List;

@Repository
public class BudgetDao extends AbstractDao {

    public void insert(Budget budget) {
        Session session = getCurrentSession();
        session.save(budget);
    }

    public List<Budget> getAll() {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Budget order by id");
        return query.list();
    }
}