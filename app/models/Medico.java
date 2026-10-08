package models;

import javax.persistence.Entity;

@Entity
public class Medico extends Usuario {
    public String especialidade;
    public String crm;
}