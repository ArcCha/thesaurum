package pl.edu.uj.views.forms;

import com.vaadin.ui.Component;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.BudgetPool;

public class BudgetPoolForm extends AbstractForm<BudgetPool> {
    private MTextField name = new MTextField("Name");
    private IntegerField value = new IntegerField("Value");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(name, value, getToolbar());
    }
}
