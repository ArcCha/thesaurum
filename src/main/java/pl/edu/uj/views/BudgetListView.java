package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.bo.BudgetPool;
import pl.edu.uj.dao.BudgetDao;
import pl.edu.uj.views.forms.BudgetForm;
import pl.edu.uj.views.forms.BudgetPoolForm;

import java.util.List;
import java.util.Set;

@Secured("ADMIN")
@SpringView(name = "budgetList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Budget List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class BudgetListView extends CustomComponent implements View {
    private BudgetDao budgetDao;
    private Budget selectedBudget;

    @Autowired
    public BudgetListView(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
        init();
    }

    private void init() {
        List<Budget> budgets = budgetDao.getAll();

        MTable<Budget> budgetTable = new MTable<>(budgets)
                .withProperties("name", "startDate", "endDate")
                .withColumnHeaders("Name", "Start", "End");
        budgetTable.setPageLength(budgetTable.size());

        MTable<BudgetPool> poolTable = new MTable<>(BudgetPool.class)
                .withProperties("name", "value")
                .withColumnHeaders("Name", "Value");
        poolTable.setPageLength(poolTable.size());

        budgetTable.addRowClickListener(event -> {
            if (event.isDoubleClick()) {
                BudgetForm budgetForm = new BudgetForm();
                budgetForm.setEntity(event.getRow());
                budgetForm.setSavedHandler(budget -> {
                    budgetDao.update(budget);
                    budgetTable.removeAllItems();
                    budgetTable.addItems(budgetDao.getAll());
                    budgetForm.closePopup();
                });
                budgetForm.openInModalPopup();
            }
            selectedBudget = event.getRow();
            refreshBudgetPoolsTable(event.getRow(), poolTable);
        });

        poolTable.addRowClickListener(event -> {
            if (event.isDoubleClick()) {
                BudgetPoolForm poolForm = new BudgetPoolForm();
                poolForm.setEntity(event.getRow());
                poolForm.setSavedHandler(pool -> {
                    budgetDao.update(pool.getBudget());
                    refreshBudgetPoolsTable(pool.getBudget(), poolTable);
                    poolForm.closePopup();
                });
            }
        });


        MButton addBudgetBtn = new MButton("Add new")
                .withStyleName(ValoTheme.BUTTON_FRIENDLY)
                .withListener(event -> {
                    BudgetForm budgetForm = new BudgetForm();
                    budgetForm.setEntity(new Budget());
                    budgetForm.setSavedHandler(budget -> {
                        budgetDao.insert(budget);
                        budgetTable.removeAllItems();
                        budgetTable.addItems(budgetDao.getAll());
                    });
                    budgetForm.openInModalPopup();
                });

        MButton addPoolBtn = new MButton("Add new")
                .withStyleName(ValoTheme.BUTTON_FRIENDLY)
                .withListener(event -> {
                    if (selectedBudget == null) {
                        Notification.show(
                                "You need to select budget first",
                                Notification.Type.ERROR_MESSAGE);
                        return;
                    }
                    BudgetPoolForm poolForm = new BudgetPoolForm();
                    poolForm.setEntity(new BudgetPool());
                    poolForm.setSavedHandler(pool -> {
                            selectedBudget.addBudgetPool(pool);
                            budgetDao.update(selectedBudget);
                            refreshBudgetPoolsTable(pool.getBudget(), poolTable);
                    });
                    poolForm.openInModalPopup();
                });

        MVerticalLayout budgetsLayout = new MVerticalLayout()
                .withSizeUndefined()
                .with(budgetTable, addBudgetBtn);
        MVerticalLayout poolsLayout = new MVerticalLayout()
                .withSizeUndefined()
                .with(poolTable, addPoolBtn);
        MHorizontalLayout rootLayout = new MHorizontalLayout()
                .withSize(MSize.FULL_SIZE)
                .with(budgetsLayout, poolsLayout);
        setCompositionRoot(rootLayout);
    }

    private void refreshBudgetPoolsTable(Budget budget, MTable<BudgetPool> poolTable) {
        Set<BudgetPool> pools = budget.getBudgetPools();
        poolTable.removeAllItems();
        poolTable.addItems(pools);
        poolTable.setPageLength(poolTable.size());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
