package telas.programa;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import modelo.programa.ProgramaDeTv;
import modelo.programa.ProgramasContinuos;
import modelo.programa.SeriesRegulares;
import modelo.programa.enums.TipoPrograma;
import modelo.usuario.Usuario;
import telas.TelaPadrao;
import telas.ouvintes.OuvinteBotaoVoltarParaHome;
import telas.programa.ouvintes.OuvinteBotaoAdicionarPrograma;
import telas.programa.ouvintes.OuvinteBotaoDetalhesPrograma;
import telas.programa.ouvintes.OuvinteBotaoExcluirPrograma;
import telas.programa.ouvintes.OuvinteMenu;
import utilidades.CentralDeInformacoes;
import utilidades.Componentes;
import utilidades.Icones;
import utilidades.Imagens;
import utilidades.Medidas;
import utilidades.OutlineJLabel;
import utilidades.Persistencia;

public class TelaListagemProgramas extends TelaPadrao {

	private Usuario usuario;
	private OutlineJLabel lbTitulo;
	private JButton botaoAddPrograma;
	private JButton botaoDetalhes;
	private JButton botaoDeletar;
	private JRadioButton favorito;
	private JComboBox<String> canais;
	private JComboBox<String> tipo;
	private JComboBox<String> status;
	private JTextField pesquisa;
	private JTable tabelaListagem;
	private DefaultTableModel modelo;
	

	public JRadioButton getFavorito() {
		return favorito;
	}

	public JComboBox<String> getCanais() {
		return canais;
	}

	public JComboBox<String> getTipo() {
		return tipo;
	}

	public JComboBox<String> getStatus() {
		return status;
	}

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public OutlineJLabel getLbTitulo() {
		return lbTitulo;
	}

	public JButton getBotaoAddPrograma() {
		return botaoAddPrograma;
	}

	public JButton getBotaoDetalhes() {
		return botaoDetalhes;
	}

	public JTextField getPesquisa() {
		return pesquisa;
	}

	public JTable getTabelaListagem() {
		return tabelaListagem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public TelaListagemProgramas(Usuario usuario) {
		super("Listagem de programas");
		this.usuario = usuario;
		addTabela();
		adicionarBotoes();
	}

	public void adicionarComponentesGraficos() {
		adicionarBackground();
		adicionarMenu();
		adicionarLabels();
		addTextField();
		addRadioButton();
		addComboBox();
	}

	private void adicionarMenu() {
		JMenuBar menuBar = Componentes.addMenuPadrao(this);
		JMenu menu = menuBar.getMenu(0);

		OuvinteMenu ouvinteMenu = new OuvinteMenu(this);

		JMenuItem programacao = Componentes.addItemNoMenu(menu, "Receber programacao");
		programacao.addActionListener(ouvinteMenu);

		JMenuItem relatorio = Componentes.addItemNoMenu(menu, "Emitir Relatorio");
		relatorio.addActionListener(ouvinteMenu);
	}

	private void adicionarBotoes() {

		botaoAddPrograma = Componentes.addJButton(this, "Programa", Icones.ADICIONAR, 760, 490, 100, Medidas.ALTURA_30);
		OuvinteBotaoAdicionarPrograma addPrograma = new OuvinteBotaoAdicionarPrograma(this);
		botaoAddPrograma.addActionListener(addPrograma);

		botaoDetalhes = Componentes.addJButton(this, "detalhes", 625, 490, 100, Medidas.ALTURA_30);
		OuvinteBotaoDetalhesPrograma ouvinteDetalhes = new OuvinteBotaoDetalhesPrograma(this);
		botaoDetalhes.addActionListener(ouvinteDetalhes);

		JButton botaoVoltar = Componentes.addJButton(this, "Voltar", Icones.VOLTAR, 20, 20, Medidas.COMPRIMENTO_50,
				Medidas.ALTURA_30);
		botaoVoltar.addActionListener(new OuvinteBotaoVoltarParaHome(this, usuario));
		botaoDeletar = Componentes.addJButton(this, "Excluir",500, 490, 100, Medidas.ALTURA_30);
		OuvinteBotaoExcluirPrograma ouvinteBotaoExcluirPrograma = new OuvinteBotaoExcluirPrograma(this);
		botaoDeletar.addActionListener(ouvinteBotaoExcluirPrograma);
		
		if(tabelaListagem.getModel().getRowCount() == 0){
			botaoDetalhes.setEnabled(false);
			botaoDeletar.setEnabled(false);
		}
	}

	private void adicionarBackground() {
		JLabel background = new JLabel(Imagens.BACKGROUND_TELA_LISTAGEM);
		setContentPane(background);
	}

	private void addTextField() {
		pesquisa = Componentes.addJTextFieldComIcone(this, Icones.LUPA, 30, 100, Medidas.COMPRIMENTO_310, 25);
		pesquisa.setForeground(Color.GRAY);
	}

	private void adicionarLabels() {
		Persistencia p = new Persistencia();
		CentralDeInformacoes central = p.recuperarCentral("central");
		lbTitulo = Componentes.addJLabel(this, "Programas", 390, 20, Medidas.COMPRIMENTO_130, Medidas.ALTURA_30);
		lbTitulo.setOutlineColor(Color.WHITE);
		Componentes.addJLabel(this, "Programas cadastrados: " + central.getProgramas().size(), 30, 490, 400,
				Medidas.ALTURA_30);
	}

	private void addComboBox() {
		Persistencia p = new Persistencia();
		CentralDeInformacoes central = p.recuperarCentral("central");
		String espacoVazio = "";
		ArrayList<String> statusTxt = central.obterTiposDeStatus();
		statusTxt.add(0, espacoVazio);
		status = Componentes.inserirComboBox(this, statusTxt, 520, 95, 100, Medidas.ALTURA_30);
		ArrayList<String> tiposTxt = central.obterTiposDeProgramas();
		tiposTxt.add(0, espacoVazio);
		tipo = Componentes.inserirComboBox(this, tiposTxt, 635, 95, 120, 30);
		ArrayList<String> canaisTxt = central.obterCanaisNome();
		canaisTxt.add(0, espacoVazio);
		canais = Componentes.inserirComboBox(this, canaisTxt, 635, 65, 120, 30);

	}

	private void addRadioButton() {
		favorito = Componentes.addRadioButton(this, "favoritos", 430, 95, 80, Medidas.ALTURA_30);
	}

	private void addTabela() {
		modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelaListagem = new JTable(modelo);
		tabelaListagem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tabelaListagem.getModel());
		tabelaListagem.setRowSorter(rowSorter);

		pesquisa.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = pesquisa.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = pesquisa.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

		});

		Persistencia persistencia = new Persistencia();
		CentralDeInformacoes central = persistencia.recuperarCentral("central");

		ArrayList<ProgramaDeTv> programas = central.getProgramas();

		if (programas.size() == 0) {
			modelo.addColumn("Nao ha programas cadastrados!");
		} else {
			modelo.addColumn("ID");
			modelo.addColumn("Nome");
			modelo.addColumn("Canal");
			modelo.addColumn("Preferencia");
			modelo.addColumn("Genero ou Apresentadores");
			modelo.addColumn("Estilo");
			modelo.addColumn("Horario");
			modelo.addColumn("Temporadas");
			modelo.addColumn("Status do Programa");

//				escondendo a coluna ID 
			tabelaListagem.getColumnModel().getColumn(0).setMinWidth(0);
			tabelaListagem.getColumnModel().getColumn(0).setMaxWidth(0);

			Object[] linhas = new Object[programas.size()];
			
			for (ProgramaDeTv programa : programas) {
				Object[] linha = new Object[9];

				String estilo = null;
				String generoouApresentadores = null;
				String preferencia = String.valueOf(programa.getFavorito());
				if (programa.getTipo() == TipoPrograma.SERIES_REGULARES) {
					SeriesRegulares programaModificado = (SeriesRegulares) programa;
					generoouApresentadores = String.valueOf(programaModificado.getGenero());
					estilo = (String.valueOf(programaModificado.getEstilo()));
				} else {
					ProgramasContinuos programaModificado = (ProgramasContinuos) programa;

					String apresentadores = "";
					for (String concatenar : programaModificado.getApresentadores()) {
						apresentadores += concatenar + ",";
					}

					generoouApresentadores = apresentadores;
				}

				linha[0] = programa.getId();
				linha[1] = programa.getNome();
				linha[2] = programa.getCanal().toString();
				linha[3] = preferencia;
				linha[4] = generoouApresentadores;
				linha[5] = estilo;
				linha[6] = programa.getHorario();
				linha[7] = programa.getTemporadas();
				linha[8] = programa.getStatus();
				
				modelo.addRow(linha);
			}
			tabelaListagem.addRowSelectionInterval(0, 0);
		}

		JScrollPane scroll = new JScrollPane(tabelaListagem);
		scroll.setBounds(30, 130, 790, 305);
		add(scroll);
	}
}
