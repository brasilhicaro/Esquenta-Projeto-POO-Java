package telas.ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import telas.TelaHome;
import telas.usuario.TelaCadastroDeUsuario;

public class OuvintBotoesTelaInicial implements ActionListener {

	private JFrame tela;

	public OuvintBotoesTelaInicial(JFrame tela) {
		this.tela = tela;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "Entrar":
			// TODO: realizar valida��o
			new TelaHome();
			tela.dispose();
			break;
		case "Cadastre-se":
			//TODO: cadastrar usu�rio
			new TelaCadastroDeUsuario();
			tela.dispose();
			break;
		case "Esqueceu a senha?":
			//TODO: fazer tela
			break;
		case "Cat�logo":
			//TODO: fazer tela
			break;
		}

	}

}
