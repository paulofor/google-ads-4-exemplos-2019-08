package br.com.digicom.modelo;

import java.util.Map;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.RestAdapter;

import br.com.digicom.modelo.repositorio.RepositorioBase;

public abstract class ModeloBase extends Model{

	protected RestAdapter adapter = new RestAdapter("http://validacao.kinghost.net:21101/api");
	
	protected AnuncioAplicacaoResultado criaAnuncioAplicacaoResultado(Object item) {
		RepositorioBase.AnuncioAplicacaoResultadoRepository rep = adapter
				.createRepository(RepositorioBase.AnuncioAplicacaoResultadoRepository.class);
		Object saida = rep.createObject((Map<String, ? extends Object>) item);
		return (AnuncioAplicacaoResultado) saida;
	}
	
	protected CampanhaAnuncioResultado criaCampanhaAnuncioResultado(Object item) {
		RepositorioBase.CampanhaAnuncioResultadoRepository rep = adapter
				.createRepository(RepositorioBase.CampanhaAnuncioResultadoRepository.class);
		Object saida = rep.createObject((Map<String, ? extends Object>) item);
		return (CampanhaAnuncioResultado) saida;
	}
	
	/*
	 * 
	 * Evolucao que ainda nao fiz pq ta funcionando bem assim.
	 * 
	 * 
	protected AnuncioAplicativo criaAnuncioAplicativo(Object item) {
		RepositorioBase.AnuncioAplicativoRepository rep = adapter
				.createRepository(RepositorioBase.AnuncioAplicativoRepository.class);
		Object saida = rep.createObject((Map<String, ? extends Object>) item);
		return (AnuncioAplicativo) saida;
	}
	*/
}
