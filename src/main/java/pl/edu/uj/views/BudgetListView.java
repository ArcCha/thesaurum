package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.bo.BudgetPool;
import pl.edu.uj.service.BudgetService;
import pl.edu.uj.views.forms.BudgetForm;
import pl.edu.uj.views.forms.BudgetPoolForm;

@Secured("ROLE_ADMIN")
@SpringView(name = "budgetList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Budget List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class BudgetListView extends CustomComponent implements View {
    private BudgetService budgetService;

    @Autowired
    public BudgetListView(BudgetService budgetService) {
        this.budgetService = budgetService;
        init();
    }

    private void init() {
        Budget current = budgetService.getCurrent();

        // TODO why can't I create empty table?
        // It does not render correctly when it's initially empty
        MTable<BudgetPool> poolTable = new MTable<>(current.getBudgetPools())
                .withProperties("name", "value")
                .withColumnHeaders("Name", "Value");
        poolTable.setPageLength(poolTable.size());

        BudgetPoolForm poolForm = new BudgetPoolForm();
        poolForm.setSavedHandler(pool -> {
            budgetService.update(pool.getBudget());
            poolTable.removeAllItems();
            poolTable.addItems(pool.getBudget().getBudgetPools());
            poolTable.setPageLength(poolTable.size());
        });
        poolTable.addMValueChangeListener(event -> {
            if (event.getValue() != null) {
                poolForm.setEntity(event.getValue());
            }
        });

        MTable<Budget> budgetTable = new MTable<>(budgetService.fetchAllBudgets())
                .withProperties("name", "startDate", "endDate")
                .withColumnHeaders("Name", "Start", "End");
        budgetTable.setPageLength(budgetTable.size());
        budgetTable.setSelected(current);

        BudgetForm budgetForm = new BudgetForm();
        budgetForm.setSavedHandler(budget -> {
            budgetService.update(budget);
            budgetTable.removeAllItems();
            budgetTable.addItems(budgetService.fetchAllBudgets());
        });
        budgetTable.addMValueChangeListener(event -> {
            if (event.getValue() != null) {
                budgetForm.setEntity(event.getValue());
                poolTable.removeAllItems();
                poolTable.addItems(event.getValue().getBudgetPools());
                poolTable.setPageLength(poolTable.size());
            }
        });

        MVerticalLayout budgetsLayout = new MVerticalLayout()
                .withFullHeight()
                .with(budgetTable, budgetForm);
        MVerticalLayout poolsLayout = new MVerticalLayout()
                .withFullHeight()
                .with(poolTable, poolForm);
        MHorizontalLayout rootLayout = new MHorizontalLayout()
                .withFullWidth()
                .with(budgetsLayout, poolsLayout);
        setCompositionRoot(rootLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
