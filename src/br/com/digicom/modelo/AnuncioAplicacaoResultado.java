package br.com.digicom.modelo;

import java.util.Map;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.remoting.BeanUtil;

public class AnuncioAplicacaoResultado extends Model {
	
	
	private String idAds;
	private AnuncioAplicativo anuncioAplicativo;
	
	private Integer anuncioAplicativoId;
	private Integer campanhaAdsId;

	
	
	
	public Integer getAnuncioAplicativoId() {
		return anuncioAplicativoId;
	}

	public void setAnuncioAplicativoId(Integer anuncioAplicativoId) {
		this.anuncioAplicativoId = anuncioAplicativoId;
	}

	public Integer getCampanhaAdsId() {
		return campanhaAdsId;
	}

	public void setCampanhaAdsId(Integer campanhaAdsId) {
		this.campanhaAdsId = campanhaAdsId;
	}

	public void setId(Integer id) {
		super.setIdObjeto(id);
	}
	
	// ** ANUNCIO APLICATIVO **
	public AnuncioAplicativo getAnuncioAplicativo() {
		return anuncioAplicativo;
	}

	public void setAnuncioAplicativo(Object anuncioAplicativo) {
		this.anuncioAplicativo = new AnuncioAplicativo();
		BeanUtil.setProperties(this.anuncioAplicativo, (Map<String, ? extends Object>) anuncioAplicativo, true);
	}

	public void resetAnuncioAplicativo() {
		this.anuncioAplicativo = null;
	}
	
	public String getIdAds() {
		return idAds;
	}

	public void setIdAds(String idAds) {
		this.idAds = idAds;
	}

}
