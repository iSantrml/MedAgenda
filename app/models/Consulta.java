package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;
import play.data.validation.Required;
import play.data.validation.Match;

@Entity
public class Consulta extends Model {
    
    @Required(message="A data é obrigatória")
    @Temporal(TemporalType.DATE) 
    public Date data;
    
    @Required(message="A hora é obrigatória")
    @Match(value="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message="Use o formato HH:mm")
    public String hora;
    
    public String obs;
    
    @Enumerated(EnumType.STRING) 
    public Status status; 
    
    @Required(message="O paciente é obrigatório")
    @ManyToOne
    public Cliente cliente; 

    @Required(message="O médico é obrigatório")
    @ManyToOne
    public Medico medico; 

    @ManyToOne
    public Atendente atendenteAgendador;

    public Consulta() { 
        this.status = Status.AGENDADA; 
    }
}