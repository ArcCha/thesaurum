package pl.edu.uj;


import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.MSize;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.EmailField;
import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import pl.edu.uj.bo.User;
import pl.edu.uj.dao.UserDao;
import pl.edu.uj.event.SuccessfulRegistrationEvent;

@PrototypeScope
@SpringComponent
public class RegistrationScreen extends CustomComponent {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final EventBus.SessionEventBus eventBus;

    private MTextField usernameField;
    private MTextField nameField;
    private MTextField surnameField;
    private EmailField emailField;
    private MPasswordField passwordField;
    private MButton register;

    @Autowired
    public RegistrationScreen(UserDao userDao,
                              EventBus.SessionEventBus eventBus,
                              PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.eventBus = eventBus;
        this.passwordEncoder = passwordEncoder;
        initLayout();
    }

    private void initLayout() {
        usernameField = new MTextField("Username");
        nameField = new MTextField("Name");
        surnameField = new MTextField("Surname");
        emailField = new EmailField("Email");
        passwordField = new MPasswordField("Password");
        register = new MButton("Register")
                .withStyleName(ValoTheme.BUTTON_FRIENDLY)
                .withClickShortcut(ShortcutAction.KeyCode.ENTER)
                .withListener(event -> register());

        MFormLayout registerForm = new MFormLayout()
                .withSizeUndefined()
                .with(usernameField, nameField, surnameField,
                        emailField, passwordField, register);

        MVerticalLayout registerLayout = new MVerticalLayout()
                .withSizeUndefined()
                .with(registerForm);

        MVerticalLayout rootLayout = new MVerticalLayout()
                .withSize(MSize.FULL_SIZE)
                .with(registerLayout)
                .withAlign(registerLayout, Alignment.MIDDLE_CENTER);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }

    private void register() {
        User user = new User(usernameField.getValue(),
                nameField.getValue(),
                surnameField.getValue(),
                emailField.getValue(),
                passwordEncoder.encode(passwordField.getValue()));
        userDao.insert(user);
        eventBus.publish(this, new SuccessfulRegistrationEvent(getUI()));
    }
}
