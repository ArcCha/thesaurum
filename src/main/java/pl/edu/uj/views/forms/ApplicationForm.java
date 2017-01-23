package pl.edu.uj.views.forms;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MDateField;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MHorizontalLayout;
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

    private MButton submitBtn = new MButton("Submit")
            .withListener(click -> {
                getEntity().setState(Application.State.SUBMITTED);
                closePopup();
            });

    @Override
    protected Component createContent() {
        if (getEntity().getState() != Application.State.NEW) {
            getSaveButton().setEnabled(false);
            submitBtn.setCaption("Submitted");
            submitBtn.setEnabled(false);
        }

        HorizontalLayout toolbar = getToolbar();
        toolbar.addComponentAsFirst(submitBtn);
        return new MVerticalLayout(
                name,
                localization,
                new MHorizontalLayout()
                    .with(beginDate, endDate),
                description,
                justification,
                actionPlan,
                toolbar)
                .withSize(MSize.FULL_SIZE);
    }
}
