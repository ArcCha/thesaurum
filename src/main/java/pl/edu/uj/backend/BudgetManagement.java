package pl.edu.uj.backend;

import org.springframework.security.access.prepost.PreAuthorize;
import pl.edu.uj.bo.Budget;

import java.util.List;

public interface BudgetManagement {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void add(Budget budget);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<Budget> getAll();
}
