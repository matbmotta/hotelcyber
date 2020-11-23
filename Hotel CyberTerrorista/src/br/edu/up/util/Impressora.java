package br.edu.up.util;

import java.util.Scanner;

public class Impressora {

	public static int disponivel(int contSolteiro, int contSolteiroDuplo, int contCasal, int contCasalSolteiro, String chegada, Scanner leitor) {
		int alta = ProcessadorDeArquivo.AltaTemporada(chegada);
		cls.limpar();
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Quartos disponíveis: ");
		
		if(contSolteiro > 0 && alta == 0) {
			System.out.println("(1) Quarto com cama de Solteiro. Valor da diária: R$120");
		}else if(contSolteiro > 0 && alta == 1) {
			System.out.println("(1) Quarto com cama de Solteiro. Valor da diária: R$180");
		}
		
		if(contSolteiroDuplo > 0 && alta == 0) {
			System.out.println("(2) Quarto com duas camas de Solteiro. Valor da diária: R$180");
		}else if(contSolteiroDuplo > 0 && alta == 1) {
			System.out.println("(2) Quarto com duas camas de Solteiro. Valor da diária: R$220");
		}
		
		if(contCasal > 0 && alta == 0) {
			System.out.println("(3) Quarto com cama de Casal. Valor da diária: R$220");
		}else if(contCasal > 0 && alta == 1) {
			System.out.println("(3) Quarto com cama de Casal. Valor da diária: R$280");
		}
		
		if(contCasalSolteiro > 0 && alta == 0) {
			System.out.println("(4) Quarto com cama de Casal e uma de Solteiro. Valor da diária: R$280");
		}else if(contCasal > 0 && alta == 1) {
			System.out.println("(4) Quarto com cama de Casal e uma de Solteiro. Valor da diária: R$320");
		}
		
		System.out.println("Qual desse quartos você deseja?");
		int Escolha = leitor.nextInt();
		
	return Escolha;
	}

	public static void ConsumoSolteiro(String nome, int numero_do_quarto, String entrada, String saida, Double valorConsumido, String itemConsumido) {
		int vDiaria = 0;
		System.out.println("--------------------------------------");
		System.out.println("Encerrado por: " + nome + " Quarto de Solteiro Nº: " + numero_do_quarto);
		int diaria = ProcessadorDeArquivo.CalendarioDiaria(entrada, saida);
		int alta = ProcessadorDeArquivo.AltaTemporada(entrada);
		if (alta == 0) {
			vDiaria = diaria * 120;
		}else {
			vDiaria = diaria * 180;
			System.out.println("Reserva em alta temporada");
		}
		double vtotal = vDiaria + valorConsumido;
		System.out.println("Quantidade de diária: " + diaria);
		System.out.println("Valor total da diaria: R$" + vDiaria);
		System.out.println("Valor do consumo extra: ");
		System.out.println(itemConsumido);
		System.out.println("-----------------------------------");
		System.out.println("Valor total: R$" + vtotal);
	}
	
	public static void ConsumoSolteiroDuplo(String nome, int numero_do_quarto, String entrada, String saida, Double valorConsumido, String itemConsumido) {
		int vDiaria = 0;
		System.out.println("--------------------------------------");
		System.out.println("Encerrado por: " + nome + " Quarto de Solteiro Duplo Nº: " + numero_do_quarto);
		int diaria = ProcessadorDeArquivo.CalendarioDiaria(entrada, saida);
		int alta = ProcessadorDeArquivo.AltaTemporada(entrada);
		if (alta == 0) {
			vDiaria = diaria * 180;
		}else {
			vDiaria = diaria * 220;
			System.out.println("Reserva em alta temporada");
		}
		double vtotal = vDiaria + valorConsumido;
		System.out.println("Quantidade de diária: " + diaria);
		System.out.println("Valor total da diaria: R$" + vDiaria);
		System.out.println("Valor do consumo extra: ");
		System.out.println(itemConsumido);
		System.out.println("-----------------------------------");
		System.out.println("Valor total: R$" + vtotal);
	}
	
	public static void ConsumoCasal(String nome, int numero_do_quarto, String entrada, String saida, Double valorConsumido, String itemConsumido) {
		int vDiaria = 0;
		System.out.println("--------------------------------------");
		System.out.println("Encerrado por: " + nome + " Quarto de Casal Nº: " + numero_do_quarto);
		int diaria = ProcessadorDeArquivo.CalendarioDiaria(entrada, saida);
		int alta = ProcessadorDeArquivo.AltaTemporada(entrada);
		if (alta == 0) {
			vDiaria = diaria * 220;
		}else {
			vDiaria = diaria * 280;
			System.out.println("Reserva em alta temporada");
		}
		double vtotal = vDiaria + valorConsumido;
		System.out.println("Quantidade de diária: " + diaria);
		System.out.println("Valor total da diaria: R$" + vDiaria);
		System.out.println("Valor do consumo extra: ");
		System.out.println(itemConsumido);
		System.out.println("-----------------------------------");
		System.out.println("Valor total: R$" + vtotal);
	}
	
	public static void ConsumoCasalSolteiro(String nome, int numero_do_quarto, String entrada, String saida, Double valorConsumido, String itemConsumido) {
		int vDiaria = 0;
		System.out.println("--------------------------------------");
		System.out.println("Encerrado por: " + nome + " Quarto de Casal com uma cama de Solteiro Nº: " + numero_do_quarto);
		int diaria = ProcessadorDeArquivo.CalendarioDiaria(entrada, saida);
		int alta = ProcessadorDeArquivo.AltaTemporada(entrada);
		if (alta == 0) {
			vDiaria = diaria * 280;
		}else {
			vDiaria = diaria * 320;
			System.out.println("Reserva em alta temporada");
		}
		double vtotal = vDiaria + valorConsumido;
		System.out.println("Quantidade de diária: " + diaria);
		System.out.println("Valor total da diaria: R$" + vDiaria);
		System.out.println("Valor do consumo extra: ");
		System.out.println(itemConsumido);
		System.out.println("-----------------------------------");
		System.out.println("Valor total: R$" + vtotal);
	}
}