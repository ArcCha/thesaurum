package pl.edu.uj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.dao.BudgetDao;

import java.util.List;

@Component
public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetDao getBudgetDao() {
        return budgetDao;
    }

    @Autowired
    public void setBudgetDao(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void add(Budget budget) {
        getBudgetDao().insert(budget);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public List<Budget> fetchAllBudgets() {
        return getBudgetDao().getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Budget getByName(String name) {
        return getBudgetDao().getByName(name);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void update(Budget budget) {
        getBudgetDao().update(budget);
    }
}
