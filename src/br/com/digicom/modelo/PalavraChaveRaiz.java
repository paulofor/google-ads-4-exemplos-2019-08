package br.com.digicom.modelo;

import com.strongloop.android.loopback.Model;

public class PalavraChaveRaiz extends Model{

	private String palavra;
	private String dataUltimaAtualizacao;
	private Integer ativo;


	
	
	public String getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	
	
}
