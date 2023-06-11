//=================================== LOGIN ===================================
const formLogin = document.getElementById("formLogin");
const emailLogin = document.getElementById("id-user2");
const passLogin = document.getElementById("id-pass2");


formLogin.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsLogin();

})

const setError = elemento => {
	elemento.style.borderColor = "red";
}

const setSuccess = elemento => {
	elemento.style.borderColor = "blue";
};


const comprobarEmail = email => {
	const comprobacion = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return comprobacion.test(String(email).toLowerCase());
}


const validarInputsLogin = () => {
	var validarLogin = 0;

	const emailLoginValue = emailLogin.value.trim();
	const passLoginValue = passLogin.value.trim();

	//Email
	if (emailLoginValue == '') {
		setError(emailLogin);
	} else {
		setSuccess(emailLogin);
		validarLogin += 1;
	}

	//Contraseña
	if (passLoginValue == '') {
		setError(passLogin);
	} else {
		setSuccess(passLogin);
		validarLogin += 1;
	}

	if (validarLogin == 2) {
		formLogin.submit();
	}
}