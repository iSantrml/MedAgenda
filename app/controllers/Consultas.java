package controllers;

import java.util.List;

import models.Cliente;
import models.Medico;
import models.Consulta;
import models.Status;
import play.mvc.Controller;

public class Consultas extends Controller {

  
    public static void cadastro() { 
    List<Medico> medicos = Medico.findAll(); 
        render(medicos);
    }

    public static void listar(String busca) {
        List<Consulta> consultas = null; 
        
       
        if (busca == null) { 
            consultas = Consulta.find("status != ?1", Status.CANCELADA).fetch(); 
        } else { 
           
            consultas = Consulta.find("status != ?1 and lower(cliente.nome) like ?2",
                    Status.CANCELADA, 
                    "%"+busca.toLowerCase()+"%").fetch();
        }
        
        render(consultas, busca);
    }
	
    public static void salvar(Consulta c) {
        
      
        List<Consulta> possiveisConflitos = Consulta.find(
                "medico = ?1 and data = ?2 and hora = ?3 and status != ?4",
                c.medico, c.data, c.hora, Status.CANCELADA).fetch();

       
        boolean conflito = false;
        for (Consulta existente : possiveisConflitos) {
            if (c.id == null || !existente.id.equals(c.id)) {
                conflito = true;
            }
        }

        if (conflito) {
            List<Cliente> clientes = Cliente.findAll();
            List<Medico> medicos = Medico.findAll();
            String erro = "Este médico já possui uma consulta marcada nesta data e horário.";
            renderTemplate("Consultas/form.html", c, clientes, medicos, erro);
        }

        c.save();
        listar(null);
    }
	
    public static void form() {
        Consulta c = new Consulta(); 
        
        List<Cliente> clientes = Cliente.findAll(); 
        List<Medico> medicos = Medico.findAll(); 
        
        render(c, clientes, medicos);
    }
	
    public static void formCliente() {
        Cliente c = new Cliente();
        render(c);
    }

    public static void salvarCliente(Cliente c) {
        c.save();
        cadastro();
    }

    public static void remover(Long id) {
        Consulta c = Consulta.findById(id);
        c.status = Status.CANCELADA; 
        c.save();
        listar(null); 
    }
    
    public static void editar(Long id) { 
        Consulta c = Consulta.findById(id); 
        List<Cliente> clientes = Cliente.findAll(); 
        List<Medico> medicos = Medico.findAll(); 
        
      
        renderTemplate("Consultas/form.html", c, clientes, medicos);
    }
    
    public static void agendaMedica(Long idMedico) { 
  
        if (idMedico == null) {
            listar(null);
            return;
        }

        Medico medico = Medico.findById(idMedico); 
     
        
        List<Consulta> cdm = Consulta.find(
                "medico = ?1 and status != ?2 order by data, hora", medico, Status.CANCELADA).fetch();
        
        render(medico, cdm); 
    }
    
    public static void removerDaAgenda(Long idMedico, Long idConsulta) { 
    	Consulta c = Consulta.findById(idConsulta); 
    	c.status = Status.CANCELADA; 
    	c.save(); 
    	agendaMedica(idMedico); 
    	}
    
    public static void realizar(Long id) { 
    	Consulta c = Consulta.findById(id); 
    	c.status = Status.REALIZADA; 
    	c.save(); 
    	listar(null); 
    }
}