package telas.ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaListagemCanais;
import telas.TelaNovoCanal;

public class OuvinteBotaoAdicionarCanal implements ActionListener{

	private TelaListagemCanais telaListagem;
	
	

	public TelaListagemCanais getTelaListagem() {
		return telaListagem;
	}

	public void setTelaListagem(TelaListagemCanais telaListagem) {
		this.telaListagem = telaListagem;
	}
	
	
	
	public OuvinteBotaoAdicionarCanal(TelaListagemCanais telaListagem) {
		this.telaListagem = telaListagem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		telaListagem.dispose();
		new TelaNovoCanal(telaListagem.getUsuarioLogado());
		
	}
}
