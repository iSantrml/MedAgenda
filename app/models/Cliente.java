package models;

import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class Cliente extends Usuario {

    @Required
    public String cpf;

    @Required
    public String telefone;
}