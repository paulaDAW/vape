//=================================== PERFIL ===================================
const formRegistro = document.getElementById("formPerfil");
const nombreRegistro = document.getElementById("idNombre");
const ape1Registro = document.getElementById("idApellido1");
const ape2Registro = document.getElementById("idApellido2");
const emailRegistro = document.getElementById("idEmail");


formRegistro.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsRegistro();

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

const comprobarEmail = email => {
	const comprobacion = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return comprobacion.test(String(email).toLowerCase());
}


const validarInputsRegistro = () => {
	var validarRegistro = 0;

	const nombreRegistroValue = nombreRegistro.value.trim();
	const ape1RegistroValue = ape1Registro.value.trim();
	const ape2RegistroValue = ape2Registro.value.trim();
	const emailRegistroValue = emailRegistro.value.trim();

	//Nombre
	if (nombreRegistroValue === '') {
		setError(nombreRegistro);
	} else if (!comprobarNombre(nombreRegistroValue)) {
		setError(nombreRegistro);
	} else {
		setSuccess(nombreRegistro);
		validarRegistro += 1;
	}

	//Apellido1
	if (ape1RegistroValue === '') {
		setError(ape1Registro);
	} else if (!comprobarApellido(ape1RegistroValue)) {
		setError(ape1Registro);
	}else {
		setSuccess(ape1Registro);
		validarRegistro += 1;
	}

	//Apellido2
	if (!comprobarApellido(ape2RegistroValue)) {
		setError(ape2Registro);
	}else {
		setSuccess(ape2Registro);
		validarRegistro += 1;
	}

	//Email
	if (emailRegistroValue === '') {
		setError(emailRegistro);
	} else if (!comprobarEmail(emailRegistroValue)) {
		setError(emailRegistro);
	} else {
		setSuccess(emailRegistro);
		validarRegistro += 1;
	}

		
	if (validarRegistro == 4) {
		formRegistro.submit();
	}
}