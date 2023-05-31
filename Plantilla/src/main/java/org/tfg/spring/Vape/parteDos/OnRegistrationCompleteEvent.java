package org.tfg.spring.Vape.parteDos;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;
import org.tfg.spring.Vape.entities.User;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data


public class OnRegistrationCompleteEvent extends ApplicationEvent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
    private Locale locale;
    private User user;

    public OnRegistrationCompleteEvent(
      User user, Locale locale, String appUrl) {
        super(user);
        
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
    
    // standard getters and setters
}
