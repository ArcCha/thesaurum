package pl.edu.uj.views;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.bo.BudgetPool;
import pl.edu.uj.service.BudgetService;

@Secured("ROLE_ADMIN")
@SpringView(name = "budgetList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Budget List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class BudgetListView extends CustomComponent implements View {

    private BudgetService budgetService;

    private TextField nameField;
    private DateField startField;
    private DateField endField;

    private TextField poolNameField;
    private TextField valueField;

    private Budget currentBudget = null;
    private Table poolTable;

    @Autowired
    public BudgetListView(BudgetService budgetService) {
        this.budgetService = budgetService;
        init();
    }

    private void init() {
        Table budgetTable = new Table("Budgets");
        BeanItemContainer<Budget> budgetContainer = new BeanItemContainer<>(Budget.class);
        budgetContainer.addAll(budgetService.fetchAllBudgets());
        budgetTable.setContainerDataSource(budgetContainer);
        budgetTable.setPageLength(budgetTable.size());
        budgetTable.setEditable(true);
        budgetTable.setVisibleColumns("name", "startDate", "endDate");

        poolTable = new Table("Pools");
        BeanItemContainer<BudgetPool> poolContainer  = new BeanItemContainer<>(BudgetPool.class);
        poolTable.setPageLength(poolTable.size());


        VerticalLayout budgetsLayout = new VerticalLayout();
        budgetsLayout.setSizeUndefined();
        budgetsLayout.addComponent(budgetTable);

        VerticalLayout poolsLayout = new VerticalLayout();
        poolsLayout.setSizeUndefined();
        poolsLayout.addComponent(poolTable);

        HorizontalLayout rootLayout = new HorizontalLayout();
        rootLayout.setSizeFull();
        rootLayout.addComponent(budgetsLayout);
        rootLayout.addComponent(poolsLayout);
        setCompositionRoot(rootLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
