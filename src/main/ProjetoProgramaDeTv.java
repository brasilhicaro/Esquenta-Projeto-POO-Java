package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
					TipoPrograma tipo = null;
					
					if(central.obterTiposDeProgramas().contains(tipoString)) {
						tipo = TipoPrograma.valueOf(tipoString);
					} else {
						System.out.println("n�o existe esse tipo de programa : " + tipoString);
						break;
					}
					
					System.out.print("Canais: ");
					central.exibirCanaisPeloNome();
					System.out.print("\nCanal: ");
					String nomeCanal = inputter.nextLine();
					Canal canal = central.recuperarCanalPeloNome(nomeCanal);
					
					if(canal == null) {
						System.out.println("n�o existe esse canal: " + nomeCanal);
						break;
					}
					
					System.out.println("dias da Semana: [separados por ','. ex: segunda-feira...]");
					System.out.print("dias escolhidos: ");
					String[] dias = inputter.nextLine().split(",");

					ArrayList<String> diasDaSemana = new ArrayList<>(Arrays.asList(dias));
					ProgramaDeTv programa = new ProgramaDeTv(nome, tipo, canal, diasDaSemana);

					if (central.AdicionarProgramaDeTv(programa)) {
						System.out.println("Programa cadastrado com sucesso!");
						persistencia.salvarCentral(central, "central");
					} else {
						System.out.println("n�o foi poss�vel cadastrar o programa!");
						System.out.println("O programa j� existe ou n�o possui um canal.");
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
				System.out.print("tipo do Programa: ");
				central.exibirTiposDeProgramas();
				System.out.print("\nOp��o: ");
				String tipoString = inputter.nextLine().toUpperCase();
				TipoPrograma tipo = null;
				
				if(central.obterTiposDeProgramas().contains(tipoString)) {
					tipo = TipoPrograma.valueOf(tipoString);
					central.exibirProgramasPeloTipo(tipo);
				} else {
					System.out.println("n�o existe esse tipo de programa : " + tipoString);
					break;
				}
				break;
			case "4":
				System.out.println("-- dados do canal --");
				System.out.print("nome: ");
				String nomeCanal = inputter.nextLine();
				System.out.print("Tipo do canal: ");
				String tipoCanal = inputter.nextLine();

				Canal canal = new Canal(nomeCanal, tipoCanal);
				
				if (central.adicionarCanal(canal)) {
					System.out.println("Canal cadastrado com sucesso!\n");
					persistencia.salvarCentral(central, "central");
					
				} else {
					System.out.println("n�o foi poss�vel cadastrar o canal!");
					System.out.println("O canal j� existe.");
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
				
				canal = central.recuperarCanalPeloNome(nomeDoCanal);
	
				if( canal != null) {
					GeradorDePdf.ObterProgramacaoDeUmCanal(canal);	
				} else {
					System.out.println("N�o foi encontrado um canal com esse nome");
				}
				break;
			case "7":
				
				System.out.print("informe seu e-mail: ");
				String destinatario = inputter.nextLine();
				
				ArrayList<ProgramaDeTv> programasDoDia = central.obterProgramasComTransmiss�oNaDataAtual();
				
				String mensagem = null; 
				
				if(programasDoDia.isEmpty()) {
					mensagem = "n�o h� programas para hoje";
				} else {
					mensagem = programasDoDia.toString();
				}
				
				System.out.println("enviando e-mail...");
				Mensageiro.enviarProgramacaoDeHoje(destinatario, "programa��o do dia", mensagem );
				System.out.println("e-mail enviado. cheque sua caixa de entrada ou spam");
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
