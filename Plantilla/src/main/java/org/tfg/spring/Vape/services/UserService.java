package org.tfg.spring.Vape.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tfg.spring.Vape.dto.UserDto;
import org.tfg.spring.Vape.entities.Rol;
import org.tfg.spring.Vape.entities.User;

import org.tfg.spring.Vape.exception.UserAlreadyExistException;
import org.tfg.spring.Vape.parteDos.VerificationToken;
import org.tfg.spring.Vape.parteDos.VerificationTokenRepository;
import org.tfg.spring.Vape.repositories.IUserService;
import org.tfg.spring.Vape.repositories.RolRepository;
import org.tfg.spring.Vape.repositories.UserRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public User registerNewUserAccount(UserDto userDto) 
      throws UserAlreadyExistException {
        
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException(
              "There is an account with that email adress: " 
              + userDto.getEmail());
        }
        
        User user = new User();
        user.setName(userDto.getNombre());
        user.setPrimerApellido(userDto.getApellido1());
        user.setSegundoApellido(userDto.getApellido2());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setMail(userDto.getEmail());

        
        Rol rol = rolRepository.findByNombre("Cliente");
		user.setRol(rol);
		
		 if(user.getRol() == null || rol.getId() != user.getRol().getId() ) {
			
			Rol nuevoRol = null;
			try {
				nuevoRol = rolService.saveRol("Cliente");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setRol(nuevoRol);
		}
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByMail(email) != null;
    }
    
    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    
    @Override
    public void saveRegisteredUser(User user) {
    	userRepository.save(user);
    }
    
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    @Autowired
	JavaMailSender javaMailSender;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private RolService rolService;
	
	@Value("${spring.mail.username}")
	private String email;

	
	public List<User> getUsuarios() {
		return userRepository.findAll();
	}
	
	/*
	public List<UsuarioDTO> getUsuarios() {
		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();
		for(Usuario usuario : usuarioRepository.findAll()) {
			usuariosDTO.add(new UsuarioDTO(usuario));
		}
		return usuariosDTO;
	}
	*/

	//public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) throws Exception {
	public User saveUsuario(UserDto usuario) throws Exception {
		/////Usuario usuario = Usuario.builder().nombre(nombre).apellidos(apellidos).fnac(fnac).email(email).loginName(loginName).password(password).build();
		//Usuario usuario = new Usuario(usuarioDTO);
		User user = new User();
        user.setName(usuario.getNombre());
        user.setPrimerApellido(usuario.getApellido1());
        user.setSegundoApellido(usuario.getApellido2());
        user.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        user.setMail(usuario.getEmail());

		
		/*
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		usuario.setActivado(false);	
		*/	
		
		Rol rol = rolRepository.findByNombre("Cliente");
		user.setRol(rol);
		
		 if(user.getRol() == null || rol.getId() != user.getRol().getId() ) {
			
			Rol nuevoRol = rolService.saveRol("Cliente");
			user.setRol(nuevoRol);
		}
		
		User userSaved = new User();
		try {
			userSaved = userRepository.saveAndFlush(user);
			//usuario = usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("El email " + usuario.getEmail() + " ya se ha sido registrado");
		}
		return userSaved;
	}

	public User getUsuarioById(Long id) {
		return userRepository.findById(id).get();
	}

	//public void updateUsuario(UsuarioDTO usuarioDTO) throws Exception {
	public User updateUsuario(
			Long idUsuario,
			String nombre,
			String apellido1,
			String apellido2,

			String email) throws Exception {
		User usuario = userRepository.findById(idUsuario).get();
		
		usuario.setName(nombre);
		usuario.setPrimerApellido(apellido1);
		usuario.setSegundoApellido(apellido2);
		usuario.setMail(email);

		//Usuario usuario = new Usuario(usuarioDTO);
			User userUpdated = new User();
		try {
			userUpdated = userRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error. Inténtelo de nuevo.");
		}
		return userUpdated;
	}

	public void deleteUsuario(Long id) {
		User usuario = userRepository.findById(id).get();
		userRepository.delete(usuario);
	}
	
	public User autenticarUsuario(String email, String password) throws Exception {
		User usuario = null;
		try {
			usuario = userRepository.findByMail(email);
			if(usuario == null) {
				throw new Exception("No esta registrado");
			}
		} catch (Exception e) {
			throw new Exception("El usuario " + email.split("@")[0] + " no existe.");
		}
		if(!(new BCryptPasswordEncoder()).matches(password, usuario.getPassword())) {
			throw new Exception("La contraseña no es correcta");
		}
		return usuario;
	}
	
	@Async
	public void confirmacionCompra(String emailTo) throws Exception {
		MimeMessage mensaje = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mensaje,true);
			
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Confirmación de compra de entrada/s");
			helper.setText("Su compra ha sido realizada con éxito.");
			javaMailSender.send(mensaje);
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error");
		}
	}
	
	public void emailExponer(String nombre, String apellido1, String apellido2, String dni, String emailTo) throws Exception {
		MimeMessage mensaje = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mensaje,true);
			
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Tus datos nos han llegado");
			helper.setText("Hola " + nombre + " " + apellido1 + ", acabamos de recibir tu datos, ¡pronto entraremos en contacto contigo!");
			javaMailSender.send(mensaje);
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error");
		}
	}
	
	/*
	public void envioConfirmarRegistro(String emailTo) throws Exception {
		MimeMessage mensaje = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mensaje,true);
			String texto = "Bienvenido al museo. Ve al siguiente enlace para confirmar tu registro.";
			String html = "<div class='container'>Bienvenido. Solo un pasito más. Accede al siguiente"
					+" enlace para confirmar tu registro <br>"
					+ "<form action='http://localhost:8080/confirmar/registro' method='post'>"+
					" <input type='submit' value='Confirmar Registro'/> </form></div>";
			helper.setFrom(email);
			helper.setTo(emailTo);
			helper.setSubject("Museo VAPE Confirmación de correo electrónico");
			helper.setText(texto, html);
			javaMailSender.send(mensaje);
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error al registrarte.");
		}
	}
	*/
	
	/*
	public User activarUsuario(String email) throws Exception {
		User usuario = userRepository.findByMail(email);
		//usuario.setActivado(true);
		User usuarioDevuelto = new User();
		try {
			usuarioDevuelto = userRepository.saveAndFlush(usuario);
		}catch (Exception e){
			throw new Exception("Este usuario no está registrado");
		}
		return usuarioDevuelto;
	}
	*/

	public User updatePass(Long id, String nuevaPass) throws Exception {
		// TODO Auto-generated method stub
		User usuario = userRepository.findById(id).get();
		usuario.setPassword(new BCryptPasswordEncoder().encode(nuevaPass));//Encriptar y enviar a BBDD
		User userPassSaved = new User();
		try {
			userPassSaved = userRepository.saveAndFlush(usuario);
		}catch(Exception e) {
			throw new Exception("Lo sentimos, no se pudo guardar tu nueva contraseña");
		}
		return userPassSaved;
	}

	@Override
	public void deleteUserToken(User user, VerificationToken token) throws Exception {
		try {
			tokenRepository.delete(token);
			userRepository.delete(user);
		}
		catch (Exception e) {
			throw new Exception("Error al borrar usuario");
		}
		
		
	}
}