package pl.edu.uj;


import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vaadin.spring.annotation.PrototypeScope;
import org.vaadin.spring.events.EventBus;
import pl.edu.uj.bo.User;
import pl.edu.uj.event.SuccessfulRegistrationEvent;
import pl.edu.uj.service.UserService;

@PrototypeScope
@SpringComponent
public class RegistrationScreen extends CustomComponent {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EventBus.SessionEventBus eventBus;

    private TextField usernameField;
    private TextField nameField;
    private TextField surnameField;
    private TextField emailField;
    private PasswordField passwordField;
    private Button register;

    @Autowired
    public RegistrationScreen(UserService userService, EventBus.SessionEventBus eventBus, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.eventBus = eventBus;
        this.passwordEncoder = passwordEncoder;
        initLayout();
    }

    private void initLayout() {
        FormLayout loginForm = new FormLayout();
        loginForm.setSizeUndefined();
        usernameField = new TextField("Username");
        nameField = new TextField("Name");
        surnameField = new TextField("Surname");
        emailField = new TextField("Email");
        passwordField = new PasswordField("Password");
        register = new Button("Register");
        loginForm.addComponent(usernameField);
        loginForm.addComponent(nameField);
        loginForm.addComponent(surnameField);
        loginForm.addComponent(emailField);
        loginForm.addComponent(passwordField);
        loginForm.addComponent(register);

        register.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        register.addClickListener(event -> register());

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setSizeUndefined();
        loginLayout.addComponent(loginForm);
        loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);
        VerticalLayout rootLayout = new VerticalLayout(loginLayout);
        rootLayout.setSizeFull();
        rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
        setCompositionRoot(rootLayout);
        setSizeFull();
    }

    private void register() {
        String username = usernameField.getValue();
        String name = nameField.getValue();
        String surname = surnameField.getValue();
        String email = emailField.getValue();
        String password = passwordField.getValue();

        User user = new User(username, name, surname, email, passwordEncoder.encode(password));
        user.setEnabled(false);
        userService.add(user);
        eventBus.publish(this, new SuccessfulRegistrationEvent(getUI()));
    }
}
