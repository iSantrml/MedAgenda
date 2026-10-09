package controllers;

import models.Atendente;
import models.Cliente;
import models.Medico;
import models.Perfil;
import play.jobs.Job;
import play.jobs.OnApplicationStart;


@OnApplicationStart
public class Inicializador extends Job {

    @Override
    public void doJob() throws Exception {
        if (Atendente.count() == 0) {
            Atendente admin = new Atendente();
            admin.nome = "Administrador";
            admin.login = "admin@clinica.com";
            admin.senha = "123";
            admin.perfil = Perfil.ADMIN;
            admin.matricula = "001";
            admin.save();
        }

        if (Medico.count() == 0) {
            Medico m1 = new Medico();
            m1.nome = "Dr. Pedro Henrique";
            m1.senha = "123";
            m1.perfil = Perfil.MEDICO;
            m1.especialidade = "Nutricionista";
            m1.crm = "1111-RN";
            m1.save();

            Medico m2 = new Medico();
            m2.nome = "Dr. Rafael Ferreira";
            m2.senha = "123";
            m2.perfil = Perfil.MEDICO;
            m2.especialidade = "Cardiologista";
            m2.crm = "1112-RN";
            m2.save();

            Medico m3 = new Medico();
            m3.nome = "Dr. Maria Eduarda";
            m3.senha = "123";
            m3.perfil = Perfil.MEDICO;
            m3.especialidade = "Oftamologista";
            m3.crm = "1113-RN";
            m3.save();

            Medico m4 = new Medico();
            m4.nome = "Dr. Pedro Otavio";
            m4.senha = "123";
            m4.perfil = Perfil.MEDICO;
            m4.especialidade = "Pediatra";
            m4.crm = "1114-RN";
            m4.save();
        }

        if (Cliente.count() == 0) {
            Cliente c1 = new Cliente();
            c1.nome = "João da Silva";
            c1.senha = "123";
            c1.perfil = Perfil.CLIENTE;
            c1.cpf = "000.000.000-00";
            c1.telefone = "(84) 9999-9999";
            c1.save();
        }
    }
}
