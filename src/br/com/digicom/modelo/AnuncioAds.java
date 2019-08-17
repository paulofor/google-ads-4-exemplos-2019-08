package br.com.digicom.modelo;

import com.strongloop.android.loopback.Model;

public class AnuncioAds extends Model {

	private Integer id;
	private Integer projetoCanvasMySqlId;

	private String titulo1;
	private String titulo2;
	private String titulo3;
	private String descricao1;
	private String descricao2;
	
	private Double custo;
	private Integer impressao;
	private Integer click;

	
	
	
	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public String getTitulo3() {
		return titulo3;
	}

	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}

	public String getDescricao1() {
		return descricao1;
	}

	public void setDescricao1(String descricao1) {
		this.descricao1 = descricao1;
	}

	public String getDescricao2() {
		return descricao2;
	}

	public void setDescricao2(String descricao2) {
		this.descricao2 = descricao2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getProjetoCanvasMySqlId() {
		return projetoCanvasMySqlId;
	}

	public void setProjetoCanvasMySqlId(Integer projetoCanvasMySqlId) {
		this.projetoCanvasMySqlId = projetoCanvasMySqlId;
	}
	
	public String toString() {
		return "titulo1 = " + titulo1 + ", titulo2 = " + titulo2 + ", titulo3 = " + titulo3;
	}

	
	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public void setQuantidadeImpressao(Integer impressao) {
		this.impressao = impressao;
	}

	public void setQuantidadeClique(Integer click) {
		this.click = click;
	}
	public Integer getQuantidadeClique() {
		return this.click;
	}
	public Integer getQuantidadeImpressao() {
		return this.impressao;
	}
	public Double getCusto() {
		return this.custo;
	}

}
