package controllers;

import java.util.List;
import models.*;
import play.mvc.Controller;
import play.mvc.With;
import security.Seguranca;
import security.RestritoA;
import play.data.validation.Valid;

@With(Seguranca.class)
public class Consultas extends Controller {

    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void cadastro() { 
        List<Medico> medicos = Medico.findAll(); 
        render(medicos);
    }

    public static void listar(String busca) {
        String perfil = session.get("perfilUsuario");
        Long usuarioId = Long.parseLong(session.get("usuarioLogado"));
        boolean mostrarTodas = params.get("mostrarTodas") != null;
        List<Consulta> consultas = null; 
        
        if (perfil.equals(Perfil.CLIENTE.name())) {
            consultas = Consulta.find("status != ?1 and cliente.id = ?2", Status.CANCELADA, usuarioId).fetch();
        } else if (perfil.equals(Perfil.MEDICO.name())) {
            if (mostrarTodas) {
                consultas = Consulta.find("medico.id = ?1", usuarioId).fetch();
            } else {
                consultas = Consulta.find("status = ?1 and medico.id = ?2", Status.AGENDADA, usuarioId).fetch();
            }
        } else {
            String termo = busca == null ? "" : busca.toLowerCase();
            if (mostrarTodas) {
                consultas = Consulta.find("lower(cliente.nome) like ?1", "%"+termo+"%").fetch();
            } else {
                consultas = Consulta.find("status = ?1 and lower(cliente.nome) like ?2", Status.AGENDADA, "%"+termo+"%").fetch();
            }
        }
        render(consultas, busca, mostrarTodas);
    }
	
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void salvar(@Valid Consulta c) {
        if(validation.hasErrors()) {
            List<Cliente> clientes = Cliente.findAll();
            List<Medico> medicos = Medico.findAll();
            renderTemplate("Consultas/form.html", c, clientes, medicos);
        }

        List<Consulta> conflitos = Consulta.find("medico = ?1 and data = ?2 and hora = ?3 and status != ?4", c.medico, c.data, c.hora, Status.CANCELADA).fetch();
        boolean conflito = false;
        for (Consulta existente : conflitos) {
            if (c.id == null || !existente.id.equals(c.id)) conflito = true;
        }

        if (conflito) {
            List<Cliente> clientes = Cliente.findAll();
            List<Medico> medicos = Medico.findAll();
            String erro = "Este médico já possui uma consulta marcada nesta data e horário.";
            renderTemplate("Consultas/form.html", c, clientes, medicos, erro);
        }

        if (c.id == null) {
            c.atendenteAgendador = Atendente.findById(Long.parseLong(session.get("usuarioLogado")));
        }
        c.save();
        listar(null);
    }
	
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void form() {
        Consulta c = new Consulta(); 
        List<Cliente> clientes = Cliente.findAll(); 
        List<Medico> medicos = Medico.findAll(); 
        render(c, clientes, medicos);
    }
	
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void formCliente() {
        Cliente c = new Cliente();
        render(c);
    }

    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void salvarCliente(@Valid Cliente c) {
        if(validation.hasErrors()) renderTemplate("Consultas/formCliente.html", c);
        c.perfil = Perfil.CLIENTE;
        c.save();
        cadastro();
    }

    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void remover(Long id) {
        Consulta c = Consulta.findById(id);
        c.status = Status.CANCELADA; 
        c.save();
        listar(null); 
    }
    
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void editar(Long id) { 
        Consulta c = Consulta.findById(id); 
        List<Cliente> clientes = Cliente.findAll(); 
        List<Medico> medicos = Medico.findAll(); 
        renderTemplate("Consultas/form.html", c, clientes, medicos);
    }
    
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void agendaMedica(Long idMedico) { 
        if (idMedico == null) { listar(null); return; }
        Medico medico = Medico.findById(idMedico); 
        List<Consulta> cdm = Consulta.find("medico = ?1 and status != ?2 order by data, hora", medico, Status.CANCELADA).fetch();
        render(medico, cdm); 
    }
    
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void removerDaAgenda(Long idMedico, Long idConsulta) { 
        Consulta c = Consulta.findById(idConsulta); 
        c.status = Status.CANCELADA; 
        c.save(); 
        agendaMedica(idMedico); 
    }
    
    @RestritoA({Perfil.ADMIN, Perfil.ATENDENTE})
    public static void realizar(Long id) { 
        Consulta c = Consulta.findById(id); 
        c.status = Status.REALIZADA; 
        c.save(); 
        listar(null); 
    }
}