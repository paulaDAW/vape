package org.carlos.spring.Plantilla.services;

import java.time.LocalDate;
import java.util.List;
import org.carlos.spring.Plantilla.entities.Horario;
import org.carlos.spring.Plantilla.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioService {

	@Autowired
	private HorarioRepository horarioRepository;

	public List<Horario> getHorarios() {
		return horarioRepository.findAll();
	}

	public void saveHorario(String hora) throws Exception {
		Horario horario = Horario.builder().hora(hora).build();
		try {
			horarioRepository.saveAndFlush(horario);
		} catch (Exception e) {
			throw new Exception("La hora " + hora + " ya ha sido registrada."
					+ "\nInténtelo con otra");
		}
	}

	public Horario getHorarioById(Long id) {
		return horarioRepository.findById(id).get();
	}

	public void updateHorario(Long id, String hora) throws Exception {
		Horario horario = horarioRepository.findById(id).get();
		horario.setHora(hora);
		try {
			horarioRepository.saveAndFlush(horario);
		} catch (Exception e) {
			throw new Exception("Esta hora ( " + hora + " ) ya ha sido registrada");
		}
	}

	public void deleteHorario(Long id) {
		Horario horario = horarioRepository.findById(id).get();
		horarioRepository.delete(horario);
	}
}

