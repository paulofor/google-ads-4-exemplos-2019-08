package br.com.digicom.modelo;

import com.strongloop.android.loopback.Model;

public class VersaoApp extends Model{

	private String nome;
	private String objetivo;
	private String codigoVersao;
	private String pacoteApp;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getCodigoVersao() {
		return codigoVersao;
	}
	public void setCodigoVersao(String codigoVersao) {
		this.codigoVersao = codigoVersao;
	}
	public String getPacoteApp() {
		return pacoteApp;
	}
	public void setPacoteApp(String pacoteApp) {
		this.pacoteApp = pacoteApp;
	}
	
	
	
	
}
