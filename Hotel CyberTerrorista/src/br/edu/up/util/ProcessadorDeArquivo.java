package br.edu.up.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import br.edu.up.dominio.Cliente;
import br.edu.up.dominio.QuartoCasal;
import br.edu.up.dominio.QuartoCasalSolteiro;
import br.edu.up.dominio.QuartoSolteiro;
import br.edu.up.dominio.QuartoSolteiroDuplo;

public class ProcessadorDeArquivo {
	
	private static List<Cliente> ListaDeClientes;
	private static List<QuartoSolteiro> ListaDeQuartoSolteiro;
	private static List<QuartoSolteiroDuplo> ListaDeQuartoSolteiroDuplo;
	private static List<QuartoCasal> ListaDeQuartoCasal;
	private static List<QuartoCasalSolteiro> ListaDeQuartoCasalSolteiro;
	private static String url = "jdbc:sqlite:C:\\Users\\Rafael\\Desktop\\Java+BD\\hotel.db";
	
	static {
		ListaDeClientes = new ArrayList<Cliente>();
		ListaDeClientes = carregarListaDeCliente();
		ListaDeQuartoSolteiro = new ArrayList<QuartoSolteiro>();
		ListaDeQuartoSolteiro = carregarListaDeQuartoSolteiro();
		ListaDeQuartoSolteiroDuplo = new ArrayList<QuartoSolteiroDuplo>();
		ListaDeQuartoSolteiroDuplo = carregarListaDeQuartoSolteiroDuplo();
		ListaDeQuartoCasal = new ArrayList<QuartoCasal>();
		ListaDeQuartoCasal = carregarListaDeQuartoCasal();
		ListaDeQuartoCasalSolteiro = new ArrayList<QuartoCasalSolteiro>();
		ListaDeQuartoCasalSolteiro = carregarListaDeQuartoCasalSolteiro();
	}
	
	public static void IncluirCliente(List<Cliente> listaDeClientes) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			for(Cliente cliente: listaDeClientes) {
				con = DriverManager.getConnection(url);
				String sql = "insert into clientes (cpf, nome) values (?, ?);";
				executor = con.prepareStatement(sql);
				executor.setString(1, cliente.getCpf());
				executor.setString(2, cliente.getNome());
				executor.execute();
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
		
	}

	public static void IncluirReservaQuartoSolteiro(List<Cliente> listaDeCliente) {
		PreparedStatement executor = null;
		Connection con = null;
		int i = 1;
		int cont = 0;
		String menorEntrada = null;
		String menorSaida = null;
		int menorNumeroQuarto = 0;
		try {
			ListaDeQuartoSolteiro = ProcessadorDeArquivo.carregarListaDeQuartoSolteiro();
			List<QuartoSolteiro> ListaSolteiroClone = new ArrayList<>();
			for (QuartoSolteiro quartoSolteiro: ListaDeQuartoSolteiro) {
				int id = quartoSolteiro.getId();
				String cpf = quartoSolteiro.getCpf();
				String nome = quartoSolteiro.getNome();
				String entrada = quartoSolteiro.getEntrada();
				String saida = quartoSolteiro.getSaida();
				int numero_do_quarto = quartoSolteiro.getNumero_do_quarto();
				int checkIn = quartoSolteiro.getCheckIn();
				String itemConsumido = quartoSolteiro.getItemConsumido();
				Double valorDouble = quartoSolteiro.getValorConsumido();
				QuartoSolteiro quartoSolteiro2 = new QuartoSolteiro(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorDouble);
				ListaSolteiroClone.add(quartoSolteiro2);
			}
			for(Cliente cliente : listaDeCliente) {
				for (QuartoSolteiro quartoSolteiro: ListaDeQuartoSolteiro) {
					for(QuartoSolteiro quartoSolteiro1: ListaSolteiroClone) {
						if(quartoSolteiro.getNome() != null && quartoSolteiro1.getNome() != null && quartoSolteiro.getNumero_do_quarto() == i && quartoSolteiro1.getNumero_do_quarto() == i) {
							int menorData = 0;
							menorData = ProcessadorDeArquivo.CalendarioComparar(quartoSolteiro.getEntrada(), quartoSolteiro1.getEntrada());
							if(menorData == 0) {
								menorEntrada = quartoSolteiro1.getEntrada();
								menorSaida = quartoSolteiro1.getSaida();
								menorNumeroQuarto = quartoSolteiro1.getNumero_do_quarto();
							}
						}
					}
					i++;
				}
				int disponivel = 0;
				if(menorEntrada != null && menorSaida != null) {
					disponivel = ProcessadorDeArquivo.CalendarioReserva(cliente.getEntrada(), cliente.getSaida(), menorEntrada, menorSaida);
				}
				if(disponivel == 1 && cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "insert into quarto_solteiro (cpf, nome, entrada, saida, numero_do_quarto, checkIn, valor_consumido) values (?, ?, ?, ?, ?, ?, ?);";
					executor = con.prepareStatement(sql);
					executor.setString(1, cliente.getCpf());
					executor.setString(2, cliente.getNome());
					executor.setString(3, cliente.getEntrada());
					executor.setString(4, cliente.getSaida());
					executor.setInt(5, menorNumeroQuarto);
					executor.setInt(6, 0);
					executor.setDouble(7, 0);
					executor.execute();
					cont = 1;
				}else if(disponivel == 0 && cont == 0){
					i = 1;
					for (QuartoSolteiro quartoSolteiro: ListaDeQuartoSolteiro) {
						if(quartoSolteiro.getNome() == null && cont == 0) {
							con = DriverManager.getConnection(url);
							String sql = "update quarto_solteiro set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, valor_consumido = ? where id = ?;";
							executor = con.prepareStatement(sql);
							executor.setString(1, cliente.getCpf());
							executor.setString(2, cliente.getNome());
							executor.setString(3, cliente.getEntrada());
							executor.setString(4, cliente.getSaida());
							executor.setInt(5, 0);
							executor.setDouble(6, 0);
							executor.setInt(7, i);
							executor.execute();
							cont = 1;
						}
						i++;
					}
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
	}
	
	public static void IncluirReservaQuartoSolteiroDuplo(List<Cliente> listaDeCliente) {
		PreparedStatement executor = null;
		Connection con = null;
		int i = 1;
		int cont = 0;
		String menorEntrada = null;
		String menorSaida = null;
		int menorNumeroQuarto = 0;
		try {
			ListaDeQuartoSolteiroDuplo = ProcessadorDeArquivo.carregarListaDeQuartoSolteiroDuplo();
			List<QuartoSolteiroDuplo> ListaSolteiroDuploClone = new ArrayList<>();
			for (QuartoSolteiroDuplo quartoSolteiroDuplo: ListaDeQuartoSolteiroDuplo) {
				int id = quartoSolteiroDuplo.getId();
				String cpf = quartoSolteiroDuplo.getCpf();
				String nome = quartoSolteiroDuplo.getNome();
				String entrada = quartoSolteiroDuplo.getEntrada();
				String saida = quartoSolteiroDuplo.getSaida();
				int numero_do_quarto = quartoSolteiroDuplo.getNumero_do_quarto();
				int checkIn = quartoSolteiroDuplo.getCheckIn();
				String itemConsumido = quartoSolteiroDuplo.getItemConsumido();
				Double valorDouble = quartoSolteiroDuplo.getValorConsumido();
				QuartoSolteiroDuplo quartoSolteiroDuplo2 = new QuartoSolteiroDuplo(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorDouble);
				ListaSolteiroDuploClone.add(quartoSolteiroDuplo2);
			}
			for(Cliente cliente : listaDeCliente) {
				for (QuartoSolteiroDuplo quartoSolteiroDuplo: ListaDeQuartoSolteiroDuplo) {
					for(QuartoSolteiroDuplo quartoSolteiroDuplo1: ListaSolteiroDuploClone) {
						if(quartoSolteiroDuplo.getNome() != null && quartoSolteiroDuplo1.getNome() != null && quartoSolteiroDuplo.getNumero_do_quarto() == i && quartoSolteiroDuplo1.getNumero_do_quarto() == i) {
							int menorData = 0;
							menorData = ProcessadorDeArquivo.CalendarioComparar(quartoSolteiroDuplo.getEntrada(), quartoSolteiroDuplo1.getEntrada());
							if(menorData == 0) {
								menorEntrada = quartoSolteiroDuplo1.getEntrada();
								menorSaida = quartoSolteiroDuplo1.getSaida();
								menorNumeroQuarto = quartoSolteiroDuplo1.getNumero_do_quarto();
							}
						}
					}
					i++;
				}
				int disponivel = 0;
				if(menorEntrada != null && menorSaida != null) {
					disponivel = ProcessadorDeArquivo.CalendarioReserva(cliente.getEntrada(), cliente.getSaida(), menorEntrada, menorSaida);
				}
				if(disponivel == 1 && cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "insert into quarto_solteiro_duplo (cpf, nome, entrada, saida, numero_do_quarto, checkIn, valor_consumido) values (?, ?, ?, ?, ?, ?, ?);";
					executor = con.prepareStatement(sql);
					executor.setString(1, cliente.getCpf());
					executor.setString(2, cliente.getNome());
					executor.setString(3, cliente.getEntrada());
					executor.setString(4, cliente.getSaida());
					executor.setInt(5, menorNumeroQuarto);
					executor.setInt(6, 0);
					executor.setDouble(7, 0);
					executor.execute();
					cont = 1;
				}else if(disponivel == 0 && cont == 0){
					i = 1;
					for (QuartoSolteiroDuplo quartoSolteiroDuplo: ListaDeQuartoSolteiroDuplo) {
						if(quartoSolteiroDuplo.getNome() == null && cont == 0) {
							con = DriverManager.getConnection(url);
							String sql = "update quarto_solteiro_duplo set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, valor_consumido = ? where id = ?;";
							executor = con.prepareStatement(sql);
							executor.setString(1, cliente.getCpf());
							executor.setString(2, cliente.getNome());
							executor.setString(3, cliente.getEntrada());
							executor.setString(4, cliente.getSaida());
							executor.setInt(5, 0);
							executor.setDouble(6, 0);
							executor.setInt(7, i);
							executor.execute();
							cont = 1;
						}
						i++;
					}
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
	}
	
	public static void IncluirReservaQuartoCasal(List<Cliente> listaDeCliente) {
		PreparedStatement executor = null;
		Connection con = null;
		int i = 1;
		int cont = 0;
		String menorEntrada = null;
		String menorSaida = null;
		int menorNumeroQuarto = 0;
		try {
			ListaDeQuartoCasal = ProcessadorDeArquivo.carregarListaDeQuartoCasal();
			List<QuartoCasal> ListaCasalClone = new ArrayList<>();
			for (QuartoCasal quartoCasal: ListaDeQuartoCasal) {
				int id = quartoCasal.getId();
				String cpf = quartoCasal.getCpf();
				String nome = quartoCasal.getNome();
				String entrada = quartoCasal.getEntrada();
				String saida = quartoCasal.getSaida();
				int numero_do_quarto = quartoCasal.getNumero_do_quarto();
				int checkIn = quartoCasal.getCheckIn();
				String itemConsumido = quartoCasal.getItemConsumido();
				Double valorDouble = quartoCasal.getValorConsumido();
				QuartoCasal quartoCasal2 = new QuartoCasal(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorDouble);
				ListaCasalClone.add(quartoCasal2);
			}
			for(Cliente cliente : listaDeCliente) {
				for (QuartoCasal quartoCasal: ListaDeQuartoCasal) {
					for(QuartoCasal quartoCasal1: ListaCasalClone) {
						if(quartoCasal.getNome() != null && quartoCasal1.getNome() != null && quartoCasal.getNumero_do_quarto() == i && quartoCasal1.getNumero_do_quarto() == i) {
							int menorData = 0;
							menorData = ProcessadorDeArquivo.CalendarioComparar(quartoCasal.getEntrada(), quartoCasal1.getEntrada());
							if(menorData == 0) {
								menorEntrada = quartoCasal1.getEntrada();
								menorSaida = quartoCasal1.getSaida();
								menorNumeroQuarto = quartoCasal1.getNumero_do_quarto();
							}
						}
					}
					i++;
				}
				int disponivel = 0;
				if(menorEntrada != null && menorSaida != null) {
					disponivel = ProcessadorDeArquivo.CalendarioReserva(cliente.getEntrada(), cliente.getSaida(), menorEntrada, menorSaida);
				}
				if(disponivel == 1 && cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "insert into quarto_casal (cpf, nome, entrada, saida, numero_do_quarto, checkIn, valor_consumido) values (?, ?, ?, ?, ?, ?, ?);";
					executor = con.prepareStatement(sql);
					executor.setString(1, cliente.getCpf());
					executor.setString(2, cliente.getNome());
					executor.setString(3, cliente.getEntrada());
					executor.setString(4, cliente.getSaida());
					executor.setInt(5, menorNumeroQuarto);
					executor.setInt(6, 0);
					executor.setDouble(7, 0);
					executor.execute();
					cont = 1;
				}else if(disponivel == 0 && cont == 0){
					i = 1;
					for (QuartoCasal quartoCasal: ListaDeQuartoCasal) {
						if(quartoCasal.getNome() == null && cont == 0) {
							con = DriverManager.getConnection(url);
							String sql = "update quarto_casal set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, valor_consumido = ? where id = ?;";
							executor = con.prepareStatement(sql);
							executor.setString(1, cliente.getCpf());
							executor.setString(2, cliente.getNome());
							executor.setString(3, cliente.getEntrada());
							executor.setString(4, cliente.getSaida());
							executor.setInt(5, 0);
							executor.setDouble(6, 0);
							executor.setInt(7, i);
							executor.execute();
							cont = 1;
						}
						i++;
					}
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
	}
	
	public static void IncluirReservaQuartoCasalSolteiro(List<Cliente> listaDeCliente) {
		PreparedStatement executor = null;
		Connection con = null;
		int i = 1;
		int cont = 0;
		String menorEntrada = null;
		String menorSaida = null;
		int menorNumeroQuarto = 0;
		try {
			ListaDeQuartoCasalSolteiro = ProcessadorDeArquivo.carregarListaDeQuartoCasalSolteiro();
			List<QuartoCasalSolteiro> ListaCasalSolteiroClone = new ArrayList<>();
			for (QuartoCasalSolteiro quartoCasalSolteiro: ListaDeQuartoCasalSolteiro) {
				int id = quartoCasalSolteiro.getId();
				String cpf = quartoCasalSolteiro.getCpf();
				String nome = quartoCasalSolteiro.getNome();
				String entrada = quartoCasalSolteiro.getEntrada();
				String saida = quartoCasalSolteiro.getSaida();
				int numero_do_quarto = quartoCasalSolteiro.getNumero_do_quarto();
				int checkIn = quartoCasalSolteiro.getCheckIn();
				String itemConsumido = quartoCasalSolteiro.getItemConsumido();
				Double valorDouble = quartoCasalSolteiro.getValorConsumido();
				QuartoCasalSolteiro quartoCasalSolteiro2 = new QuartoCasalSolteiro(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorDouble);
				ListaCasalSolteiroClone.add(quartoCasalSolteiro2);
			}
			for(Cliente cliente : listaDeCliente) {
				for (QuartoCasalSolteiro quartoCasalSolteiro: ListaDeQuartoCasalSolteiro) {
					for(QuartoCasalSolteiro quartoCasalSolteiro1: ListaCasalSolteiroClone) {
						if(quartoCasalSolteiro.getNome() != null && quartoCasalSolteiro1.getNome() != null && quartoCasalSolteiro.getNumero_do_quarto() == i && quartoCasalSolteiro1.getNumero_do_quarto() == i) {
							int menorData = 0;
							menorData = ProcessadorDeArquivo.CalendarioComparar(quartoCasalSolteiro.getEntrada(), quartoCasalSolteiro1.getEntrada());
							if(menorData == 0) {
								menorEntrada = quartoCasalSolteiro1.getEntrada();
								menorSaida = quartoCasalSolteiro1.getSaida();
								menorNumeroQuarto = quartoCasalSolteiro1.getNumero_do_quarto();
							}
						}
					}
					i++;
				}
				int disponivel = 0;
				if(menorEntrada != null && menorSaida != null) {
					disponivel = ProcessadorDeArquivo.CalendarioReserva(cliente.getEntrada(), cliente.getSaida(), menorEntrada, menorSaida);
				}
				if(disponivel == 1 && cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "insert into quarto_casal_solteiro (cpf, nome, entrada, saida, numero_do_quarto, checkIn, valor_consumido) values (?, ?, ?, ?, ?, ?, ?);";
					executor = con.prepareStatement(sql);
					executor.setString(1, cliente.getCpf());
					executor.setString(2, cliente.getNome());
					executor.setString(3, cliente.getEntrada());
					executor.setString(4, cliente.getSaida());
					executor.setInt(5, menorNumeroQuarto);
					executor.setInt(6, 0);
					executor.setDouble(7, 0);
					executor.execute();
					cont = 1;
				}else if(disponivel == 0 && cont == 0){
					i = 1;
					for (QuartoCasalSolteiro quartoCasalSolteiro: ListaDeQuartoCasalSolteiro) {
						if(quartoCasalSolteiro.getNome() == null && cont == 0) {
							con = DriverManager.getConnection(url);
							String sql = "update quarto_casal_solteiro set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, valor_consumido = ? where id = ?;";
							executor = con.prepareStatement(sql);
							executor.setString(1, cliente.getCpf());
							executor.setString(2, cliente.getNome());
							executor.setString(3, cliente.getEntrada());
							executor.setString(4, cliente.getSaida());
							executor.setInt(5, 0);
							executor.setDouble(6, 0);
							executor.setInt(7, i);
							executor.execute();
							cont = 1;
						}
						i++;
					}
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
	}
	
	public static void CheckInSolteiro(List<QuartoSolteiro> listaDeQuartoSolteiros) {
		PreparedStatement executor = null;
		Connection con = null;
		int cont = 0;
		try {
			for (QuartoSolteiro quartoSolteiro : listaDeQuartoSolteiros) {
				if(cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "update quarto_solteiro set checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
					executor = con.prepareStatement(sql);
					executor.setInt(1, 1);
					executor.setString(2, "");
					executor.setDouble(3, 0.0);
					executor.setInt(4, quartoSolteiro.getId());			
					executor.execute();
					cont = 1;
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckInSolteiroDuplo(List<QuartoSolteiroDuplo> listaDeQuartoSolteiroDuplos) {
		PreparedStatement executor = null;
		Connection con = null;
		int cont = 0;
		try {
			for (QuartoSolteiroDuplo quartoSolteiroDuplo : listaDeQuartoSolteiroDuplos) {
				if(cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "update quarto_solteiro_duplo set checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
					executor = con.prepareStatement(sql);
					executor.setInt(1, 1);
					executor.setString(2, "");
					executor.setDouble(3, 0.0);
					executor.setInt(4, quartoSolteiroDuplo.getId());			
					executor.execute();
					cont = 1;
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckInCasal(List<QuartoCasal> listaDeQuartoCasals) {
		PreparedStatement executor = null;
		Connection con = null;
		int cont = 0;
		try {
			for (QuartoCasal quartoCasal : listaDeQuartoCasals) {
				if(cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "update quarto_casal set checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
					executor = con.prepareStatement(sql);
					executor.setInt(1, 1);
					executor.setString(2, "");
					executor.setDouble(3, 0.0);
					executor.setInt(4, quartoCasal.getId());			
					executor.execute();
					cont = 1;
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckInCasalSolteiro(List<QuartoCasalSolteiro> listaDeQuartoCasalSolteiros) {
		PreparedStatement executor = null;
		Connection con = null;
		int cont = 0;
		try {
			for (QuartoCasalSolteiro quartoCasalSolteiro : listaDeQuartoCasalSolteiros) {
				if(cont == 0) {
					con = DriverManager.getConnection(url);
					String sql = "update quarto_casal_solteiro set checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
					executor = con.prepareStatement(sql);
					executor.setInt(1, 1);
					executor.setString(2, "");
					executor.setDouble(3, 0.0);
					executor.setInt(4, quartoCasalSolteiro.getId());			
					executor.execute();
					cont = 1;
				}
			}
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	private static List<Cliente> carregarListaDeCliente() {
		
		return listarCliente();
	}

	static List<Cliente> listarCliente() {
		List<Cliente> listaDeRetornoCliente = new ArrayList<>();
		Statement executor;
		Connection con;
		
		try {
			con = DriverManager.getConnection(url);
			executor = con.createStatement();
			
			String sql = "select * from clientes;";
			ResultSet resultado = executor.executeQuery(sql);
				
			while(resultado.next()) {
				String cpf = resultado.getString("cpf");
				String nome = resultado.getString("nome");
				
				Cliente cliente = new Cliente(cpf, nome);
				listaDeRetornoCliente.add(cliente);
			}
			
			executor.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
		return listaDeRetornoCliente;
	}

	private static List<QuartoSolteiro> carregarListaDeQuartoSolteiro() {
		
		return listarQuartoSolteiro();
	}

	static List<QuartoSolteiro> listarQuartoSolteiro(){
		List<QuartoSolteiro> listaDeRetornoQuartoSolteiro = new ArrayList<>();
		Statement executor;
		Connection con;
		
		try {
			con = DriverManager.getConnection(url);
			executor = con.createStatement();
			
			String sql = "select * from quarto_solteiro;";
			ResultSet resultado = executor.executeQuery(sql);
				
			while(resultado.next()) {
				int id = resultado.getInt("id");
				String cpf = resultado.getString("cpf");
				String nome = resultado.getString("nome");
				String entrada = resultado.getString("entrada");
				String saida = resultado.getString("saida");
				int numero_do_quarto = resultado.getInt("numero_do_quarto");
				int checkIn = resultado.getInt("checkIn");
				String itemConsumido = resultado.getString("item_consumido");
				Double valorConsumido = resultado.getDouble("valor_consumido");
				QuartoSolteiro quartoSolteiro = new QuartoSolteiro(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorConsumido);
				listaDeRetornoQuartoSolteiro.add(quartoSolteiro);
			}
			
			executor.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
		return listaDeRetornoQuartoSolteiro;	
	}
	
	private static List<QuartoSolteiroDuplo> carregarListaDeQuartoSolteiroDuplo() {
		
		return listarQuartoSolteiroDuplo();
	}

	static List<QuartoSolteiroDuplo> listarQuartoSolteiroDuplo(){
		List<QuartoSolteiroDuplo> listaDeRetornoQuartoSolteiroDuplo = new ArrayList<>();
		Statement executor;
		Connection con;
		
		try {
			con = DriverManager.getConnection(url);
			executor = con.createStatement();
			
			String sql = "select * from quarto_solteiro_duplo;";
			ResultSet resultado = executor.executeQuery(sql);
				
			while(resultado.next()) {
				int id = resultado.getInt("id");
				String cpf = resultado.getString("cpf");
				String nome = resultado.getString("nome");
				String entrada = resultado.getString("entrada");
				String saida = resultado.getString("saida");
				int numero_do_quarto = resultado.getInt("numero_do_quarto");
				int checkIn = resultado.getInt("checkIn");
				String itemConsumido = resultado.getString("item_consumido");
				Double valorConsumido = resultado.getDouble("valor_consumido");
				QuartoSolteiroDuplo quartoSolteiroDuplo = new QuartoSolteiroDuplo(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorConsumido);
				listaDeRetornoQuartoSolteiroDuplo.add(quartoSolteiroDuplo);
			}
			
			executor.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
		return listaDeRetornoQuartoSolteiroDuplo;	
	}
	
	private static List<QuartoCasal> carregarListaDeQuartoCasal() {
		
		return listarQuartoCasal();
	}

	static List<QuartoCasal> listarQuartoCasal(){
		List<QuartoCasal> listaDeRetornoQuartoCasal = new ArrayList<>();
		Statement executor;
		Connection con;
		
		try {
			con = DriverManager.getConnection(url);
			executor = con.createStatement();
			
			String sql = "select * from quarto_casal;";
			ResultSet resultado = executor.executeQuery(sql);
				
			while(resultado.next()) {
				int id = resultado.getInt("id");
				String cpf = resultado.getString("cpf");
				String nome = resultado.getString("nome");
				String entrada = resultado.getString("entrada");
				String saida = resultado.getString("saida");
				int numero_do_quarto = resultado.getInt("numero_do_quarto");
				int checkIn = resultado.getInt("checkIn");
				String itemConsumido = resultado.getString("item_consumido");
				Double valorConsumido = resultado.getDouble("valor_consumido");
				QuartoCasal quartoCasal = new QuartoCasal(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorConsumido);
				listaDeRetornoQuartoCasal.add(quartoCasal);
			}
			
			executor.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
		return listaDeRetornoQuartoCasal;	
	}
	
	private static List<QuartoCasalSolteiro> carregarListaDeQuartoCasalSolteiro() {
		
		return listarQuartoCasalSolteiro();
	}

	static List<QuartoCasalSolteiro> listarQuartoCasalSolteiro(){
		List<QuartoCasalSolteiro> listaDeRetornoQuartoCasalSolteiro = new ArrayList<>();
		Statement executor;
		Connection con;
		
		try {
			con = DriverManager.getConnection(url);
			executor = con.createStatement();
			
			String sql = "select * from quarto_casal_solteiro;";
			ResultSet resultado = executor.executeQuery(sql);
				
			while(resultado.next()) {
				int id = resultado.getInt("id");
				String cpf = resultado.getString("cpf");
				String nome = resultado.getString("nome");
				String entrada = resultado.getString("entrada");
				String saida = resultado.getString("saida");
				int numero_do_quarto = resultado.getInt("numero_do_quarto");
				int checkIn = resultado.getInt("checkIn");
				String itemConsumido = resultado.getString("item_consumido");
				Double valorConsumido = resultado.getDouble("valor_consumido");
				QuartoCasalSolteiro quartoCasalSolteiro = new QuartoCasalSolteiro(id, cpf, nome, entrada, saida, numero_do_quarto, checkIn, itemConsumido, valorConsumido);
				listaDeRetornoQuartoCasalSolteiro.add(quartoCasalSolteiro);
			}
			
			executor.close();
			con.close();
			
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo");
			//e.printStackTrace();
		}
		return listaDeRetornoQuartoCasalSolteiro;	
	}
	
	public static List<QuartoSolteiro> carregarPesquisaQuartoSolteiro(String cpf){
		
		return PesquisarcheckInSolteiro(cpf);
	}
	
	static List<QuartoSolteiro> PesquisarcheckInSolteiro(String cpf) {
		List<QuartoSolteiro> ListaCheckInRetorno = new ArrayList<>();
		ListaDeQuartoSolteiro = ProcessadorDeArquivo.carregarListaDeQuartoSolteiro();
		for (QuartoSolteiro quartoSolteiro : ListaDeQuartoSolteiro) {
			if(cpf.equals(quartoSolteiro.getCpf())){
				ListaCheckInRetorno.add(quartoSolteiro);
			}
		}
		return ListaCheckInRetorno;
	}
	
	public static List<QuartoSolteiroDuplo> carregarPesquisaQuartoSolteiroDuplo(String cpf){
		
		return PesquisarcheckInInSolteiroDuplo(cpf);
	}
	
	static List<QuartoSolteiroDuplo> PesquisarcheckInInSolteiroDuplo(String cpf) {
		List<QuartoSolteiroDuplo> ListacheckInInRetorno = new ArrayList<>();
		ListaDeQuartoSolteiroDuplo = ProcessadorDeArquivo.carregarListaDeQuartoSolteiroDuplo();
		for (QuartoSolteiroDuplo quartoSolteiroDuplo : ListaDeQuartoSolteiroDuplo) {
			if(cpf.equals(quartoSolteiroDuplo.getCpf())){
				ListacheckInInRetorno.add(quartoSolteiroDuplo);
			}
		}
		return ListacheckInInRetorno;
	}
	
	public static List<QuartoCasal> carregarPesquisaQuartoCasal(String cpf){
		
		return PesquisarcheckInCasal(cpf);
	}
	
	static List<QuartoCasal> PesquisarcheckInCasal(String cpf) {
		List<QuartoCasal> ListacheckInInRetorno = new ArrayList<>();
		ListaDeQuartoCasal = ProcessadorDeArquivo.carregarListaDeQuartoCasal();
		for (QuartoCasal quartoCasal : ListaDeQuartoCasal) {
			if(cpf.equals(quartoCasal.getCpf())){
				ListacheckInInRetorno.add(quartoCasal);
			}
		}
		return ListacheckInInRetorno;
	}
	
	public static List<QuartoCasalSolteiro> carregarPesquisaQuartoCasalSolteiro(String cpf){
		
		return PesquisarcheckInCasalSolteiro(cpf);
	}
	
	static List<QuartoCasalSolteiro> PesquisarcheckInCasalSolteiro(String cpf) {
		List<QuartoCasalSolteiro> ListacheckInInRetorno = new ArrayList<>();
		ListaDeQuartoCasalSolteiro = ProcessadorDeArquivo.carregarListaDeQuartoCasalSolteiro();
		for (QuartoCasalSolteiro quartoCasalSolteiro : ListaDeQuartoCasalSolteiro) {
			if(cpf.equals(quartoCasalSolteiro.getCpf())){
				ListacheckInInRetorno.add(quartoCasalSolteiro);
			}
		}
		return ListacheckInInRetorno;
	}
	
	public static void AtualizarHospedagemSolteiro(String itemConsumido, Double valorConsumido, int i) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_solteiro set item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, itemConsumido);
			executor.setDouble(2, valorConsumido);
			executor.setInt(3, i);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
		
	}
	
	public static void AtualizarHospedagemSolteiroDuplo(String itemConsumido, Double valorConsumido, int i) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_solteiro_duplo set item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, itemConsumido);
			executor.setDouble(2, valorConsumido);
			executor.setInt(3, i);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
		
	}
	
	public static void AtualizarHospedagemCasal(String itemConsumido, Double valorConsumido, int i) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_casal set item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, itemConsumido);
			executor.setDouble(2, valorConsumido);
			executor.setInt(3, i);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void AtualizarHospedagemCasalSolteiro(String itemConsumido, Double valorConsumido, int i) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_casal_solteiro set item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, itemConsumido);
			executor.setDouble(2, valorConsumido);
			executor.setInt(3, i);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckOutSolteiro(int id) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_solteiro set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, null);
			executor.setString(2, null);
			executor.setString(3, null);
			executor.setString(4, null);
			executor.setInt(5, 0);
			executor.setString(6, "");
			executor.setDouble(7, 0.0);
			executor.setInt(8, id);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckOutSolteiroDuplo(int id) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_solteiro_duplo set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, null);
			executor.setString(2, null);
			executor.setString(3, null);
			executor.setString(4, null);
			executor.setInt(5, 0);
			executor.setString(6, "");
			executor.setDouble(7, 0.0);
			executor.setInt(8, id);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckOutCasal(int id) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_casal set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, null);
			executor.setString(2, null);
			executor.setString(3, null);
			executor.setString(4, null);
			executor.setInt(5, 0);
			executor.setString(6, "");
			executor.setDouble(7, 0.0);
			executor.setInt(8, id);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static void CheckOutCasalSolteiro(int id) {
		PreparedStatement executor = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			String sql = "update quarto_casal_solteiro set cpf = ?, nome = ?, entrada = ?, saida = ?, checkIn = ?, item_consumido = ?, valor_consumido = ? where id = ?;";
			executor = con.prepareStatement(sql);
			executor.setString(1, null);
			executor.setString(2, null);
			executor.setString(3, null);
			executor.setString(4, null);
			executor.setInt(5, 0);
			executor.setString(6, "");
			executor.setDouble(7, 0.0);
			executor.setInt(8, id);			
			executor.execute();
			executor.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Erro na leitura do arquivo: " + e);
			//e.printStackTrace();
		}
	}
	
	public static char Validacao(Scanner leitor, int cont, char escolha) {
		do {
			cont = 0;
			System.out.println("Deseja continuar? (S)Sim (N)Não");
			escolha = leitor.next().charAt(0);
			if (escolha == 'S' || escolha == 's'|| escolha == 'N' || escolha == 'n') {
				cont = 1;
				cls.limpar();
			}else{
				System.out.println("Opção invalida, escolha novamente.");
			}
		}while(cont == 0);
		return escolha;
	}
	
	public static char ValidacaoQuarto(Scanner leitor, int cont, char escolha) {
		do {
			cont = 0;
			System.out.println("Deseja encerrar esse quarto? (S)Sim (N)Não");
			escolha = leitor.next().charAt(0);
			if (escolha == 'S' || escolha == 's'|| escolha == 'N' || escolha == 'n') {
				cont = 1;
				cls.limpar();
			}else{
				System.out.println("Opção invalida, escolha novamente.");
			}
		}while(cont == 0);
		return escolha;
	}
	
	public static int AltaTemporada(String Chegada){
		String dezInicio = "01/12";
		String dezFim = "31/12";
		String janInicio = "01/01";
		String fevFim = "28/02";
		String julInicio = "01/07";
		String julFim = "31/07";
		int temp = 0;
		
		try {
		Calendar altaInicio1 = Calendar.getInstance();
		altaInicio1.setTime(new SimpleDateFormat("dd/MM").parse(dezInicio));
		Calendar altaFim1 = Calendar.getInstance();
	    altaFim1.setTime(new SimpleDateFormat("dd/MM").parse(dezFim));
	    Calendar altaInicio2 = Calendar.getInstance();
		altaInicio2.setTime(new SimpleDateFormat("dd/MM").parse(janInicio));
		Calendar altaFim2 = Calendar.getInstance();
	    altaFim2.setTime(new SimpleDateFormat("dd/MM").parse(fevFim));
	    Calendar chegada = Calendar.getInstance();
	    Calendar altaInicio3 = Calendar.getInstance();
		altaInicio3.setTime(new SimpleDateFormat("dd/MM").parse(julInicio));
		Calendar altaFim3 = Calendar.getInstance();
	    altaFim3.setTime(new SimpleDateFormat("dd/MM").parse(julFim));
		chegada.setTime(new SimpleDateFormat("dd/MM").parse(Chegada));

	    int contagem = ContagemDeAltaTemporada(altaInicio1, altaFim1, altaInicio2, altaFim2, altaInicio3, altaFim3, chegada);
	    temp = contagem;
	    
		} catch (ParseException e) {
			System.out.println("Erro na leitura dos dados!");
			//e.printStackTrace();
		}
		return temp;
    }
    
    private static int ContagemDeAltaTemporada(Calendar altaInicio1, Calendar altaFim1, Calendar altaInicio2, Calendar altaFim2, Calendar altaInicio3, Calendar altaFim3, Calendar chegada) {
        return ContagemDeAltaTemporada(TransformarCalendario(altaInicio1), TransformarCalendario(altaFim1), TransformarCalendario(altaInicio2), TransformarCalendario(altaFim2), TransformarCalendario(altaInicio3), TransformarCalendario(altaFim3), TransformarCalendario(chegada), 0);
    }

    private static int ContagemDeAltaTemporada(Calendar altaInicio1, Calendar altaFim1, Calendar altaInicio2, Calendar altaFim2, Calendar altaInicio3, Calendar altaFim3, Calendar chegada, int count) {
        if ((chegada.compareTo(altaInicio1) >= 0 && chegada.compareTo(altaFim1) < 0) || (chegada.compareTo(altaInicio2) >= 0 && chegada.compareTo(altaFim2) < 0) || (chegada.compareTo(altaInicio3) >= 0 && chegada.compareTo(altaFim3) < 0)) {
            count = 1;
        } else {
            count = 0;
        }
        return count;
    }

	public static int CalendarioDiaria(String Chegada, String Saida){
		
		int diaria = 0;
		
		try {
		Calendar inicio = Calendar.getInstance();
		inicio.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(Chegada));
		Calendar fim = Calendar.getInstance();
	    fim.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(Saida));

	    int contagem = ContagemDeDias(inicio, fim);
	    diaria = contagem;
	    
		} catch (ParseException e) {
			System.out.println("Erro na leitura dos dados!");
			//e.printStackTrace();
		}
		return diaria;
    }

    private static int ContagemDeDias(Calendar inicio, Calendar fim) {
        return ContagemDeDias(TransformarCalendario(inicio), TransformarCalendario(fim), 0);
    }

    private static int ContagemDeDias(Calendar inicio, Calendar fim, int count) {
        if (inicio.compareTo(fim) >= 0) {
            return count;
        } else {
            count += 1;
            inicio.add(Calendar.DAY_OF_WEEK, 1);
            return ContagemDeDias(TransformarCalendario(inicio), TransformarCalendario(fim), count);
        }
    }
    
    public static int CalendarioComparar(String entrada1, String entrada2) {
    	int reserva = 0;
    	try {
    		Calendar inicio = Calendar.getInstance();
    		inicio.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(entrada1));
    	    Calendar entrada = Calendar.getInstance();
    		entrada.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(entrada2));
    	    
    	    int verificacao = Comparacao(entrada, inicio);
    	    reserva = verificacao;
    		} catch (ParseException e) {
    			System.out.println("Erro na leitura dos dados!");
    			//e.printStackTrace();
    		}
    	
		return reserva;
    }
    
    private static int Comparacao(Calendar inicio, Calendar entrada) {
        return Comparacao(TransformarCalendario(inicio), TransformarCalendario(entrada), 0);
    }

    private static int Comparacao(Calendar inicio, Calendar entrada, int count) {
        if (entrada.compareTo(inicio) < 0) {
        	count = 1;
        }else {
        	count = 0;
        }
        return count;
    }
    
    public static int CalendarioReserva(String Chegada, String Saida, String EntradaRegistrada, String SaidaRegistrada) {
    	int reserva = 0;
    	try {
    		Calendar inicio = Calendar.getInstance();
    		inicio.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(EntradaRegistrada));
    		Calendar fim = Calendar.getInstance();
    	    fim.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(SaidaRegistrada));
    	    Calendar entrada = Calendar.getInstance();
    		entrada.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(Chegada));
    		Calendar saida = Calendar.getInstance();
    	    saida.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(Saida));
    	    
    	    int verificacao = ContagemDeDiasReserva(inicio, fim, entrada, saida);
    	    reserva = verificacao;
    		} catch (ParseException e) {
    			System.out.println("Erro na leitura dos dados!");
    			//e.printStackTrace();
    		}
    	
		return reserva;
    }
    
    private static int ContagemDeDiasReserva(Calendar inicio, Calendar fim, Calendar entrada, Calendar saida) {
        return ContagemDeDiasReserva(TransformarCalendario(inicio), TransformarCalendario(fim), TransformarCalendario(entrada), TransformarCalendario(saida), 0);
    }

    private static int ContagemDeDiasReserva(Calendar inicio, Calendar fim, Calendar entrada, Calendar saida, int count) {
        if ((entrada.compareTo(inicio) >= 0 && entrada.compareTo(fim) <= 0) || (saida.compareTo(inicio) >= 0 && saida.compareTo(fim) <= 0)) {
            if(inicio.compareTo(entrada) >= 0 && inicio.compareTo(saida) <= 0) {
            	count = 0;
            }
        } else {
            count = 1;
        }
        return count;
    }
    
    public static int CalendarioCheckIn(String Chegada, String EntradaRegistrada, String SaidaRegistrada) {
    	int reserva = 0;
    	try {
    		Calendar inicio = Calendar.getInstance();
    		inicio.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(EntradaRegistrada));
    		Calendar fim = Calendar.getInstance();
    	    fim.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(SaidaRegistrada));
    	    Calendar entrada = Calendar.getInstance();
    		entrada.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(Chegada));
    		
    	    int verificacao = ContagemDeCheckIn(inicio, fim, entrada);
    	    reserva = verificacao;
    		} catch (ParseException e) {
    			System.out.println("Erro na leitura dos dados!");
    			//e.printStackTrace();
    		}
    	
		return reserva;
    }
    
    private static int ContagemDeCheckIn(Calendar inicio, Calendar fim, Calendar entrada) {
        return ContagemDecheckIn(TransformarCalendario(inicio), TransformarCalendario(fim), TransformarCalendario(entrada), 0);
    }

    private static int ContagemDecheckIn(Calendar inicio, Calendar fim, Calendar entrada, int count) {
        if (entrada.compareTo(inicio) >= 0 && entrada.compareTo(fim) < 0) {
            count = 1;
        } else {
            count = 0;
        }
        return count;
    }

    private static Calendar TransformarCalendario(Calendar calendar) {
        Calendar calTransfomar = (Calendar) calendar.clone();
        calTransfomar.set(GregorianCalendar.HOUR, 0);
        calTransfomar.set(GregorianCalendar.MINUTE, 0);
        calTransfomar.set(GregorianCalendar.SECOND, 0);
        calTransfomar.set(GregorianCalendar.MILLISECOND, 0);
        calTransfomar.set(GregorianCalendar.AM_PM, 0);
        return calTransfomar;
    }
}