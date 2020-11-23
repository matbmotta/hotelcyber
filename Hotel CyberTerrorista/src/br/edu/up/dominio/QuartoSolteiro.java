package br.edu.up.dominio;

public class QuartoSolteiro {
	
	private int id;
	private String cpf;
	private String nome;
	private String entrada;
	private String saida;
	private int numero_do_quarto;
	private int checkIn;
	private String itemConsumido;
	private Double valorConsumido;
	
	public QuartoSolteiro(int id, String cpf, String nome, String entrada, String saida, int numero_do_quarto, int checkIn, String itemConsumido, Double valorConsumido) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.entrada = entrada;
		this.saida = saida;
		this.numero_do_quarto = numero_do_quarto;
		this.checkIn = checkIn;
		this.itemConsumido = itemConsumido;
		this.valorConsumido = valorConsumido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public int getNumero_do_quarto() {
		return numero_do_quarto;
	}

	public void setNumero_do_quarto(int numero_do_quarto) {
		this.numero_do_quarto = numero_do_quarto;
	}

	public int getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(int checkIn) {
		this.checkIn = checkIn;
	}

	public String getItemConsumido() {
		return itemConsumido;
	}

	public void setItemConsumido(String itemConsumido) {
		this.itemConsumido = itemConsumido;
	}

	public Double getValorConsumido() {
		return valorConsumido;
	}

	public void setValorConsumido(Double valorConsumido) {
		this.valorConsumido = valorConsumido;
	}
}