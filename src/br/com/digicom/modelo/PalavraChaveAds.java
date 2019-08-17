package br.com.digicom.modelo;

import com.strongloop.android.loopback.Model;

public class PalavraChaveAds extends Model {

	
	private Integer id;
	private String palavra;
	private Integer ganhoDorMySqlId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPalavra() {
		return palavra;
	}
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	public Integer getGanhoDorMySqlId() {
		return ganhoDorMySqlId;
	}
	public void setGanhoDorMySqlId(Integer ganhoDorMySqlId) {
		this.ganhoDorMySqlId = ganhoDorMySqlId;
	}
	
	
}
