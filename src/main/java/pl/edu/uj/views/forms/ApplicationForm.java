package pl.edu.uj.views.forms;

import com.vaadin.ui.Component;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.fields.MDateField;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.Application;

public class ApplicationForm extends AbstractForm<Application> {
    private MTextField name = new MTextField("Name");
    private MTextArea localization = new MTextArea("Localization");
    private MDateField beginDate = new MDateField("Begin date");
    private MDateField endDate = new MDateField("End date");
    private MTextArea description = new MTextArea("Description");
    private MTextArea justification = new MTextArea("Justification");
    private MTextArea actionPlan = new MTextArea("Action plan");

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                name,
                localization,
                beginDate,
                endDate,
                description,
                justification,
                actionPlan,
                getToolbar())
                .withSize(MSize.FULL_SIZE);
    }
}
