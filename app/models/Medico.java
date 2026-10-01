package models;

import javax.persistence.Entity;
import play.db.jpa.Model;

@Entity	
public class Medico extends Model {
    public String nome; 
    public String especialidade;
    public String crm;
}