package controllers;

import models.Medico;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

	@Override
	public void doJob() throws Exception {

		if (Medico.count() != 0)
		return; {

			Medico m1 = new Medico();
			m1.nome = "Dr. Pedro Henrique";
			m1.especialidade = "Nutricionista";
			m1.crm = "1111-RN";
			m1.save();
			
			Medico m2 = new Medico();
			m2.nome = "Dr. Rafael Ferreira";
			m2.especialidade = "Cardiologista";
			m2.crm = "1112-RN";
			m2.save();
			
			Medico m3 = new Medico();
			m3.nome = "Dr. Maria Eduarda";
			m3.especialidade = "Oftamologista";
			m3.crm = "1113-RN";
			m3.save();
			
			Medico m4 = new Medico();
			m4.nome = "Dr. Pedro Otavio";
			m4.especialidade = "Pediatra";
			m4.crm = "1114-RN";
			m4.save();

		}
	}
}