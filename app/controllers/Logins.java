package controllers;

import models.Cliente;
import models.Medico;
import models.Usuario;
import play.mvc.Controller;

public class Logins extends Controller {

    public static void form() {
        render();
    }

    public static void logar(String login, String senha) {
        
        Usuario usuario = Usuario.find("login = ?1 and senha = ?2", login, senha).first();
        if (usuario == null) {
            usuario = Medico.find("crm = ?1 and senha = ?2", login, senha).first();
        }
        if (usuario == null) {
            usuario = Cliente.find("cpf = ?1 and senha = ?2", login, senha).first();
        }
        
        if (usuario != null) {
            session.put("usuarioLogado", usuario.id);
            session.put("usuarioNome", usuario.nome);
            session.put("perfilUsuario", usuario.perfil.name());
            Consultas.listar(null);
        } else {
            flash.error("Login ou senha inválidos.");
            form();
        }
    }

    public static void sair() {
        session.clear();
        flash.success("Sessão encerrada.");
        form();
    }
}