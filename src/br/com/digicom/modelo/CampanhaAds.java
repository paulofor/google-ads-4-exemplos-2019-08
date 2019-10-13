package br.com.digicom.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.BeanUtil;

import br.com.digicom.modelo.repositorio.RepositorioBase;

public class CampanhaAds extends ModeloBase {

	
	private List<CampanhaPalavraChaveResultado> campanhaPalavraChaveResultados;
	private List<CampanhaAnuncioResultado> campanhaAnuncioResultados;
	private List<AnuncioAplicacaoResultado> anuncioAplicacaoResultados; 

	private String nome;
	private String idAds;
	private String dataFinal;
	private String dataInicial;
	private String dataPublicacao;
	private String dataResultado;
	private String dataFechamento;

	private Integer quantidadeImpressao;
	private Integer quantidadeClique;
	private Integer quantidadeConversao;
	private Double orcamentoDia;
	private Double orcamentoTotalPlanejado;
	private Double orcamentoTotalExecutado;
	private Integer paginaValidacaoWebId;
	private Integer modeloCampanhaId;
	private Integer setupCampanhaId;
	private Integer anuncioAplicativoId;

	private String listaCampanha;
	private String urlAlvo;
	private String urlAlvoMobile;

	private SetupCampanha setupCampanha;

	private Double ctr;
	private Double cpcMedio;
	private Double conversao;
	private Double custoConversao;
	private Double taxaConversao;

	private AnuncioAplicativo anuncioAplicativo;
	private VersaoApp versaoApp;
	//private AnuncioAplicacaoResultado anuncioAplicacaoResultado;

	public Integer getAnuncioAplicativoId() {
		return anuncioAplicativoId;
	}

	public void setAnuncioAplicativoId(Integer anuncioAplicativoId) {
		this.anuncioAplicativoId = anuncioAplicativoId;
	}

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

	// ** SETUP CAMPANHA **
	public SetupCampanha getSetupCampanha() {
		return setupCampanha;
	}

	public void setSetupCampanha(Object setupCampanha) {
		this.setupCampanha = new SetupCampanha();
		BeanUtil.setProperties(this.setupCampanha, (Map<String, ? extends Object>) setupCampanha, true);
	}

	public void resetSetupCampanha() {
		this.setupCampanha = null;
	}

	public List<CampanhaPalavraChaveResultado> getCampanhaPalavraChaveResultados() {
		return campanhaPalavraChaveResultados;
	}

	public void setCampanhaPalavraChaveResultados(List<CampanhaPalavraChaveResultado> campanhaPalavraChaveResultados) {
		this.campanhaPalavraChaveResultados = new ArrayList<CampanhaPalavraChaveResultado>();
		for (int i = 0; i < campanhaPalavraChaveResultados.size(); i++) {
			Object objeto = new CampanhaPalavraChaveResultado();
			BeanUtil.setProperties(objeto, (Map<String, ? extends Object>) campanhaPalavraChaveResultados.get(i), true);
			this.campanhaPalavraChaveResultados.add((CampanhaPalavraChaveResultado) objeto);
		}

	}

	public List<CampanhaAnuncioResultado> getCampanhaAnuncioResultados() {
		return campanhaAnuncioResultados;
	}

	public void setCampanhaAnuncioResultados(List<CampanhaAnuncioResultado> campanhaAnuncioResultados) {
		this.campanhaAnuncioResultados = new ArrayList<CampanhaAnuncioResultado>();
		/*
		for (int i = 0; i < campanhaAnuncioResultados.size(); i++) {
			Object objeto = new CampanhaAnuncioResultado();
			BeanUtil.setProperties(objeto, (Map<String, ? extends Object>) campanhaAnuncioResultados.get(i), true);
			this.campanhaAnuncioResultados.add((CampanhaAnuncioResultado) objeto);
		}
		*/
		for (Object item : campanhaAnuncioResultados) {
			this.campanhaAnuncioResultados.add(this.criaCampanhaAnuncioResultado(item));
		}

	}

	
	public List<AnuncioAplicacaoResultado> getAnuncioAplicacaoResultados() {
		return anuncioAplicacaoResultados;
	}

	public void setAnuncioAplicacaoResultados(List<AnuncioAplicacaoResultado> anuncioAplicacaoResultados) {
		RepositorioBase.AnuncioAplicacaoResultadoRepository rep = adapter
				.createRepository(RepositorioBase.AnuncioAplicacaoResultadoRepository.class);
		this.anuncioAplicacaoResultados = new ArrayList<AnuncioAplicacaoResultado>();
		for (int i = 0; i < anuncioAplicacaoResultados.size(); i++) {
			AnuncioAplicacaoResultado objeto2 = criaAnuncioAplicacaoResultado(anuncioAplicacaoResultados.get(i));
			this.anuncioAplicacaoResultados.add(objeto2);
		}

	}
	public String getUrlAlvo() {
		return urlAlvo;
	}

	public void setUrlAlvo(String urlAlvo) {
		this.urlAlvo = urlAlvo;
	}

	public String toString() {
		return "Campanha: " + this.nome + "( #" + this.getId() + ") - Anuncios: "
				+ this.campanhaAnuncioResultados.size() + ", Palavra-Chave: "
				+ this.campanhaPalavraChaveResultados.size() + " #Setup= " + this.getSetupCampanhaId();
	}

	/*
	 * public List<CampanhaAnuncioResultado> getAnuncioAds() { return anuncioAds; }
	 * 
	 * public void setAnuncioAds(List<CampanhaAnuncioResultado> anuncioAds) {
	 * this.anuncioAds = new ArrayList<CampanhaAnuncioResultado>(); for (int i=0;
	 * i<anuncioAds.size(); i++) { Object objeto = new AnuncioAds();
	 * BeanUtil.setProperties(objeto, (Map<String, ? extends Object>)
	 * anuncioAds.get(i), true); this.anuncioAds.add((CampanhaAnuncioResultado)
	 * objeto); } }
	 */

	public String getListaCampanha() {
		return listaCampanha;
	}

	public void setListaCampanha(String listaCampanha) {
		this.listaCampanha = listaCampanha;
	}

	public String getIdAds() {
		return idAds;
	}

	public void setIdAds(String idAds) {
		this.idAds = idAds;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
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

	public Integer getQuantidadeConversao() {
		return quantidadeConversao;
	}

	public void setQuantidadeConversao(Integer quantidadeConversao) {
		this.quantidadeConversao = quantidadeConversao;
	}

	public Double getOrcamentoDia() {
		return orcamentoDia;
	}

	public void setOrcamentoDia(Double orcamentoDia) {
		this.orcamentoDia = orcamentoDia;
	}

	public Double getOrcamentoTotalPlanejado() {
		return orcamentoTotalPlanejado;
	}

	public void setOrcamentoTotalPlanejado(Double orcamentoTotalPlanejado) {
		this.orcamentoTotalPlanejado = orcamentoTotalPlanejado;
	}

	public Double getOrcamentoTotalExecutado() {
		return orcamentoTotalExecutado;
	}

	public void setOrcamentoTotalExecutado(Double orcamentoTotalExecutado) {
		this.orcamentoTotalExecutado = orcamentoTotalExecutado;
	}

	public Integer getPaginaValidacaoWebId() {
		return paginaValidacaoWebId;
	}

	public void setPaginaValidacaoWebId(Integer paginaValidacaoWebId) {
		this.paginaValidacaoWebId = paginaValidacaoWebId;
	}

	public Integer getModeloCampanhaId() {
		return modeloCampanhaId;
	}

	public void setModeloCampanhaId(Integer modeloCampanhaId) {
		this.modeloCampanhaId = modeloCampanhaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getDataResultado() {
		return dataResultado;
	}

	public void setDataResultado(String dataResultado) {
		this.dataResultado = dataResultado;
	}

	public String getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getUrlAlvoMobile() {
		return urlAlvoMobile;
	}

	public void setUrlAlvoMobile(String urlAlvoMobile) {
		this.urlAlvoMobile = urlAlvoMobile;
	}

	public Integer getSetupCampanhaId() {
		return setupCampanhaId;
	}

	public void setSetupCampanhaId(Integer setupCampanhaId) {
		this.setupCampanhaId = setupCampanhaId;
	}

	// ** VERSAO APLICATIVO **
	public VersaoApp getVersaoApp() {
		return versaoApp;
	}

	public void setVersaoApp(Object versaoApp) {
		this.versaoApp = new VersaoApp();
		BeanUtil.setProperties(this.versaoApp, (Map<String, ? extends Object>) versaoApp, true);
	}

	public void resetVersaoApp() {
		this.versaoApp = null;
	}

}
