package br.com.digicom.modelo;

import java.util.HashMap;
import java.util.Map;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.remoting.BeanUtil;

public class CampanhaAnuncioResultado extends Model {

	private String idAds;
	private Integer quantidadeImpressao;
	private Integer quantidadeClique;
	private Double custo;
	//private Integer id;
	private Integer anuncioAdsId;
	private Integer campanhaAdsId;

	private AnuncioAds anuncioAds;
	
	private Double ctr;
	private Double cpcMedio;
	private Double conversao;
	private Double custoConversao;
	private Double taxaConversao;
	
	
	
	
	public Double getCtr() {
		return ctr;
	}


	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}


	public Double getCpcMedio() {
		return cpcMedio;
	}


	public void setCpcMedio(Double cpcMedio) {
		this.cpcMedio = cpcMedio;
	}


	public Double getConversao() {
		return conversao;
	}


	public void setConversao(Double conversao) {
		this.conversao = conversao;
	}


	public Double getCustoConversao() {
		return custoConversao;
	}


	public void setCustoConversao(Double custoConversao) {
		this.custoConversao = custoConversao;
	}


	public Double getTaxaConversao() {
		return taxaConversao;
	}


	public void setTaxaConversao(Double taxaConversao) {
		this.taxaConversao = taxaConversao;
	}


	
	
	public void setId(Integer id) {
		this.setIdObjeto(id);
	}
	
	public Map<String, ? extends Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idAds", idAds);
		map.put("quantidadeImpressao", (quantidadeImpressao!=null?quantidadeImpressao:0));
		map.put("quantidadeClique", (quantidadeClique!=null?quantidadeClique:0));
		map.put("custo", (custo!=null?custo:0));
		map.put("id", this.getId());
		map.put("anuncioAdsId", this.getAnuncioAdsId());
		map.put("campanhaAdsId", this.getCampanhaAdsId());
		map.put("ctr", (ctr!=null?ctr:0));
		map.put("cpcMedio", (cpcMedio!=null?cpcMedio:0));
		map.put("conversao", (conversao!=null?conversao:0));
		map.put("custoConversao", (custoConversao!=null?custoConversao:0));
		map.put("taxaConversao", (taxaConversao!=null?taxaConversao:0));
		return map;
	}


	public String getIdAds() {
		return idAds;
	}

	public void setIdAds(String idAds) {
		this.idAds = idAds;
	}

	public Integer getQuantidadeImpressao() {
		return quantidadeImpressao;
	}

	public void setQuantidadeImpressao(Integer quantidadeImpressao) {
		this.quantidadeImpressao = quantidadeImpressao;
	}

	public Integer getQuantidadeClique() {
		return quantidadeClique;
	}

	public void setQuantidadeClique(Integer quantidadeClique) {
		this.quantidadeClique = quantidadeClique;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	

	public Integer getAnuncioAdsId() {
		return anuncioAdsId;
	}

	public void setAnuncioAdsId(Integer anuncioAdsId) {
		this.anuncioAdsId = anuncioAdsId;
	}

	public Integer getCampanhaAdsId() {
		return campanhaAdsId;
	}

	public void setCampanhaAdsId(Integer campanhaAdsId) {
		this.campanhaAdsId = campanhaAdsId;
	}
	
	
	public void resetAnuncioAds() {
		this.anuncioAds = null;
	}
	public AnuncioAds getAnuncioAds() {
		return anuncioAds;
	}

	public void setAnuncioAds(Object anuncioAds) {
		this.anuncioAds = new AnuncioAds();
		BeanUtil.setProperties(this.anuncioAds, (Map<String, ? extends Object>) anuncioAds, true);
	}

	
}
