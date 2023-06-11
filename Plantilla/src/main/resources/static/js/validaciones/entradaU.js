//					REVISAR
//=================================== CREACION/ACTUALIZAR ENTRADA ===================================
const formEntradaU = document.getElementById("formEntradaU");
const numeroMaxEntradaU = document.getElementById("id-numeroMax");
const numeroVenEntradaU = document.getElementById("id-numeroVen");
const fechaEntradaU = document.getElementById("id-fecha");


formEntradaU.addEventListener('submit', e => {
	e.preventDefault();

	validarInputsEntradaU();

})

const setError = elemento => {
	elemento.style.borderColor = "red";
}

const setSuccess = elemento => {
	elemento.style.borderColor = "blue";
};

const comprobarNumeroMax = numeroMax => {
	const comprobacion = /^\d{1,3}$/;
	return comprobacion.test(numeroMax);
}

const validarInputsEntradaU = () => {
	var validarEntradaU = 0;

	const numeroMaxEntradaUValue = numeroMaxEntradaU.value;
	const numeroVenEntradaUValue = numeroVenEntradaU.value;
	const fechaEntradaUValue = fechaEntradaU.value;

	//Número Máximo U
	if (numeroMaxEntradaUValue == '') {
		setError(numeroMaxEntradaU);
		console.log(numeroMaxEntradaUValue);
	} else if (!comprobarNumeroMax(numeroMaxEntradaUValue)) {
		setError(numeroMaxEntradaU);
		console.log(numeroMaxEntradaUValue);
	} else {
		setSuccess(numeroMaxEntradaU);
		validarEntradaU += 1;
	}
	//Número Vendidas U
	if (numeroVenEntradaUValue == '') {
		setError(numeroVenEntradaU);
		console.log(numeroVenEntradaUValue);
	} else if (!comprobarNumeroMax(numeroVenEntradaUValue)) {
		setError(numeroVenEntradaU);
		console.log(numeroVenEntradaUValue);
	} else {
		setSuccess(numeroVenEntradaU);
		validarEntradaU += 1;
	}
	//Fecha U
	if (fechaEntradaUValue == '') {
		setError(fechaEntradaU);
	} else {
		setSuccess(fechaEntradaU);
		validarEntradaU += 1;
	}

	if (validarEntradaU == 3) {
		formEntradaU.submit();
	}
}