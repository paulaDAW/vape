//=================================== CREACION ===================================
const formRol = document.getElementById("formRol");
const nombreRol = document.getElementById("id-Nombre");

console.log(formRol);

formRol.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsRol();

})


const setError = elemento => {
	elemento.style.borderColor = "red";
}

const setSuccess = elemento => {
	elemento.style.borderColor = "blue";
};

const comprobarNombre = nombre => {
	const comprobacion = /^[A-ZÁÉÍÓÚa-záéíóú]{1,}$/;
	return comprobacion.test(nombre);
}


const validarInputsRol = () => {
	var validarRol = 0;

	const nombreRolValue = nombreRol.value.trim();
	
	//Nombre
	if (nombreRolValue === '') {
		setError(nombreRol);
	} else if (!comprobarNombre(nombreRolValue)) {
		setError(nombreRol);
	} else {
		setSuccess(nombreRol);
		validarRol += 1;
	}
		
	if (validarRol == 1) {
		formRol.submit();
	}
}
