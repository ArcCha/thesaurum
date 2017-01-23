package pl.edu.uj;

import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.security.util.SuccessfulLoginEvent;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@PrototypeScope
@SpringComponent
public class LoginScreen extends CustomComponent {

    private final VaadinSecurity vaadinSecurity;
    private final EventBus.SessionEventBus eventBus;

    private MTextField usernameField;
    private MPasswordField passwordField;
    private MButton loginBtn;
    private MLabel loginFailedLabel;
    private MLabel loggedOutLabel;
    private MButton registerBtn;

    @Autowired
    public LoginScreen(VaadinSecurity vaadinSecurity, EventBus.SessionEventBus eventBus) {
        this.vaadinSecurity = vaadinSecurity;
        this.eventBus = eventBus;
        initLayout();
    }

    public void setLoggedOut(boolean loggedOut) {
        loggedOutLabel.setVisible(loggedOut);
    }

    private void initLayout() {
        Header header = new Header("Thesaurum").setHeaderLevel(1);

        MFormLayout loginForm = initFormLayout();
        loginFailedLabel = new MLabel()
                .withStyleName(ValoTheme.LABEL_FAILURE)
                .withVisible(false);
        loginFailedLabel.setSizeUndefined();
        loggedOutLabel = new MLabel()
                .withStyleName(ValoTheme.LABEL_SUCCESS)
                .withVisible(false);
        loggedOutLabel.setSizeUndefined();

        MVerticalLayout loginLayout = new MVerticalLayout()
                .withSizeUndefined()
                .with(header, loginFailedLabel, loggedOutLabel, loginForm);
        MVerticalLayout rootLayout = new MVerticalLayout()
                .withSize(MSize.FULL_SIZE)
                .with(loginLayout)
                .withAlign(loginLayout,  Alignment.MIDDLE_CENTER);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }

    private MFormLayout initFormLayout() {
        usernameField = new MTextField("Username").withValue("admin");
        passwordField = new MPasswordField("Password");
        passwordField.setValue("admin");
        loginBtn = new MButton("Login")
                .withStyleName(ValoTheme.BUTTON_PRIMARY)
                .withClickShortcut(ShortcutAction.KeyCode.ENTER)
                .withListener(event -> login());
        loginBtn.setDisableOnClick(true);
        registerBtn = new MButton("Register")
                .withStyleName(ValoTheme.BUTTON_FRIENDLY)
                .withListener(event -> ((ThesaurumUI) getUI()).showRegistrationScreen());
        registerBtn.setDisableOnClick(true);
        return new MFormLayout()
                .withSizeUndefined()
                .with(usernameField, passwordField,
                        new MHorizontalLayout().withSizeUndefined().with(registerBtn, loginBtn));
    }

    private void login() {
        try {
            loggedOutLabel.setVisible(false);
            String password = passwordField.getValue();
            passwordField.setValue("");
            final Authentication authentication = vaadinSecurity.login(usernameField.getValue(), password);
            eventBus.publish(this, new SuccessfulLoginEvent(getUI(), authentication));
        } catch (AuthenticationException ex) {
            usernameField.focus();
            usernameField.selectAll();
            loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
            loginFailedLabel.setVisible(true);
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred",
                    ex.getMessage(),
                    Notification.Type.ERROR_MESSAGE);
            LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
        } finally {
            loginBtn.setEnabled(true);
        }
    }
}
