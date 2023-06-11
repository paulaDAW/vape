	$(document).ready(function(){
		$('#confirUno').on("click", function(){
			if($("#iUno").val().length > 0){
				//$('.formularioCompra').css("height","+=200");
				$("#iDos").animate({
					top: "+=100",
					opacity: 1
				}, 1500);
				$('#iUno').prop('readonly',true);
				$('#confirUno').prop('disabled',true);
				$('#cancelarUno').prop('disabled',true);
			}else{
				$("#iDos").animate({
					top: 0,
					opacity: 0
				}, 1500);
				
			}
		});
		$('#cancelarUno').on("click", function(){
			document.getElementById("iUno").value = "";
			$("#iDos").animate({
					top: 0,
					opacity: 0
				}, 1500);
		});
		
		$('#confirDos').on("click", function(){
			if($("#iSelect option:selected").text() != '' && $("#iSelect option:selected").text() != null && $("#iSelect option:selected").text() != 'null'){
				$("#iTres").animate({
					top: "+=100",
					opacity: 1
				}, 1500);
				$('#iSelect').prop('readonly',true);
				$('#confirDos').prop('disabled',true);
				$('#cancelarDos').prop('disabled',true);
			}else{
				$("#iTres").animate({
					top: 0,
					opacity: 0
				}, 1500);
			}
		});

		$('#cancelarDos').on("click", function(){
			document.getElementById("iDos").value = "";
			$("#iDos").animate({
					top: 0,
					opacity: 0
				}, 1500)
			$("#iTres").animate({
					top: 0,
					opacity: 0
				}, 1500);
			$('#iUno').prop('readonly',false);
			$('#confirUno').prop('disabled',false);
			$('#cancelarUno').prop('disabled',false);
		});

		$('#cancelarTres').on("click", function(){
			document.getElementById("iTres").value = "";
			$("#iTres").animate({
					top: 0,
					opacity: 0
				}, 1500);
			$('#iSelect').prop('readonly',false);
			$('#confirDos').prop('disabled',false);
			$('#cancelarDos').prop('disabled',false);
		});
		
	});

function comprobarValores(){
	var cantidad = document.getElementById("iUno").value;
	var tipo = document.getElementById("iSelect").value;
	console.log("Tipo: "+tipo);
	var fecha = document.getElementById("datepicker").value;
	
	if((cantidad > 0 || cantidad != null) && (tipo != null || tipo != '') ){
		document.getElementById("idBotonEnvio").style.display = "block";
	}
	
	
}
/*
window.onload = function(){
		document.getElementById("iSelect").onchange = comprobarEntradaSelect(this);
	}

function comprobarEntradaSelect(opcion){
	if(option != null ||)
}
*/
