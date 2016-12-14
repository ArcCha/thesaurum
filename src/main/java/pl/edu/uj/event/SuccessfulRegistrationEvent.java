package pl.edu.uj.event;

import org.springframework.context.ApplicationEvent;

public class SuccessfulRegistrationEvent extends ApplicationEvent {
    public SuccessfulRegistrationEvent(Object source) {
        super(source);
    }
}
