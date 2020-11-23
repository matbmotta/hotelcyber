package br.edu.up.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.edu.up.dominio.Cliente;

public class Reserva {

	public static void case1(Scanner leitor, int escolha, String chegada, String saida) {
		cls.limpar();
		System.out.println("--------------------------------------------------------");
		System.out.println("(1) Cadastrar");
		System.out.println("(2) Já sou CADASTRADO");
		escolha = leitor.nextInt();
		if(escolha == 1) {
			cls.limpar();
			int cont = 0;
			System.out.println("-------------------Cadastro do Cliente------------------");
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			System.out.println("Digite seu NOME: ");
			String Nome = leitor.nextLine();
			
			List<Cliente> listaDeCliente = new ArrayList<>();
			Cliente cliente = new Cliente (CPF, Nome, chegada, saida);
			listaDeCliente.add(cliente);
			
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {						
				if(!CPF.equals(clientes.getCpf()) && cont == 0) {
					ProcessadorDeArquivo.IncluirCliente(listaDeCliente);
					cont = 1;
				}
			}
			ProcessadorDeArquivo.IncluirReservaQuartoSolteiro(listaDeCliente);
			System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
		}else if(escolha == 2){
			cls.limpar();
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			int cont = 0;
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {
				if(CPF.equals(clientes.getCpf())) {
					List<Cliente> listaDeCliente = new ArrayList<>();
					Cliente cliente = new Cliente (CPF, clientes.getNome(), chegada, saida);
					listaDeCliente.add(cliente);
					ProcessadorDeArquivo.IncluirReservaQuartoSolteiro(listaDeCliente);
					System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
					cont = 1;
				}
			}
			if(cont == 0) {
				System.out.println("Cliente não CADASTRADO, refaça a solicitação de RESERVA!");
			}
		}else {
			System.out.println("Opção INVÁLIDA");
		}
	}
	
	public static void case2(Scanner leitor, int escolha, String chegada, String saida) {
		cls.limpar();
		System.out.println("--------------------------------------------------------");
		System.out.println("(1) Cadastrar");
		System.out.println("(2) Já sou CADASTRADO");
		escolha = leitor.nextInt();
		if(escolha == 1) {
			cls.limpar();
			int cont = 0;
			System.out.println("-------------------Cadastro do Cliente------------------");
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			System.out.println("Digite seu NOME: ");
			String Nome = leitor.nextLine();
			
			List<Cliente> listaDeCliente = new ArrayList<>();
			Cliente cliente = new Cliente (CPF, Nome, chegada, saida);
			listaDeCliente.add(cliente);
			
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {						
				if(!CPF.equals(clientes.getCpf()) && cont == 0) {
					ProcessadorDeArquivo.IncluirCliente(listaDeCliente);
					cont = 1;
				}
			}
			ProcessadorDeArquivo.IncluirReservaQuartoSolteiroDuplo(listaDeCliente);
			System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
		}else if(escolha == 2){
			cls.limpar();
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			int cont = 0;
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {
				if(CPF.equals(clientes.getCpf())) {
					List<Cliente> listaDeCliente = new ArrayList<>();
					Cliente cliente = new Cliente (CPF, clientes.getNome(), chegada, saida);
					listaDeCliente.add(cliente);
					ProcessadorDeArquivo.IncluirReservaQuartoSolteiroDuplo(listaDeCliente);
					System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
					cont = 1;
				}
			}
			if(cont == 0) {
				System.out.println("Cliente não CADASTRADO, refaça a solicitação de RESERVA!");
			}
		}else {
			System.out.println("Opção INVÁLIDA");
		}
	}
	
	public static void case3(Scanner leitor, int escolha, String chegada, String saida) {
		cls.limpar();
		System.out.println("--------------------------------------------------------");
		System.out.println("(1) Cadastrar");
		System.out.println("(2) Já sou CADASTRADO");
		escolha = leitor.nextInt();
		if(escolha == 1) {
			cls.limpar();
			int cont = 0;
			System.out.println("-------------------Cadastro do Cliente------------------");
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			System.out.println("Digite seu NOME: ");
			String Nome = leitor.nextLine();
			
			List<Cliente> listaDeCliente = new ArrayList<>();
			Cliente cliente = new Cliente (CPF, Nome, chegada, saida);
			listaDeCliente.add(cliente);
			
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {						
				if(!CPF.equals(clientes.getCpf()) && cont == 0) {
					ProcessadorDeArquivo.IncluirCliente(listaDeCliente);
					cont = 1;
				}
			}
			ProcessadorDeArquivo.IncluirReservaQuartoCasal(listaDeCliente);
			System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
		}else if(escolha == 2){
			cls.limpar();
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			int cont = 0;
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {
				if(CPF.equals(clientes.getCpf())) {
					List<Cliente> listaDeCliente = new ArrayList<>();
					Cliente cliente = new Cliente (CPF, clientes.getNome(), chegada, saida);
					listaDeCliente.add(cliente);
					ProcessadorDeArquivo.IncluirReservaQuartoCasal(listaDeCliente);
					System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
					cont = 1;
				}
			}
			if(cont == 0) {
				System.out.println("Cliente não CADASTRADO, refaça a solicitação de RESERVA!");
			}
		}else {
			System.out.println("Opção INVÁLIDA");
		}
	}
	
	public static void case4(Scanner leitor, int escolha, String chegada, String saida) {
		cls.limpar();
		System.out.println("--------------------------------------------------------");
		System.out.println("(1) Cadastrar");
		System.out.println("(2) Já sou CADASTRADO");
		escolha = leitor.nextInt();
		if(escolha == 1) {
			cls.limpar();
			int cont = 0;
			System.out.println("-------------------Cadastro do Cliente------------------");
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			System.out.println("Digite seu NOME: ");
			String Nome = leitor.nextLine();
			
			List<Cliente> listaDeCliente = new ArrayList<>();
			Cliente cliente = new Cliente (CPF, Nome, chegada, saida);
			listaDeCliente.add(cliente);
			
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {						
				if(!CPF.equals(clientes.getCpf()) && cont == 0) {
					ProcessadorDeArquivo.IncluirCliente(listaDeCliente);
					cont = 1;
				}
			}
			ProcessadorDeArquivo.IncluirReservaQuartoCasalSolteiro(listaDeCliente);
			System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
		}else if(escolha == 2){
			cls.limpar();
			leitor.nextLine();
			System.out.println("Digite seu CPF: ");
			String CPF = leitor.nextLine();
			int cont = 0;
			List<Cliente> listaDeClientes = ProcessadorDeArquivo.listarCliente();
			for (Cliente clientes : listaDeClientes) {
				if(CPF.equals(clientes.getCpf())) {
					List<Cliente> listaDeCliente = new ArrayList<>();
					Cliente cliente = new Cliente (CPF, clientes.getNome(), chegada, saida);
					listaDeCliente.add(cliente);
					ProcessadorDeArquivo.IncluirReservaQuartoCasalSolteiro(listaDeCliente);
					System.out.println("-------------------Reserva Realizada com Sucesso!------------------");
					cont = 1;
				}
			}
			if(cont == 0) {
				System.out.println("Cliente não CADASTRADO, refaça a solicitação de RESERVA!");
			}
		}else {
			System.out.println("Opção INVÁLIDA");
		}
	}
}