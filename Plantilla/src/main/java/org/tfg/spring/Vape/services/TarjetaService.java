package org.tfg.spring.Vape.services;

//Crear tarjeta
//Actualizar tarjeta
//Recuperar tarjeta por id
//Recuperar las tarjetas de un usuario con el id??


import org.tfg.spring.Vape.entities.Tarjeta;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.repositories.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaService {
	@Autowired
	private TarjetaRepository tarjetaRepository;
	
	@Autowired
	private UserService userService;
	
	public void saveTarjeta(Tarjeta tarjeta, Long idUsuario) throws Exception {
		User usuario = userService.getUsuarioById(idUsuario);
		tarjeta.setUsuario(usuario);
		try {
			tarjetaRepository.saveAndFlush(tarjeta);
		} catch (Exception e) {
			throw new Exception("La tarjeta: "+ tarjeta.getNumeroTarjeta()+" ya ha sido registrada");
		}
	}

	public Tarjeta getTarjetaById(Long id) {
		return tarjetaRepository.findById(id).get();
	}
	
	public void updateTarjeta(Long idTarjeta, Tarjeta tarjeta) throws Exception {
		Tarjeta tarjetaACambiar = tarjetaRepository.findById(idTarjeta).get();
		tarjetaACambiar.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
		tarjetaACambiar.setFechaCaducidad(tarjeta.getFechaCaducidad());
		tarjetaACambiar.setCvv(tarjeta.getCvv());
		tarjetaACambiar.setTitular(tarjeta.getTitular());
		try {
			tarjetaRepository.saveAndFlush(tarjetaACambiar);
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error al actualizar la tarjeta.");
		}
	}

	public void deleteTarjeta(Long id) {
		Tarjeta tarjeta = tarjetaRepository.findById(id).get();
		tarjetaRepository.delete(tarjeta);
	}
}



