//=================================== EXPONER ===================================
const formExponer = document.getElementById("formExponer");
const nombreExponer = document.getElementById("idNombre");
const ape1Exponer = document.getElementById("idApellido1");
const ape2Exponer = document.getElementById("idApellido2");
//const dniExponer = document.getElementById("idDni");
const emailExponer = document.getElementById("idEmail");
const comentarioExponer = document.getElementById("idComentario");

console.log(formExponer)
formExponer.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsExponer();

})

const setError = elemento => {
	elemento.style.borderColor = "red";
}

const setSuccess = elemento => {
	elemento.style.borderColor = "blue";
};

const comprobarNombre = nombre => {
	const comprobacion = /^[A-ZÁÉÍÓÚ]{1}[a-záéíóú]{1,}$/;
	return comprobacion.test(nombre);
}

const comprobarApellido = apellido => {
	const comprobacion = /^[A-ZÁÉÍÓÚ]{1}[a-záéíóú]{1,}$/;
	return comprobacion.test(apellido);
}

//	EXPLOTA, REVISAR
/*const comprobarDni = dni => {
  var numero
  var letr
  var letra
  var expresion_regular_dni
 
  const comprobacion = /^\d{8}[A-Z]$/;
 
  if(comprobacion.test(dni) == true){
	 numero = dni.substr(0,dni.length-1);
	 letr = dni.substr(dni.length-1,1);
	 numero = numero % 23;
	 letra='TRWAGMYFPDXBNJZSQVHLCKET';
	 letra=letra.substring(numero,numero+1);
  }
  return true;
}

const comprobarDni = dni => {
	var boolean;
	
	while (!(/^\d{8}[a-zA-Z]$/.test(dni))) {
		boolean = false;
	}

	//Se separan los números de la letra
	var letraDNI = dni.substring(8, 9).toUpperCase();
	var numDNI = parseInt(dni.substring(0, 8));

	//Se calcula la letra correspondiente al número
	var letras = ['T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E', 'T'];
	var letraCorrecta = letras[numDNI % 23];

	if (letraDNI != letraCorrecta) {
		boolean = false;
	} else {
		boolean = true;

	}
	return boolean;
}
*/

const comprobarEmail = email => {
	const comprobacion = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return comprobacion.test(String(email).toLowerCase());
}

const comprobarComentario = comentario => {
	const comprobacion = /^[A-ZÁÉÍÓÚa-záéíóú]{1,}\.?\,?\s?$/;
	return comprobacion.test(String(comentario).toLowerCase());
}


const validarInputsExponer = () => {
	var validarExponer = 0;

	const nombreExponerValue = nombreExponer.value.trim();
	const ape1ExponerValue = ape1Exponer.value.trim();
	const ape2ExponerValue = ape2Exponer.value.trim();
	//const dniExponerValue = dniExponer.value.trim();
	const emailExponerValue = emailExponer.value.trim();
	const comentarioExponerValue = comentarioExponer.value.trim();

	//Nombre
	if (nombreExponerValue === '') {
		setError(nombreExponer);
	} else if (!comprobarNombre(nombreExponerValue)) {
		setError(nombreExponer);
	} else {
		setSuccess(nombreExponer);
		validarExponer += 1;
	}

	//Apellido1
	if (ape1ExponerValue === '') {
		setError(ape1Exponer);
	} else if (!comprobarApellido(ape1ExponerValue)) {
		setError(ape1Exponer);
	} else {
		setSuccess(ape1Exponer);
		validarExponer += 1;
	}

	//Apellido2
	if (ape2ExponerValue === '') {
		setError(ape2Exponer);
	} else if (!comprobarApellido(ape2ExponerValue)) {
		setError(ape2Exponer);
	} else {
		setSuccess(ape2Exponer);
		validarExponer += 1;
	}

	//DNI
/*	if (dniExponerValue === '') {
		setError(dniExponer);
	} else if (!comprobarDni(dniExponerValue)) {
		setError(dniExponer);
	} else {
		setSuccess(dniExponer);
		validarExponer += 1;
	}*/

	//Email
	if (emailExponerValue === '') {
		setError(emailExponer);
	} else if (!comprobarEmail(emailExponerValue)) {
		setError(emailExponer);
	} else {
		setSuccess(emailExponer);
		validarExponer += 1;
	}

	//Comentario
	if (comentarioExponerValue === '') {
		setError(comentarioExponer);
	} else if (!comprobarComentario(comentarioExponerValue)) {
		setError(comentarioExponer);
	} else {
		setSuccess(comentarioExponer);
		validarExponer += 1;
	}

	if (validarExponer == 5) {
		formExponer.submit();
	}
}