package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import excecoes.FalhaNoCadastroException;
import excecoes.RegistroExistenteException;
import excecoes.RegistroNaoEncontradoException;
import excecoes.SemProgramaNaDataAtualException;
import excecoes.TipoDeProgramaNaoExisteException;
import modelo.Canal;
import modelo.ProgramaDeTv;
import modelo.TipoPrograma;
import utilidades.CentralDeInformacoes;
import utilidades.GeradorDePdf;
import utilidades.Mensageiro;
import utilidades.Persistencia;

public class ProjetoProgramaDeTv {

	public static void main(String[] args) {

		Scanner inputter = new Scanner(System.in);

		Persistencia persistencia = new Persistencia();
		CentralDeInformacoes central = persistencia.recuperarCentral("central");

		boolean sair = false;

		do {
			System.out.println("[1] - Novo Programa");
			System.out.println("[2] - Listar Programas");
			System.out.println("[3] - Listar programas de um mesmo tipo");
			System.out.println("[4] - Novo canal");
			System.out.println("[5] - Listar canais");
			System.out.println("[6] - Gerar rela��o dos programas de um mesmo canal");
			System.out.println("[7] - Enviar programa��o de hoje");
			System.out.println("[S] - Sair");

			System.out.print("\nOp��o: ");
			String opcao = inputter.nextLine();

			switch (opcao) {
			case "1":
				if (central.getCanais().isEmpty()) {
					System.out.println("para cadastrar um programa � necess�rio cadastrar um Canal(s) previamente.\n");
				} else {
					System.out.println("\n-- dados do Programa -- ");
					System.out.print("Nome do Programa: ");
					String nome = inputter.nextLine();

					System.out.print("tipo do Programa: ");
					central.exibirTiposDeProgramas();

					System.out.print("\nOp��o: ");
					String tipoString = inputter.nextLine().toUpperCase();
					TipoPrograma tipo = TipoPrograma.valueOf(tipoString);
					try {
						central.hasTipoPrograma(tipo);

					} catch (TipoDeProgramaNaoExisteException e) {
						System.out.println(e.getMessage());
					}

					System.out.print("Canais: ");
					central.exibirCanaisPeloNome();

					System.out.print("\nCanal: ");
					String nomeCanal = inputter.nextLine();

					Canal canal = null;

					try {
						canal = central.recuperarCanalPeloNome(nomeCanal);
					} catch (RegistroNaoEncontradoException e) {
						System.out.println(e.getMessage());
						break;
					}

					System.out.println("dias da Semana: [separados por ','. ex: segunda-feira...]");
					System.out.print("dias escolhidos: ");
					String[] dias = inputter.nextLine().split(",");

					ArrayList<String> diasDaSemana = new ArrayList<>(Arrays.asList(dias));
					ProgramaDeTv programa = new ProgramaDeTv(nome, tipo, canal, diasDaSemana);
					try {
						central.AdicionarProgramaDeTv(programa);
						persistencia.salvarCentral(central, "central");
					} catch (FalhaNoCadastroException e) {
						System.out.println(e.getMessage());
					}
				}

				break;

			case "2":
				if (central.getProgramas().isEmpty()) {
					System.out.println("N�o h� nenhum programa cadastrado.");
				} else {
					central.exibirProgramas();
				}
				break;

			case "3":
				System.out.print("tipos de Programas: ");
				central.exibirTiposDeProgramas();

				System.out.print("\nOp��o: ");
				String tipoString = inputter.nextLine().toUpperCase();
				TipoPrograma tipo = TipoPrograma.valueOf(tipoString);
				try {
					central.hasTipoPrograma(tipo);

					central.exibirProgramasPeloTipo(tipo);
				} catch (TipoDeProgramaNaoExisteException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "4":
				System.out.println("-- dados do canal --");
				System.out.print("nome: ");
				String nomeCanal = inputter.nextLine();
				System.out.print("Tipo do canal: ");
				String tipoCanal = inputter.nextLine();

				Canal canal = new Canal(nomeCanal, tipoCanal);

				try {
					central.adicionarCanal(canal);
					System.out.println("Canal cadastrado com sucesso!\n");
					persistencia.salvarCentral(central, "central");
				} catch (RegistroExistenteException e) {
					System.out.println(e.getMessage());
				}

				break;

			case "5":
				if (central.getCanais().isEmpty()) {
					System.out.println("nenhum canal cadastrado.\n");
				} else {
					central.exibirCanais();
				}
				break;

			case "6":
				System.out.print("Canais: ");
				central.exibirCanaisPeloNome();
				System.out.print("\nOp��o: ");
				String nomeDoCanal = inputter.nextLine();

				try {
					canal = central.recuperarCanalPeloNome(nomeDoCanal);
					GeradorDePdf.ObterProgramacaoDeUmCanal(canal);
				} catch (RegistroNaoEncontradoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "7":
				System.out.print("informe seu e-mail: ");
				String destinatario = inputter.nextLine();

				try {
					ArrayList<ProgramaDeTv> programasDoDia = central.obterProgramasComTransmissaoNaDataAtual();

					String mensagem = programasDoDia.toString();
					System.out.println("enviando e-mail...");
					Mensageiro.enviarProgramacaoDeHoje(destinatario, "programa��o do dia", mensagem);
					System.out.println("e-mail enviado. cheque sua caixa de entrada ou spam");

				} catch (SemProgramaNaDataAtualException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "s":
			case "S":
				System.out.println("saindo...");
				sair = true;
				break;
			default:
				System.out.println("op��o inv�lida.");
				break;
			}

		} while (!sair);
	}
}
