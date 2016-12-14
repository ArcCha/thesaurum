package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.backend.BudgetManagement;
import pl.edu.uj.bo.Budget;
import pl.edu.uj.bo.BudgetPool;

import java.util.Date;
import java.util.Set;

@Secured("ROLE_ADMIN")
@SpringView(name = "budgetList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Budget List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class BudgetListView extends CustomComponent implements View {

    private BudgetManagement budgetManagement;

    private TextField nameField;
    private DateField startField;
    private DateField endField;

    private TextField poolNameField;
    private TextField valueField;

    private Budget currentBudget = null;
    private Table poolTable;

    @Autowired
    public BudgetListView(BudgetManagement budgetManagement) {
        this.budgetManagement = budgetManagement;
        init();
    }

    private void init() {
        Table budgetTable = new Table("Budgets");
        budgetTable.addContainerProperty("Name", String.class, null);
        budgetTable.addContainerProperty("Start date", Date.class, null);
        budgetTable.addContainerProperty("End date", Date.class, null);

        int i = 0;
        for (Budget budget : budgetManagement.getAll()) {
            budgetTable.addItem(new Object[]{
                budget.getName(),
                budget.getStartDate(),
                budget.getEndDate()
            }, i);
            ++i;
        }

        budgetTable.setPageLength(budgetTable.size());
        budgetTable.addItemClickListener(ItemClickEvent -> {
            currentBudget = budgetManagement.getByName((String)ItemClickEvent.getItem().getItemProperty("Name").getValue());
            updateTable();
            System.out.println(currentBudget);
        });
        FormLayout budgetForm = buildBudgetForm();

        poolTable = new Table("Pools");
        poolTable.addContainerProperty("Name", String.class, null);
        poolTable.addContainerProperty("Amount", Integer.class, null);
        poolTable.setPageLength(poolTable.size());

        FormLayout poolForm = buildPoolForm();

        HorizontalLayout rootLayout = new HorizontalLayout();
        rootLayout.setSizeFull();
        VerticalLayout budgetsLayout = new VerticalLayout();
        budgetsLayout.addComponent(budgetTable);
        budgetsLayout.addComponent(budgetForm);

        VerticalLayout poolsLayout = new VerticalLayout();
        poolsLayout.addComponent(poolTable);
        poolsLayout.addComponent(poolForm);

        rootLayout.addComponent(budgetsLayout);
        rootLayout.addComponent(poolsLayout);
        setCompositionRoot(rootLayout);
    }

    private FormLayout buildBudgetForm() {
        FormLayout layout = new FormLayout();
        nameField = new TextField("Name");
        startField = new DateField("Start date");
        endField = new DateField("End date");
        Button addButton = new Button("Add");
        layout.addComponent(nameField);
        layout.addComponent(startField);
        layout.addComponent(endField);
        layout.addComponent(addButton);
        addButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addButton.addClickListener(event -> {
            Budget budget = new Budget(
                    nameField.getValue(),
                    startField.getValue(),
                    endField.getValue());
            budgetManagement.add(budget);
            Page.getCurrent().reload();
        });
        return layout;
    }

    private FormLayout buildPoolForm() {
        FormLayout layout = new FormLayout();
        poolNameField = new TextField("Name");
        valueField = new TextField("Value");
        Button addButton = new Button("Add");
        layout.addComponent(poolNameField);
        layout.addComponent(valueField);
        layout.addComponent(addButton);
        addButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addButton.addClickListener(event -> {
            BudgetPool pool = new BudgetPool(
                    poolNameField.getValue(),
                    Integer.parseInt(valueField.getValue()),
                    currentBudget);
            Set<BudgetPool> pools = currentBudget.getBudgetPools();
            pools.add(pool);
            currentBudget.setBudgetPools(pools);
            budgetManagement.update(currentBudget);
            updateTable();
        });
        return layout;
    }

    private void updateTable() {
        poolTable.removeAllItems();
        int i = 0;
        for (BudgetPool pool : currentBudget.getBudgetPools()) {
            poolTable.addItem(new Object[]{
                    pool.getName(),
                    pool.getValue()
            }, i);
            ++i;
        }
        poolTable.setPageLength(poolTable.size());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
