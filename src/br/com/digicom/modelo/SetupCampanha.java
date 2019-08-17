package br.com.digicom.modelo;

import com.strongloop.android.loopback.Model;

public class SetupCampanha extends Model {

	private String nome;
	private Double budgetDiario;
	private String estrategia;
	private String diaSemanaInicio;
	private String diaSemanaFinal;
	private String plataforma;
	private String matchPalavra;
	private String rotacaoAnuncio;
	private Long permiteEdicao;
	
	private Double maxCpcGrupoAnuncio;
	
	private Double custoInstalacao;
	
	
	public Double getCustoInstalacao() {
		return custoInstalacao;
	}
	public void setCustoInstalacao(Double custoInstalacao) {
		this.custoInstalacao = custoInstalacao;
	}
	public Double getMaxCpcGrupoAnuncio() {
		return maxCpcGrupoAnuncio;
	}
	public void setMaxCpcGrupoAnuncio(Double maxCpcGrupoAnuncio) {
		this.maxCpcGrupoAnuncio = maxCpcGrupoAnuncio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getBudgetDiario() {
		return budgetDiario;
	}
	public void setBudgetDiario(Double budgetDiario) {
		this.budgetDiario = budgetDiario;
	}
	public String getEstrategia() {
		return estrategia;
	}
	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}
	public String getDiaSemanaInicio() {
		return diaSemanaInicio;
	}
	public void setDiaSemanaInicio(String diaSemanaInicio) {
		this.diaSemanaInicio = diaSemanaInicio;
	}
	public String getDiaSemanaFinal() {
		return diaSemanaFinal;
	}
	public void setDiaSemanaFinal(String diaSemanaFinal) {
		this.diaSemanaFinal = diaSemanaFinal;
	}
	public String getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
	public String getMatchPalavra() {
		return matchPalavra;
	}
	public void setMatchPalavra(String matchPalavra) {
		this.matchPalavra = matchPalavra;
	}
	public String getRotacaoAnuncio() {
		return rotacaoAnuncio;
	}
	public void setRotacaoAnuncio(String rotacaoAnuncio) {
		this.rotacaoAnuncio = rotacaoAnuncio;
	}
	public Long getPermiteEdicao() {
		return permiteEdicao;
	}
	public void setPermiteEdicao(Long permiteEdicao) {
		this.permiteEdicao = permiteEdicao;
	}

	
	
	
}
