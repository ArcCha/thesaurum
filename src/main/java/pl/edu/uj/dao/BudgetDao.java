package pl.edu.uj.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.bo.BudgetPool;

import java.util.List;
import java.util.Set;

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

    public Budget getByName(String name) {
        Session session = getCurrentSession();
        Query query = session.createQuery("from Budget where name=?").setParameter(0, name);
        return (Budget)query.getSingleResult();
    }

    public void update(Budget budget) {
        Session session = getCurrentSession();
        Set<BudgetPool> pools = budget.getBudgetPools();
        Query delete = session.createQuery("delete from BudgetPool where budget=?").setParameter(0, budget);
        delete.executeUpdate();
        for (BudgetPool pool : pools) {
            session.save(pool);
        }
    }
}
