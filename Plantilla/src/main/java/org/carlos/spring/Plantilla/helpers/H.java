package org.carlos.spring.Plantilla.helpers;

//import org.carlos.spring.Plantilla.entities.Rol;
import org.carlos.spring.Plantilla.entities.Usuario;

public class H {
	public static void isRolOk(String nombreR, Usuario usuario) throws Exception {
		if(usuario == null || usuario.getRol().getNombre() == "" || usuario.getRol().getNombre() == null) {
			throw new Exception("Debe de estar registrado para realizar esta acción");
		}
		if(!usuario.getRol().getNombre().equals((nombreR))) {
			throw new Exception("Rol inadecuado, carece de los permisos necesarios para realizar esta acción.");
		//, su rol actual: " + usuario.getRol().getNombre()+"
		}
	}
	/*Puede que sea necesario añadir si el usuario a confirmado el registro o no*/
	public static void isLogged(Usuario usuario) throws Exception {
		if(usuario == null || usuario.getRol().getNombre() == "" || usuario.getRol().getNombre() == null) {
			throw new Exception("Debe de estar registrado para realizar esta acción");
		}
	}
}
