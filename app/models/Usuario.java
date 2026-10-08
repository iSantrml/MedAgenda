package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {

    @Required
    public String nome;

    // Usado por ADMIN/ATENDENTE. Médico entra com o CRM e cliente com o CPF.
    public String login;

    @Required
    public String senha;

    @Enumerated(EnumType.STRING)
    public Perfil perfil;
}