package pl.edu.uj.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.service.BudgetService;

import java.util.List;

@Service
public class BudgetManagementBean implements BudgetManagement{

    private BudgetService budgetService;

    @Autowired
    public BudgetManagementBean(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Override
    public void add(Budget budget) {
        budgetService.add(budget);
    }

    @Override
    public List<Budget> getAll() {
        return budgetService.fetchAllBudgets();
    }
}
