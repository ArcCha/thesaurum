package pl.edu.uj.views.forms;

import com.vaadin.ui.Component;
import org.vaadin.viritin.fields.MDateField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.Budget;

public class BudgetForm extends AbstractForm<Budget> {
    private MTextField name = new MTextField("Name");
    private MDateField startDate = new MDateField("Start");
    private MDateField endDate = new MDateField("End");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(name, startDate, endDate, getToolbar());
    }
}
