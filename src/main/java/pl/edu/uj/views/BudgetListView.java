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

import java.util.Date;

@Secured("ROLE_ADMIN")
@SpringView(name = "budgetList")
@SideBarItem(sectionId = Sections.VIEWS, caption = "Budget List View")
@FontAwesomeIcon(FontAwesome.COGS)
public class BudgetListView extends CustomComponent implements View {

    private BudgetManagement budgetManagement;

    private TextField nameField;
    private DateField startField;
    private DateField endField;

    @Autowired
    public BudgetListView(BudgetManagement budgetManagement) {
        this.budgetManagement = budgetManagement;
        init();
    }

    private void init() {
        Table table = new Table("Budgets");

        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Start date", Date.class, null);
        table.addContainerProperty("End date", Date.class, null);

        int i = 0;
        for (Budget budget : budgetManagement.getAll()) {
            table.addItem(new Object[]{
                budget.getName(),
                budget.getStartDate(),
                budget.getEndDate()
            }, i);
            ++i;
        }

        table.setPageLength(table.size());

        FormLayout form = buildForm();

        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();
        rootLayout.addComponent(table);
        rootLayout.addComponent(form);
        rootLayout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
        rootLayout.setComponentAlignment(form, Alignment.BOTTOM_RIGHT);
        setCompositionRoot(rootLayout);
    }

    private FormLayout buildForm() {
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

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {}
}
