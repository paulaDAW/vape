//					REVISAR
//=================================== CREACION/ACTUALIZAR ENTRADA ===================================
const formEntradaC = document.getElementById("formEntradaC");
const numeroMaxEntradaC = document.getElementById("id-numeroMax");
const fechaEntradaC = document.getElementById("id-fecha");

console.log(formEntradaC);

formEntradaC.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsEntradaC();

})

const setError = elemento => {
	elemento.style.borderColor = "red";
}

const setSuccess = elemento => {
	elemento.style.borderColor = "blue";
};

const comprobarNumeroMax = numeroMax => {
	const comprobacion = /^\d{1,3}$/;
	console.log("Comprpbar numero")
	return comprobacion.test(numeroMax);
}

const validarInputsEntradaC = () => {
	var validarEntradaC = 0;

	const numeroMaxEntradaCValue = numeroMaxEntradaC.value;
	const fechaEntradaCValue = fechaEntradaC.value;

	//Número Máximo C
	if (numeroMaxEntradaCValue == '') {
		setError(numeroMaxEntradaC);
		console.log(numeroMaxEntradaCValue);
	} else if (!comprobarNumeroMax(numeroMaxEntradaCValue)) {
		setError(numeroMaxEntradaC);
		console.log(numeroMaxEntradaCValue);
	} else {
		setSuccess(numeroMaxEntradaC);
		validarEntradaC += 1;
	}
	//Fecha C
	if (fechaEntradaCValue == '') {
		setError(fechaEntradaC);
	} else {
		setSuccess(fechaEntradaC);
		validarEntradaC += 1;
	}

	if (validarEntradaC == 2) {
		formEntradaC.submit();
	}
}