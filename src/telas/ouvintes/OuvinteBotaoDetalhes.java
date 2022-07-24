package telas.ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaDetalheCanal;
import telas.TelaListagemCanais;

public class OuvinteBotaoDetalhes implements ActionListener {

	private TelaListagemCanais TelaListagemCanais;

	
	public OuvinteBotaoDetalhes(TelaListagemCanais TelaListagemCanais) {
		this.TelaListagemCanais = TelaListagemCanais;
	}
	
	
	public TelaListagemCanais getTelaListagemCanais() {
		return TelaListagemCanais;
	}

	public void setTelaListagemCanais(TelaListagemCanais telaListagemCanais) {
		TelaListagemCanais = telaListagemCanais;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TelaListagemCanais.dispose();
		new TelaDetalheCanal();

	}

}
