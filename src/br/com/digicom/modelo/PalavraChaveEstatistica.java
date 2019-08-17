package br.com.digicom.modelo;

import org.json.JSONException;
import org.json.JSONObject;

import com.strongloop.android.loopback.Model;

public class PalavraChaveEstatistica extends Model implements ItemListaLoopback {

	private String dataConsulta;
	private Double mediaCpc;
	private Long volumePesquisa;
	private Double indiceCompeticao;

	private Long palavraChaveRaizId;
	private String palavraChaveGoogleId;
	
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject saida = new JSONObject();
		saida.put("palavraChaveGoogleId", palavraChaveGoogleId);
		saida.put("indiceCompeticao", indiceCompeticao);
		saida.put("dataConsulta",dataConsulta);
		saida.put("volumePesquisa",volumePesquisa);
		saida.put("mediaCpc",mediaCpc);
		saida.put("palavraChaveRaizId",palavraChaveRaizId);
		return saida;
	}	
	
	

	public String getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(String dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public Double getMediaCpc() {
		return mediaCpc;
	}

	public void setMediaCpc(Double mediaCpc) {
		this.mediaCpc = mediaCpc;
	}

	public Long getVolumePesquisa() {
		return volumePesquisa;
	}

	public void setVolumePesquisa(Long volumePesquisa) {
		this.volumePesquisa = volumePesquisa;
	}

	public Double getIndiceCompeticao() {
		return indiceCompeticao;
	}

	public void setIndiceCompeticao(Double indiceCompeticao) {
		this.indiceCompeticao = indiceCompeticao;
	}

	public Long getPalavraChaveRaizId() {
		return palavraChaveRaizId;
	}

	public void setPalavraChaveRaizId(Long palavraChaveRaizId) {
		this.palavraChaveRaizId = palavraChaveRaizId;
	}

	public String getPalavraChaveGoogleId() {
		return palavraChaveGoogleId;
	}

	public void setPalavraChaveGoogleId(String palavraChaveGoogleId) {
		this.palavraChaveGoogleId = palavraChaveGoogleId;
	}

	

	/*
	 * public String toString() { String saida = "{ " +
	 * " \"palavraChaveGoogleId\" : \"" + this.palavraChaveGoogleId + "\" ," +
	 * " \"IndiceCompeticao\" : " + this.IndiceCompeticao + " " + " } "; return
	 * saida; }
	 */
	
	public String toString() {
		return this.palavraChaveGoogleId + " Vol: " + this.volumePesquisa + " Cpc: " + this.mediaCpc;
	}

}
