package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Consulta extends Model {
	
    @Temporal(TemporalType.DATE) 
    public Date data;
    
    public String hora;
    
    public String obs;
	
    @Enumerated(EnumType.STRING) 
    public Status status; 
	
    public Consulta() { 
        this.status = Status.AGENDADA; 
    }
	
    @ManyToOne
    public Cliente cliente; 

    @ManyToOne
    public Medico medico; 
}