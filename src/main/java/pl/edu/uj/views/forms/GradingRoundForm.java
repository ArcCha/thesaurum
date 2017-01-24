package pl.edu.uj.views.forms;


import com.vaadin.ui.Component;
import com.vaadin.ui.TwinColSelect;
import org.vaadin.viritin.fields.MDateField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.Application;
import pl.edu.uj.bo.GradingRound;

import java.util.List;
import java.util.logging.Logger;

public class GradingRoundForm extends AbstractForm<GradingRound> {
    private static final Logger log = Logger.getLogger(GradingRoundForm.class.getSimpleName());
    private MDateField deadline = new MDateField();
    private TwinColSelect scheduledApplications = new TwinColSelect();

    private List<Application> applications;

    public GradingRoundForm(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    protected Component createContent() {
        scheduledApplications.addItems(applications);
        scheduledApplications.setRows(scheduledApplications.size());
        MVerticalLayout layout = new MVerticalLayout()
                .with(deadline, scheduledApplications, getToolbar());
        return layout;
    }

    public TwinColSelect getScheduledApplications() {
        return scheduledApplications;
    }
}
