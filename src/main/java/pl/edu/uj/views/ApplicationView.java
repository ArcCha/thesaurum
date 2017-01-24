package pl.edu.uj.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.Application;
import pl.edu.uj.dao.ApplicationDao;
import pl.edu.uj.service.StorageService;
import pl.edu.uj.views.forms.ApplicationForm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@SpringView(name = "application")
public class ApplicationView extends CustomComponent implements View {
    private static final Logger log = Logger.getLogger(ApplicationView.class.getSimpleName());
    private ApplicationDao applicationDao;
    private StorageService storageService;
    private MVerticalLayout filesLayout;

    @Autowired
    public ApplicationView(ApplicationDao applicationDao, StorageService storageService) {
        this.applicationDao = applicationDao;
        this.storageService = storageService;
    }

    // TODO add state, coordinator and budget pool
    private void init(Application app) {
        MLabel title = new MLabel(app.getName())
                .withStyleName(ValoTheme.LABEL_H1);

        MButton submitBtn = new MButton()
                .withCaption("Submit")
                .withListener(event -> {
                    applicationDao.submit(app);
                    refresh(app.getId());
                });
        MButton editBtn = new MButton()
                .withCaption("Edit")
                .withListener(event -> {
                    ApplicationForm form = new ApplicationForm();
                    form.setEntity(app);
                    form.setSavedHandler(editedApp -> {
                        applicationDao.update(editedApp);
                        form.closePopup();
                        refresh(editedApp.getId());
                    });
                    form.openInModalPopup();
                });
        if (app.getState() != Application.State.NEW) {
            submitBtn.setEnabled(false);
            editBtn.setEnabled(false);
        }

        MHorizontalLayout buttonbar = new MHorizontalLayout()
                .with(editBtn, submitBtn);
        MHorizontalLayout titlebar = new MHorizontalLayout()
                .withFullWidth()
                .with(title, buttonbar)
                .withAlign(buttonbar, Alignment.MIDDLE_RIGHT);

        MLabel state = new MLabel()
                .withContent(app.getState().toString())
                .withStyleName(ValoTheme.LABEL_LIGHT);

        MLabel space = new MLabel()
                .withContent(app.getLocalization())
                .withStyleName(ValoTheme.LABEL_H3);
        String timeStr = app.getBeginDate().toString() + " - " + app.getEndDate().toString();
        MLabel time = new MLabel()
                .withContent(timeStr)
                .withStyleName(ValoTheme.LABEL_H3);
        MHorizontalLayout spacetimeLayout = new MHorizontalLayout()
                .with(space, time);

        MLabel description = new MLabel()
                .withContentMode(ContentMode.HTML)
                .withContent("<p>" + app.getDescription() + "</p>");
        MLabel justification = new MLabel()
                .withContentMode(ContentMode.HTML)
                .withContent("<p>" + app.getJustification() + "</p>");
        MLabel actionPlan = new MLabel()
                .withContentMode(ContentMode.HTML)
                .withContent("<p>" + app.getActionPlan() + "</p>");

        filesLayout = new MVerticalLayout();
        for (MHorizontalLayout entry : getFileList(app)) {
            filesLayout.add(entry);
        }

        UploadField uploadField = new UploadField();
        uploadField.setFieldType(UploadField.FieldType.FILE);
        uploadField.setStorageMode(UploadField.StorageMode.FILE);
        uploadField.addValueChangeListener(event -> {
            File file = (File) uploadField.getValue();
            String fileName = uploadField.getLastFileName();
            storageService.save(app.getName(), file, fileName); //TODO name is not unique currently
            filesLayout.removeAllComponents();
            for (MHorizontalLayout entry : getFileList(app)) {
                filesLayout.add(entry);
            }
        });

        MVerticalLayout rootLayout = new MVerticalLayout()
                .withSize(MSize.FULL_SIZE)
                .with(
                        titlebar,
                        state,
                        spacetimeLayout,
                        description, justification, actionPlan,
                        filesLayout);
        if (app.getState() == Application.State.NEW) {
            rootLayout.add(uploadField);
        }
        setCompositionRoot(rootLayout);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() != null) {
            Integer id = Integer.valueOf(event.getParameters());
            Optional<Application> app = applicationDao.getById(id);
            if (app.isPresent()) {
                init(app.get());
            } else {
                getUI().getNavigator().navigateTo(""); // TODO
            }
        }
    }

    // TODO ugly
    private void refresh(Integer id) {
        getUI().getNavigator().navigateTo("application/" + id.toString());
    }

    private List<MHorizontalLayout> getFileList(Application app) {
        //TODO transform to stream
        List<MHorizontalLayout> files = new ArrayList<>();
        for (String fileName : storageService.filesFor(app.getName())) { //TODO same as below
            MLabel fileLabel = new MLabel()
                    .withContent(fileName);
            MButton deleteBtn = new MButton()
                    .withCaption("Delete")
                    .withStyleName(ValoTheme.BUTTON_DANGER)
                    .withListener(event -> {
                        storageService.delete(app.getName(), fileName);
                        filesLayout.removeAllComponents();
                        for (MHorizontalLayout entry : getFileList(app)) {
                            filesLayout.add(entry);
                        }
                    });
            MHorizontalLayout tmp = new MHorizontalLayout()
                    .with(fileLabel);
            if (app.getState() == Application.State.NEW) {
                tmp.add(deleteBtn);
            }
            files.add(tmp);
        }
        return files;
    }
}
