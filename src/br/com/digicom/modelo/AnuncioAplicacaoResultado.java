package br.com.digicom.modelo;

import java.util.Map;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.remoting.BeanUtil;

public class AnuncioAplicacaoResultado extends Model {
	
	
	private String idAds;
	private AnuncioAplicativo anuncioAplicativo;
	
	private Integer anuncioAplicativoId;
	private Integer campanhaAdsId;
	
	private Double orcamentoTotalExecutado;
	private Integer quantidadeImpressao;
	private Integer quantidadeClique;
	private Double ctr;
	private Double cpcMedio;
	private Double conversao;
	private Double taxaConversao;
	private Double custoConversao;
	

	
	public Double getCustoConversao() {
		return custoConversao;
	}

	public void setOrcamentoTotalExecutado(Double custo) {
		this.orcamentoTotalExecutado = custo;
	}

	public void setQuantidadeImpressao(Integer impressao) {
		this.quantidadeImpressao = impressao;
	}

	public void setQuantidadeClique(Integer click) {
		this.quantidadeClique = click;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}

	public void setCpcMedio(Double averageCpc) {
		this.cpcMedio = averageCpc;
	}

	public void setConversao(Double converions) {
		this.conversao = converions;
	}

	public void setTaxaConversao(Double conversionRate) {
		taxaConversao = conversionRate;
	}

	
	
	
	
	
	public Double getOrcamentoTotalExecutado() {
		return orcamentoTotalExecutado;
	}

	public Integer getQuantidadeImpressao() {
		return quantidadeImpressao;
	}

	public Integer getQuantidadeClique() {
		return quantidadeClique;
	}

	public Double getCtr() {
		return ctr;
	}

	public Double getCpcMedio() {
		return cpcMedio;
	}

	public Double getConversao() {
		return conversao;
	}

	public Double getTaxaConversao() {
		return taxaConversao;
	}

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

	//public void setId(Integer id) {
	//	super.setIdObjeto(id);
	//}
	
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

	

	public void setCustoConversao(Double costPerConversion) {
		this.custoConversao = costPerConversion;
	}

}
