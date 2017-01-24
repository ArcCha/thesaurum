package pl.edu.uj.operations;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import pl.edu.uj.Sections;
import pl.edu.uj.bo.Application;
import pl.edu.uj.bo.GradingRound;
import pl.edu.uj.dao.ApplicationDao;
import pl.edu.uj.dao.GradingRoundDao;
import pl.edu.uj.views.forms.GradingRoundForm;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = "Schedule grading round", order = 1)
@FontAwesomeIcon(FontAwesome.CLOCK_O)
public class ScheduleGradingRound implements Runnable {
    private static final Logger log = Logger.getLogger(ScheduleGradingRound.class.getSimpleName());
    @Autowired
    private GradingRoundDao gradingRoundDao;
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public void run() {
        List<Application> submitted = applicationDao.getSubmitted();
        GradingRound gradingRound = new GradingRound();
        GradingRoundForm form = new GradingRoundForm(submitted);
        form.setEntity(gradingRound);
        form.setSavedHandler(round -> {
            Set<Application> scheduled = (Set<Application>) form.getScheduledApplications().getValue();
            for (Application app : scheduled) {
                applicationDao.schedule(app);
            }
            form.getEntity().setApplications(scheduled);
            form.closePopup();
        });
        form.openInModalPopup();
    }
}
