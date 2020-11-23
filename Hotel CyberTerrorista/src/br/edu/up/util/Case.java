package br.edu.up.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.up.dominio.QuartoCasal;
import br.edu.up.dominio.QuartoCasalSolteiro;
import br.edu.up.dominio.QuartoSolteiro;
import br.edu.up.dominio.QuartoSolteiroDuplo;

public class Case {
	
	public static void reserva(Scanner leitor){
		System.out.println("-------------------Reserva de Quarto------------------");
		leitor.nextLine();
		System.out.println("Digite o Dia/Mês/Ano de ENTRADA: ");
		String Chegada = leitor.nextLine();
		System.out.println("Digite o Dia/Mês/Ano de SAÍDA: ");
		String Saida = leitor.nextLine();
		
		List<QuartoSolteiro> ListaDeQuartoSolteiro = ProcessadorDeArquivo.listarQuartoSolteiro();
		int contSolteiro = 0;
		for(QuartoSolteiro quartoSolteiro: ListaDeQuartoSolteiro) {
			if(quartoSolteiro.getNome() != null) {
				String EntradaRegistrada = quartoSolteiro.getEntrada();
				String SaidaRegistrada = quartoSolteiro.getSaida();
				int reserva = ProcessadorDeArquivo.CalendarioReserva(Chegada, Saida, EntradaRegistrada, SaidaRegistrada);
				if(reserva != 0) {
					contSolteiro++;
				}
			}else if(quartoSolteiro.getNumero_do_quarto() <= 5){
				contSolteiro++;
			}
		}
		
		List<QuartoSolteiroDuplo> ListaDeQuartoSolteiroDuplo = ProcessadorDeArquivo.listarQuartoSolteiroDuplo();
		int contSolteiroDuplo = 0;
		for(QuartoSolteiroDuplo quartoSolteiroDuplo: ListaDeQuartoSolteiroDuplo) {
			if(quartoSolteiroDuplo.getNome() != null) {
				String EntradaRegistrada = quartoSolteiroDuplo.getEntrada();
				String SaidaRegistrada = quartoSolteiroDuplo.getSaida();
				int reserva = ProcessadorDeArquivo.CalendarioReserva(Chegada, Saida, EntradaRegistrada, SaidaRegistrada);
				if(reserva != 0) {
					contSolteiroDuplo++;
				}
			}else if(quartoSolteiroDuplo.getNumero_do_quarto() <= 4){
				contSolteiroDuplo++;
			}
		}
		
		List<QuartoCasal> ListaDeQuartoCasal = ProcessadorDeArquivo.listarQuartoCasal();
		int contCasal = 0;
		for(QuartoCasal quartoCasal: ListaDeQuartoCasal) {
			
			if(quartoCasal.getNome() != null) {
				String EntradaRegistrada = quartoCasal.getEntrada();
				String SaidaRegistrada = quartoCasal.getSaida();
				int reserva = ProcessadorDeArquivo.CalendarioReserva(Chegada, Saida, EntradaRegistrada, SaidaRegistrada);
				if(reserva != 0) {
					contCasal++;
				}
			}else if(quartoCasal.getNumero_do_quarto() <= 7){
				contCasal++;
			}
		}
		
		List<QuartoCasalSolteiro> ListaDeQuartoCasalSolteiro = ProcessadorDeArquivo.listarQuartoCasalSolteiro();
		int contCasalSolteiro = 0;
		for(QuartoCasalSolteiro quartoCasalSolteiro: ListaDeQuartoCasalSolteiro) {
			
			if(quartoCasalSolteiro.getNome() != null) {
				
				String EntradaRegistrada = quartoCasalSolteiro.getEntrada();
				String SaidaRegistrada = quartoCasalSolteiro.getSaida();
				int reserva = ProcessadorDeArquivo.CalendarioReserva(Chegada, Saida, EntradaRegistrada, SaidaRegistrada);
				if(reserva != 0) {
					contCasalSolteiro++;
				}
				
			}else if(quartoCasalSolteiro.getNumero_do_quarto() <= 3){
				contCasalSolteiro++;
			}
		}
		
		int Escolha = Impressora.disponivel(contSolteiro, contSolteiroDuplo, contCasal, contCasalSolteiro, Chegada, leitor);
		
		switch(Escolha) {
			
			case 1:
				Escolha = 0;
				Reserva.case1(leitor, Escolha, Chegada, Saida);
			break;
			
			case 2:
				Escolha = 0;
				Reserva.case2(leitor, Escolha, Chegada, Saida);				
			break;
			
			case 3:
				Escolha = 0;
				Reserva.case3(leitor, Escolha, Chegada, Saida);				
			break;
			
			case 4:
				Escolha = 0;
				Reserva.case4(leitor, Escolha, Chegada, Saida);				
			break;
			
			default:
				System.out.println("Escolha inválida");
		}
	}

	public static void Checkin(Scanner leitor) {
		
		System.out.println("-------------------Check-In------------------");
		leitor.nextLine();
		System.out.println("Digite seu CPF: ");
		String CPF = leitor.nextLine();
		System.out.println("Digite a Data de Entrada");
		String Entrada = leitor.nextLine();
		
		List<QuartoSolteiro> listaDeQuartoSolteiro = ProcessadorDeArquivo.carregarPesquisaQuartoSolteiro(CPF);
		
		for (QuartoSolteiro quartoSolteiro : listaDeQuartoSolteiro) {
			int checkin = ProcessadorDeArquivo.CalendarioCheckIn(Entrada, quartoSolteiro.getEntrada(), quartoSolteiro.getSaida());
			if(checkin == 1) {
				System.out.println("Reservado por: " + quartoSolteiro.getNome() + " Quarto de Solteiro Nº: " + quartoSolteiro.getNumero_do_quarto());
				List<QuartoSolteiro> listaQuartoSolteiros = new ArrayList<>();
				QuartoSolteiro quartoSolteiro2 = new QuartoSolteiro(quartoSolteiro.getId(), quartoSolteiro.getCpf(), quartoSolteiro.getNome(), quartoSolteiro.getEntrada(), quartoSolteiro.getSaida(), quartoSolteiro.getNumero_do_quarto(), quartoSolteiro.getCheckIn(), quartoSolteiro.getItemConsumido(), quartoSolteiro.getValorConsumido());
				listaQuartoSolteiros.add(quartoSolteiro2);
				
				ProcessadorDeArquivo.CheckInSolteiro(listaQuartoSolteiros);
			}
		}
		
		List<QuartoSolteiroDuplo> listaDeQuartoSolteiroDuplo = ProcessadorDeArquivo.carregarPesquisaQuartoSolteiroDuplo(CPF);
		
		for (QuartoSolteiroDuplo quartoSolteiroDuplo : listaDeQuartoSolteiroDuplo) {
			int checkin = ProcessadorDeArquivo.CalendarioCheckIn(Entrada, quartoSolteiroDuplo.getEntrada(), quartoSolteiroDuplo.getSaida());
			if(checkin == 1) {
				System.out.println("Reservado por: " + quartoSolteiroDuplo.getNome() + " Quarto de Solteiro Duplo Nº: " + quartoSolteiroDuplo.getNumero_do_quarto());
				List<QuartoSolteiroDuplo> listaQuartoSolteiroDuplos = new ArrayList<>();
				QuartoSolteiroDuplo quartoSolteiroDuplo2 = new QuartoSolteiroDuplo(quartoSolteiroDuplo.getId(), quartoSolteiroDuplo.getCpf(), quartoSolteiroDuplo.getNome(), quartoSolteiroDuplo.getEntrada(), quartoSolteiroDuplo.getSaida(), quartoSolteiroDuplo.getNumero_do_quarto(), quartoSolteiroDuplo.getCheckIn(), quartoSolteiroDuplo.getItemConsumido(), quartoSolteiroDuplo.getValorConsumido());
				listaQuartoSolteiroDuplos.add(quartoSolteiroDuplo2);
				
				ProcessadorDeArquivo.CheckInSolteiroDuplo(listaQuartoSolteiroDuplos);
			}
		}
		
		List<QuartoCasal> listaDeQuartoCasal = ProcessadorDeArquivo.carregarPesquisaQuartoCasal(CPF);
		
		for (QuartoCasal quartoCasal : listaDeQuartoCasal) {
			int checkin = ProcessadorDeArquivo.CalendarioCheckIn(Entrada, quartoCasal.getEntrada(), quartoCasal.getSaida());
			if(checkin == 1) {
				System.out.println("Reservado por: " + quartoCasal.getNome() + " Quarto de Casal Nº: " + quartoCasal.getNumero_do_quarto());
				List<QuartoCasal> listaQuartoCasals = new ArrayList<>();
				QuartoCasal quartoCasal2 = new QuartoCasal(quartoCasal.getId(), quartoCasal.getCpf(), quartoCasal.getNome(), quartoCasal.getEntrada(), quartoCasal.getSaida(), quartoCasal.getNumero_do_quarto(), quartoCasal.getCheckIn(), quartoCasal.getItemConsumido(), quartoCasal.getValorConsumido());
				listaQuartoCasals.add(quartoCasal2);
				
				ProcessadorDeArquivo.CheckInCasal(listaQuartoCasals);
			}
		}
		
		List<QuartoCasalSolteiro> listaDeQuartoCasalSolteiro = ProcessadorDeArquivo.carregarPesquisaQuartoCasalSolteiro(CPF);
		
		for (QuartoCasalSolteiro quartoCasalSolteiro : listaDeQuartoCasalSolteiro) {
			int checkin = ProcessadorDeArquivo.CalendarioCheckIn(Entrada, quartoCasalSolteiro.getEntrada(), quartoCasalSolteiro.getSaida());
			if(checkin == 1) {
				System.out.println("Reservado por: " + quartoCasalSolteiro.getNome() + " Quarto de Casal com uma cama de Solteiro Nº: " + quartoCasalSolteiro.getNumero_do_quarto());
				List<QuartoCasalSolteiro> listaQuartoCasalSolteiros = new ArrayList<>();
				QuartoCasalSolteiro quartoCasalSolteiro2 = new QuartoCasalSolteiro(quartoCasalSolteiro.getId(), quartoCasalSolteiro.getCpf(), quartoCasalSolteiro.getNome(), quartoCasalSolteiro.getEntrada(), quartoCasalSolteiro.getSaida(), quartoCasalSolteiro.getNumero_do_quarto(), quartoCasalSolteiro.getCheckIn(), quartoCasalSolteiro.getItemConsumido(), quartoCasalSolteiro.getValorConsumido());
				listaQuartoCasalSolteiros.add(quartoCasalSolteiro2);
				
				ProcessadorDeArquivo.CheckInCasalSolteiro(listaQuartoCasalSolteiros);
			}
		}
	}

	public static void Consumo(Scanner leitor) {
		int cont = 0;
		char Escolha = 0;
		String itemConsumido = "";
		Double valorConsumido = 0.0;
		System.out.println("-------------------Consumo do Hóspede------------------");
		leitor.nextLine();
		System.out.println("Digite o CPF do Hóspede: ");
		String CPF = leitor.nextLine();
		
		List<QuartoSolteiro> listaDeQuartoSolteiro = ProcessadorDeArquivo.carregarPesquisaQuartoSolteiro(CPF);
		
		for (QuartoSolteiro quartoSolteiro : listaDeQuartoSolteiro) {
			if(quartoSolteiro.getCheckIn() == 1) {
				System.out.println("Hóspede: " + quartoSolteiro.getNome() + " Quarto de Solteiro número: " + quartoSolteiro.getNumero_do_quarto());
				do {
					leitor.nextLine();
					System.out.println("--------------------------------------------------------------");
					System.out.println("Digite o nome do item consumido: ");
					String item = leitor.nextLine();
					System.out.println("Digite o valor do item: ");
					String valor2 = leitor.nextLine();
					Double valor = Double.parseDouble(valor2);
					itemConsumido += item + "\t" + " R$" + valor2 + System.lineSeparator();
					valorConsumido += valor;
					Escolha = ProcessadorDeArquivo.Validacao(leitor, cont, Escolha);
				}while(Escolha == 'S' || Escolha == 's');
				itemConsumido += quartoSolteiro.getItemConsumido();
				valorConsumido += quartoSolteiro.getValorConsumido();
				ProcessadorDeArquivo.AtualizarHospedagemSolteiro(itemConsumido, valorConsumido, quartoSolteiro.getId());	
			}
		}
		
		List<QuartoSolteiroDuplo> listaDeQuartoSolteiroDuplo = ProcessadorDeArquivo.carregarPesquisaQuartoSolteiroDuplo(CPF);
		
		for (QuartoSolteiroDuplo quartoSolteiroDuplo : listaDeQuartoSolteiroDuplo) {
			if(quartoSolteiroDuplo.getCheckIn() == 1) {
				System.out.println("Hóspede: " + quartoSolteiroDuplo.getNome() + " Quarto de Solteiro Duplo número: " + quartoSolteiroDuplo.getNumero_do_quarto());
				do {
					leitor.nextLine();
					System.out.println("--------------------------------------------------------------");
					System.out.println("Digite o nome do item consumido: ");
					String item = leitor.nextLine();
					System.out.println("Digite o valor do item: ");
					String valor2 = leitor.nextLine();
					Double valor = Double.parseDouble(valor2);
					itemConsumido += item + "\t" + " R$" + valor2 + System.lineSeparator();
					valorConsumido += valor;
					Escolha = ProcessadorDeArquivo.Validacao(leitor, cont, Escolha);
				}while(Escolha == 'S' || Escolha == 's');
				itemConsumido += quartoSolteiroDuplo.getItemConsumido();
				valorConsumido += quartoSolteiroDuplo.getValorConsumido();
				ProcessadorDeArquivo.AtualizarHospedagemSolteiroDuplo(itemConsumido, valorConsumido, quartoSolteiroDuplo.getId());	
			}
		}
		
		List<QuartoCasal> listaDeQuartoCasal = ProcessadorDeArquivo.carregarPesquisaQuartoCasal(CPF);
		
		for (QuartoCasal quartoCasal : listaDeQuartoCasal) {
			if(quartoCasal.getCheckIn() == 1) {
				System.out.println("Hóspede: " + quartoCasal.getNome() + " Quarto de Casal número: " + quartoCasal.getNumero_do_quarto());
				do {
					leitor.nextLine();
					System.out.println("--------------------------------------------------------------");
					System.out.println("Digite o nome do item consumido: ");
					String item = leitor.nextLine();
					System.out.println("Digite o valor do item: ");
					String valor2 = leitor.nextLine();
					Double valor = Double.parseDouble(valor2);
					itemConsumido += item + "\t" + " R$" + valor2 + System.lineSeparator();
					valorConsumido += valor;
					Escolha = ProcessadorDeArquivo.Validacao(leitor, cont, Escolha);
				}while(Escolha == 'S' || Escolha == 's');
				itemConsumido += quartoCasal.getItemConsumido();
				valorConsumido += quartoCasal.getValorConsumido();
				ProcessadorDeArquivo.AtualizarHospedagemCasal(itemConsumido, valorConsumido, quartoCasal.getId());	
			}
		}
		
		List<QuartoCasalSolteiro> listaDeQuartoCasalSolteiro = ProcessadorDeArquivo.carregarPesquisaQuartoCasalSolteiro(CPF);
		
		for (QuartoCasalSolteiro quartoCasalSolteiro : listaDeQuartoCasalSolteiro) {
			if(quartoCasalSolteiro.getCheckIn() == 1) {
				System.out.println("Hóspede: " + quartoCasalSolteiro.getNome() + " Quarto de Casal com uma cama de Solteiro número: " + quartoCasalSolteiro.getNumero_do_quarto());
				do {
					leitor.nextLine();
					System.out.println("--------------------------------------------------------------");
					System.out.println("Digite o nome do item consumido: ");
					String item = leitor.nextLine();
					System.out.println("Digite o valor do item: ");
					String valor2 = leitor.nextLine();
					Double valor = Double.parseDouble(valor2);
					itemConsumido += item + "\t" + " R$" + valor2 + System.lineSeparator();
					valorConsumido += valor;
					Escolha = ProcessadorDeArquivo.Validacao(leitor, cont, Escolha);
				}while(Escolha == 'S' || Escolha == 's');
				itemConsumido += quartoCasalSolteiro.getItemConsumido();
				valorConsumido += quartoCasalSolteiro.getValorConsumido();
				ProcessadorDeArquivo.AtualizarHospedagemCasalSolteiro(itemConsumido, valorConsumido, quartoCasalSolteiro.getId());	
			}
		}
	}

	public static void CheckOut(Scanner leitor) {
		int cont = 0;
		char Escolha = 0;
		System.out.println("-------------------Check-Out------------------");
		leitor.nextLine();
		System.out.println("Digite seu CPF: ");
		String CPF = leitor.nextLine();
				
		List<QuartoSolteiro> listaDeQuartoSolteiro = ProcessadorDeArquivo.carregarPesquisaQuartoSolteiro(CPF);
		
		for (QuartoSolteiro quartoSolteiro : listaDeQuartoSolteiro) {
			if(quartoSolteiro.getCheckIn() == 1) {
				Escolha = 0;
				System.out.println("Quarto de Solteiro Nº: " + quartoSolteiro.getNumero_do_quarto());
				Escolha = ProcessadorDeArquivo.ValidacaoQuarto(leitor, cont, Escolha);
				if (Escolha == 'S' || Escolha == 's') {
					Impressora.ConsumoSolteiro(quartoSolteiro.getNome(), quartoSolteiro.getNumero_do_quarto(), quartoSolteiro.getEntrada(), quartoSolteiro.getSaida(), quartoSolteiro.getValorConsumido(), quartoSolteiro.getItemConsumido());
					leitor.nextLine();
					ProcessadorDeArquivo.CheckOutSolteiro(quartoSolteiro.getId());
				}
			}
		}
		
		List<QuartoSolteiroDuplo> listaDeQuartoSolteiroDuplo = ProcessadorDeArquivo.carregarPesquisaQuartoSolteiroDuplo(CPF);
		
		for (QuartoSolteiroDuplo quartoSolteiroDuplo : listaDeQuartoSolteiroDuplo) {
			if(quartoSolteiroDuplo.getCheckIn() == 1) {
				Escolha = 0;
				System.out.println("Quarto de Solteiro Duplo Nº: " + quartoSolteiroDuplo.getNumero_do_quarto());
				Escolha = ProcessadorDeArquivo.ValidacaoQuarto(leitor, cont, Escolha);
				if (Escolha == 'S' || Escolha == 's') {
					Impressora.ConsumoSolteiroDuplo(quartoSolteiroDuplo.getNome(), quartoSolteiroDuplo.getNumero_do_quarto(), quartoSolteiroDuplo.getEntrada(), quartoSolteiroDuplo.getSaida(), quartoSolteiroDuplo.getValorConsumido(), quartoSolteiroDuplo.getItemConsumido());
					leitor.nextLine();
					ProcessadorDeArquivo.CheckOutSolteiroDuplo(quartoSolteiroDuplo.getId());
				}
			}
		}
		
		List<QuartoCasal> listaDeQuartoCasal = ProcessadorDeArquivo.carregarPesquisaQuartoCasal(CPF);
		
		for (QuartoCasal quartoCasal : listaDeQuartoCasal) {
			if(quartoCasal.getCheckIn() == 1) {
				Escolha = 0;
				System.out.println("Quarto de Casal Nº: " + quartoCasal.getNumero_do_quarto());
				Escolha = ProcessadorDeArquivo.ValidacaoQuarto(leitor, cont, Escolha);
				if (Escolha == 'S' || Escolha == 's') {
					Impressora.ConsumoCasal(quartoCasal.getNome(), quartoCasal.getNumero_do_quarto(), quartoCasal.getEntrada(), quartoCasal.getSaida(), quartoCasal.getValorConsumido(), quartoCasal.getItemConsumido());
					leitor.nextLine();
					ProcessadorDeArquivo.CheckOutCasal(quartoCasal.getId());
				}
			}
		}
		
		List<QuartoCasalSolteiro> listaDeQuartoCasalSolteiro = ProcessadorDeArquivo.carregarPesquisaQuartoCasalSolteiro(CPF);
		
		for (QuartoCasalSolteiro quartoCasalSolteiro : listaDeQuartoCasalSolteiro) {
			if(quartoCasalSolteiro.getCheckIn() == 1) {
				Escolha = 0;
				System.out.println("Quarto de Casal com uma cama de Solteiro Nº: " + quartoCasalSolteiro.getNumero_do_quarto());
				Escolha = ProcessadorDeArquivo.ValidacaoQuarto(leitor, cont, Escolha);
				if (Escolha == 'S' || Escolha == 's') {
					Impressora.ConsumoCasalSolteiro(quartoCasalSolteiro.getNome(), quartoCasalSolteiro.getNumero_do_quarto(), quartoCasalSolteiro.getEntrada(), quartoCasalSolteiro.getSaida(), quartoCasalSolteiro.getValorConsumido(), quartoCasalSolteiro.getItemConsumido());
					leitor.nextLine();
					ProcessadorDeArquivo.CheckOutCasalSolteiro(quartoCasalSolteiro.getId());
				}
			}
		}
	}
}