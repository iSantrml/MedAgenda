package security;

import models.Perfil;
import play.mvc.Before;
import play.mvc.Controller;
import controllers.Logins;

public class Seguranca extends Controller {

    @Before
    static void checarAutenticacao() {
        if (!session.contains("usuarioLogado")) {
            flash.error("Faça login no sistema.");
            Logins.form();
        }
    }
    
    @Before
    static void checarAutorizacao() {
        RestritoA anotacao = getActionAnnotation(RestritoA.class);
        if (anotacao == null) {
            anotacao = getControllerAnnotation(RestritoA.class);
        }
        
        if (anotacao != null) {
            String perfilUsuario = session.get("perfilUsuario");
            boolean temPermissao = false;
            
            for (Perfil p : anotacao.value()) {
                if (p.name().equals(perfilUsuario)) {
                    temPermissao = true;
                    break;
                }
            }
            
            if (!temPermissao) {
                forbidden("Acesso Negado para o seu perfil.");
            }
        }
    }
}