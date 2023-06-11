//=================================== CREACION/ACTUALIZAR TIPO ENTRADA ===================================
const formTipo = document.getElementById("formTipo");
const nombreTipo = document.getElementById("id-Nombre");
const precioTipo = document.getElementById("id-Precio");

formTipo.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsTipo();

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

const comprobarPrecio = precio => {
	//REVISAR PARA QUE NO ACEPTE 0€ O 0.1,0.2...
	const comprobacion = /^\d*(\.\d{1})?\d{0,1}$/;
	return comprobacion.test(precio);
}

const validarInputsTipo = () => {
	var validarTipo = 0;

	const nombreTipoValue = nombreTipo.value.trim();
	const precioTipoValue = precioTipo.value;

	//Nombre
	if (nombreTipoValue === '') {
		setError(nombreTipo);
	} else if (!comprobarNombre(nombreTipoValue)) {
		setError(nombreTipo);
	} else {
		setSuccess(nombreTipo);
		validarTipo += 1;
	}

	//Precio
	if (precioTipoValue === '') {
		setError(precioTipo);
	} else if (!comprobarPrecio(precioTipoValue)) {
		setError(precioTipo);
	}else {
		setSuccess(precioTipo);
		validarTipo += 1;
	}
	
		
	if (validarTipo == 2) {
		formTipo.submit();
	}
}