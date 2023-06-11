//=================================== REGISTRO ===================================
const formRegistro = document.getElementById("formPass");
const passRegistro = document.getElementById("idPass");
const passConfRegistro = document.getElementById("idPassN");


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

const validarInputsRegistro = () => {
	var validarRegistro = 0;

	const passRegistroValue = passRegistro.value.trim();
	const passConfRegistroValue = passConfRegistro.value.trim();


	//Contraseña
	if (passRegistroValue === '') {
		setError(passRegistro);
	} else if (passRegistroValue.length < 8) {
		setError(passRegistro)
	} else {
		setSuccess(passRegistro);
		validarRegistro += 1;
	}

	//Confirmar Contraseña
	if (passConfRegistroValue === '') {
		setError(passConfRegistro);
	} else if (passConfRegistroValue !== passRegistroValue) {
        setError(passConfRegistro);
    } else {
		setSuccess(passConfRegistro);
		validarRegistro += 1;
	}
	
		
	if (validarRegistro == 2) {
		formRegistro.submit();
	}
}

//=======================================================================================

//=================================== LOGIN ===================================
	//¿Poner valdiación?

//=======================================================================================


//=================================== CREACION/ACTUALIZAR TIPO ENTRADA ===================================
//=======================================================================================



//=================================== CREACION/ACTUALIZAR ROL ===================================
//=======================================================================================

//=================================== COMPRA ===================================
//=======================================================================================