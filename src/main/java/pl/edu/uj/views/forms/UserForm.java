package pl.edu.uj.views.forms;

import com.vaadin.ui.Component;
import org.vaadin.viritin.fields.EmailField;
import org.vaadin.viritin.fields.MCheckBox;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.User;

public class UserForm extends AbstractForm<User> {
    private MTextField name = new MTextField("Name");
    private MTextField surname = new MTextField("Surname");
    private MTextField username = new MTextField("username");
    private MPasswordField password = new MPasswordField("Password");
    private EmailField email = new EmailField("Email");
    private MCheckBox enabled = new MCheckBox("Enabled");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(name, surname, username, password, email, enabled, getToolbar());
    }
}
