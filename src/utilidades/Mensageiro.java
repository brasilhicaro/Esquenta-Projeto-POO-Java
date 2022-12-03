package utilidades;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import modelo.usuario.Usuario;

public class Mensageiro {

	public static void enviarEmail() {
		Persistencia p = new Persistencia();
		CentralDeInformacoes centralDeInformacoes = p.recuperarCentral("central");
		Usuario u = centralDeInformacoes.getUsuariosCadastrados().get(0);
		String remetente = "projetoseriespoo@gmail.com";
		String senha = ${{ secrets.SENHA }};

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(remetente, senha));
		email.setSSLOnConnect(true);
		try {
			email.setFrom(remetente);
			email.setSubject("Programacao da semana");
			email.setMsg("Segue anexo da programacao da semana");
			email.addTo(u.getEmail());

			EmailAttachment anexo = new EmailAttachment();

			anexo.setPath("relatorio.pdf");
			email.attach(anexo);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void enviarSenha(Usuario eMail) {
		String remetente = "projetoseriespoo@gmail.com";
		String senha = "nchwpdgmrfccbxki";

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(remetente, senha));
		email.setSSLOnConnect(true);
		try {
			email.setFrom(remetente);
			email.setSubject("Segue sua senha");
			email.setMsg(eMail.getSenha());
			email.addTo(eMail.getEmail());

			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
