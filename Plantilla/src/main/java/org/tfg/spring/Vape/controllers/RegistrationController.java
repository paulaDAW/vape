package org.tfg.spring.Vape.controllers;

import org.tfg.spring.Vape.dto.UserDto;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.exception.DangerException;
//import org.tfg.spring.Vape.entities.User;
//import org.tfg.spring.Vape.entities.RegistrationController;
import org.tfg.spring.Vape.exception.UserAlreadyExistException;
import org.tfg.spring.Vape.helpers.PRG;
import org.tfg.spring.Vape.parteDos.OnRegistrationCompleteEvent;
import org.tfg.spring.Vape.parteDos.VerificationToken;
import org.tfg.spring.Vape.repositories.IUserService;
import org.tfg.spring.Vape.services.UserService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Calendar;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;

//import org.tfg.spring.Vape.services.RegistrationControllerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;


@Controller
public class RegistrationController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private IUserService service;
	
	@Autowired
    private MessageSource messages;

	@GetMapping("/user/registration")
	public String showRegistrationForm(WebRequest request, ModelMap model) {
	    UserDto userDto = new UserDto();
	    model.addAttribute("user", userDto);
	    model.addAttribute("view","/security/registration");
	    return "_t/frame";
	}
	
	
	/*
	@PostMapping("/user/registration")
	public String registerUserAccount(
	  @ModelAttribute("user") @Valid UserDto userDto,
	  HttpServletRequest request,
	  Errors errors,
	  ModelMap m) throws DangerException {
		User userRegister = new User();
	    try {
	         userRegister = userService.registerNewUserAccount(userDto);
	    } catch (UserAlreadyExistException uaeEx) {
	       PRG.error( "Esta cuenta de usuario ya ha sido registrada.","/");
	    }
	    m.put("user", userRegister);
	    m.put("view", "home/home");//Debe redirigir a la vista de espera de confirmación del mail
	    //Revisar la url cuando redirige, redirect quiza??
	    return "_t/frame";
	}
	*/
	

	@PostMapping("/user/registration")
	public String registerUserAccount(
	  @ModelAttribute("user") @Valid UserDto userDto, 
	  HttpServletRequest request, Errors errors, ModelMap m,
	  HttpSession s) throws DangerException { 
	    User registered = null;
	    User provisional = new User(userDto);
	    s.setAttribute("usuario", provisional);
	    try {
	    	//Si la pass coincide
	    	if(!userDto.getPassword().trim().toString().equals(userDto.getMatchingPassword().trim().toString())) {
				PRG.error("La contrasaña no coincide","/user/registration");
			}
	        registered = userService.registerNewUserAccount(userDto);
	        //System.out.println(registered.getMail());
	        String appUrl = request.getContextPath();
	        //System.out.println(appUrl);
	        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
	          request.getLocale(), appUrl));
	    } catch (UserAlreadyExistException uaeEx) {
	        /*ModelAndView mav = new ModelAndView("registration", "user", userDto);
	        mav.addObject("message", "An account for that username/email already exists.");
	        return mav;*/
	    	PRG.error("An account for that username/email already exists.","user/registration");
	    } catch (RuntimeException ex) {
	        PRG.error("No se, pues un error: "+ex.getMessage());
	    }
	    s.setAttribute("usuario", registered);
	    m.put("usuario", registered);
	    m.put("view", "/home/esperaConfirmar");
	    return "_t/frame";//esperaConfirmar
	}
	
	

	@GetMapping("/registroConfirmado")
	public String confirmRegistration
	  (WebRequest request, ModelMap m, @RequestParam("token") String token) throws Exception {
	 
	   // Locale locale = request.getLocale();
	    
	    VerificationToken verificationToken = service.getVerificationToken(token);
	    if (verificationToken == null) {
	    	PRG.error("Ha ocurrido un error con su registro. Inténtelo de nuevo.");
	    	/*
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	        */
	    }
	    
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	service.deleteUserToken(user, verificationToken);
	    	PRG.error("Ha pasado demasiado tiempo y el registro ha sido cancelado."
	    			+ "Intentelo de nuevo");
	    	
	    	/*
	    	String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	        */
	    } 
	    
	    user.setEnabled(true); 
	    service.saveRegisteredUser(user);
	    m.put("usuario", user);
	    m.put("view","/home/esperaConfirmar" );
	    return "_t/frame"; 
	}
	

}

