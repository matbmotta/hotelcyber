package br.edu.up.dominio;

public class Cliente {
	
	private int id;
	private String cpf;
	private String nome;
	private String entrada;
	private String saida;
	
	public Cliente(String cpf, String nome, String entrada, String saida) {
		this.cpf = cpf;
		this.nome = nome;
		this.entrada = entrada;
		this.saida = saida;
	}
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
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
}