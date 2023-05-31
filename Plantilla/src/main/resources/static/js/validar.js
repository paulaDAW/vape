//=================================== REGISTRO ===================================
	var nombreRegValido;
	var apellidoRegValido;
	var tarjetaRegValido;
	var emailRegValido;
	var passRegValido;
	
	function comprobarRegistro(){
		var aceptar = document.getElementById("idAceptar");
		
		if (nombreRegValido && apellidoRegValido && tarjetaRegValido && emailRegValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
	//	NOMBRE
	function comprobarNombre(nombreReg){
		var comprobar = /^[A-ZÁÉÍÓÚ]{1}[a-záéíóú]{1,}$/
		
		if (!comprobar.test(nombreReg.value)){
			nombreRegValido = false;
		} else {
			nombreRegValido = true;
		}
		comprobarRegistro();
	}
	
	// APELLIDO(S)
	function comprobarApellido(apellidoReg){
		var comprobar = /^[A-ZÁÉÍÓÚ]{1}[a-záéíóú]{1,}$/;
		
		if (!comprobar.test(apellidoReg.value)){
			apellidoRegValido = false;
		} else {
			apellidoRegValido = true;
		}
		comprobarRegistro();
	}
	
	//	TARJETA
	function comprobarTarjeta(tarjetaReg){
		var comprobar = /^(\d{4}\s\d{4}\s\d{4}\s\d{4}|\d{16})$/;
		
		if (!comprobar.test(tarjetaReg.value)){
			tarjetaRegValido = false;
		} else {
			tarjetaRegValido = true;
		}
		comprobarRegistro();
	}
	
	//	EMAIL
	function comprobarEmailReg(emailReg){
		var comprobar = /^[^\s@]+@[^\s@]+$/;
		
		if (!comprobar.test(emailReg.value)){
			emailRegValido = false;
		} else {
			emailRegValido = true;
		}
		comprobarRegistro();
	}
	
	/* CONTRASEÑA (Comprobar Validacion)
	function comprobarPassword(passwordReg){
		var comprobar = /^(?=.[a-z])(?=.[A-Z])[A-Za-z]{8,}$/;
		
		if(!comprobar.test(passwordReg.value)){
			passRegValido = false;
			console.log("Mal");
		} else {
			passRegValido = true;
			console.log("Bien");
		}
		comprobarRegistro();
	}
	*/
//=======================================================================================

//=================================== LOGIN ===================================
	var emailLogValido;
	
	function comprobarLogin(){
		var aceptar = document.getElementById("idEntrar");
		
		if (emailLogValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
		//	EMAIL
	function comprobarEmailLog(emailLog){
		var comprobar = /^[^\s@]+@[^\s@]+$/;
		
		if (!comprobar.test(emailLog.value)){
			emailLogValido = false;
		} else {
			emailLogValido = true;
		}
		comprobarLogin();
	}
//=======================================================================================

//=================================== CREACION/ACTUALIZAR TIPO ENTRADA ===================================
	var nombreEntrValido;
	function comprobarTipoEntr(){
		var aceptar = document.getElementById("idAnadir");
		
		if (nombreEntrValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
	function comprobarNombreEntr(nombreEntr){
		var comprobar = /^[A-ZÁÉÍÓÚ]{1}[a-záéíóú]{1,}$/
		
		if (!comprobar.test(nombreEntr.value)){
			nombreEntrValido = false;
		} else {
			nombreEntrValido = true;
		}
		comprobarTipoEntr();
	}
//=======================================================================================

//=================================== CREACION/ACTUALIZAR ENTRADA ===================================
	var numeroValido;
	var numeroActValido;
	var numeroVenValido;
	
	function comprobarEntr(){
		var aceptar = document.getElementById("idAnadir");
		
		if (numeroValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
	function comprobarEntrAct(){
		var aceptar = document.getElementById("idAnadir");
		
		if (numeroActValido && numeroVenValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
	//	NUMERO ENTRADAS
	function comprobarNum(numero){
		var comprobar = /^\d{1,3}$/;
		
		if (!comprobar.test(numero.value)){
			numeroValido = false;
		} else {
			numeroValido = true;
		}
		comprobarEntr();
	}
	//	NUMERO ENTRADAS ACTUALIZAR
	function comprobarNumAct(numeroAct){
		var comprobar = /^\d{1,3}$/;
		
		if (!comprobar.test(numeroAct.value)){
			numeroActValido = false;
		} else {
			numeroActValido = true;
		}
		comprobarEntrAct();
	}
	//	NUMERO ENTRADAS VENDIDAS
	function comprobarNumVen(numeroVen){
		var comprobar = /^\d{1,3}$/;
		
		if (!comprobar.test(numeroVen.value)){
			numeroVenValido = false;
		} else {
			numeroVenValido = true;
		}
		comprobarEntrAct();
	}
//=================================== CREACION/ACTUALIZAR ROL ===================================
	var nombreRolValido;
	
	function comprobarRol(){
		var aceptar = document.getElementById("idAceptar");
		
		if (nombreRolValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
	//	NOMBRE
	function comprobarNombreRol(nombreRol){
		var comprobar = /^[A-ZÁÉÍÓÚa-záéíóú]{1,}$/
		
		if (!comprobar.test(nombreRol.value)){
			nombreRolValido = false;
		} else {
			nombreRolValido = true;
		}
		comprobarRol();
	}
//=======================================================================================

//=================================== COMPRA ===================================
	var numeroComValido;
	
	function comprobarCompra(){
		var aceptar = document.getElementById("idComprar");
		
		if (numeroComValido){
			aceptar.disabled = false;
		} else {
			aceptar.disabled = true;
		}
	}
	
	//	NUMERO ENTRADAS
	function comprobarNumCom(numeroCom){
		var comprobar = /^\d{1,3}$/;
		
		if (!comprobar.test(numeroCom.value)){
			numeroComValido = false;
		} else {
			numeroComValido = true;
		}
		comprobarCompra();
	}
//=======================================================================================