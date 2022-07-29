package telas.canal.ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.canal.TelaNovoCanal;

public class OuvinteRadioButton implements ActionListener {

	private TelaNovoCanal tela;

	public TelaNovoCanal getTela() {
		return tela;
	}

	public void setTela(TelaNovoCanal tela) {
		this.tela = tela;
	}

	public OuvinteRadioButton(TelaNovoCanal tela) {
		this.tela = tela;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String radioSelecionado = e.getActionCommand();

		switch (radioSelecionado) {
		case "Broadcasting":
			tela.getCbTv().setVisible(false);
			tela.getCbBroadcasting().setVisible(true);
			tela.getNumeroCanal().setVisible(false);
			tela.getLinkBroadcasting().setVisible(true);
			break;
		case "Televisao":
			tela.getCbTv().setVisible(true);
			tela.getCbBroadcasting().setVisible(false);
			tela.getNumeroCanal().setVisible(true);
			tela.getLinkBroadcasting().setVisible(false);
			break;
		}

	}

}
