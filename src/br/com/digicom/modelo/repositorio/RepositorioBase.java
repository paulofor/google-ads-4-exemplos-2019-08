package br.com.digicom.modelo.repositorio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.CampanhaAnuncioResultado;
import br.com.digicom.modelo.CampanhaPalavraChaveResultado;
import br.com.digicom.modelo.PalavraChaveEstatistica;
import br.com.digicom.modelo.PalavraChaveRaiz;

import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.EmptyResponseParser;
import com.strongloop.android.loopback.callbacks.JsonArrayParser;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.RestContractItem;

public class RepositorioBase {
	
	
	public static class PalavraChaveRaizRepository extends ModelRepository<PalavraChaveRaiz> {
		public PalavraChaveRaizRepository() {
			super("PalavraChaveRaiz", PalavraChaveRaiz.class);
		}
		public void listaParaConsulta(final ListCallback<PalavraChaveRaiz> callback) {
			RestContractItem contrato = new RestContractItem("PalavraChaveRaizs/listaParaConsulta","GET");
			this.getRestAdapter().getContract().addItem(contrato, "PalavraChaveRaiz.listaParaConsulta");
	        Map<String, Object> params = new HashMap<String, Object>();
	        invokeStaticMethod("listaParaConsulta", params,
	                new JsonArrayParser<PalavraChaveRaiz>(this, callback));
	    }
		@Override
		protected String verificaNomeUrl(String nome) {
			return "PalavraChaveRaizs";
		}
	}
	
	public static class PalavraChaveEstatisticaRepository extends ModelRepository<PalavraChaveEstatistica> {

		public PalavraChaveEstatisticaRepository() {
			super("PalavraChaveEstatistica", PalavraChaveEstatistica.class);
		}
		
		public void insereLista(List<PalavraChaveEstatistica> listaItem, final VoidCallback voidCallback) {
			RestContractItem contrato = new RestContractItem("PalavraChaveEstatisticas/insereLista","POST");
			this.getRestAdapter().getContract().addItem(contrato, "PalavraChaveEstatistica.insereLista");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("listaResultados", listaItem);
	        invokeStaticMethod("insereLista", params, new EmptyResponseParser(voidCallback));
	    }
	    
		
		
	}
	

	public static class CampanhaAdRepository extends ModelRepository<CampanhaAds> {
		public CampanhaAdRepository() {
			super("CampanhaAd", CampanhaAds.class);
		}
		
		public void atualizaResultado(final CampanhaAds campanha, final VoidCallback callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/atualizaResultado","POST");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAd.atualizaResultado");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("campanha", campanha);
	        invokeStaticMethod("atualizaResultado", params, new EmptyResponseParser(callback));
		}
		
		
		public void listaPendente(final ListCallback<CampanhaAds> callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/listaParaPublicar","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAd.listaParaPublicar");
	        Map<String, Object> params = new HashMap<String, Object>();
	        invokeStaticMethod("listaParaPublicar", params,
	                new JsonArrayParser<CampanhaAds>(this, callback));
	    }
		public void listaPendente(int id, final ListCallback<CampanhaAds> callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/listaParaPublicarTeste","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAd.listaParaPublicarTeste");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("idCampanha", id);
	        invokeStaticMethod("listaParaPublicarTeste", params,
	                new JsonArrayParser<CampanhaAds>(this, callback));
	    }
		public void listaParaResultado(final ListCallback<CampanhaAds> callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/listaParaResultado","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAd.listaParaResultado");
	        Map<String, Object> params = new HashMap<String, Object>();
	        invokeStaticMethod("listaParaResultado", params,
	                new JsonArrayParser<CampanhaAds>(this, callback));
	    }
		public void listaParaResultado(int id, final ListCallback<CampanhaAds> callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/listaParaResultadoTeste","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAd.listaParaResultadoTeste");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("idCampanha", id);
	        invokeStaticMethod("listaParaResultadoTeste", params,
	                new JsonArrayParser<CampanhaAds>(this, callback));
	    }
		public void calculaResultados(int id, final VoidCallback callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/calculaResultados","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAd.calculaResultados");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("idCampanha", id);
	        invokeStaticMethod("calculaResultados", params, new EmptyResponseParser(callback));
	    }
	}
	
	

	public static class CampanhaAnuncioResultadoRepository extends ModelRepository<CampanhaAnuncioResultado> {
		public CampanhaAnuncioResultadoRepository() {
			super("CampanhaAnuncioResultado", CampanhaAnuncioResultado.class);
		}
		public void listaParaResultadoPorIdCampanha(final Integer idCampanha, final ListCallback<CampanhaAnuncioResultado> callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/:id/campanhaAnuncioResultados","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaAnuncioResultado.listaParaResultadoPorIdCampanha");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("id", idCampanha);
	        params.put("filter", "{ \"include\" : \"anuncioAds\" }");
	        invokeStaticMethod("listaParaResultadoPorIdCampanha", params,
	                new JsonArrayParser<CampanhaAnuncioResultado>(this, callback));
	    }
		@Override
		protected String verificaNomeUrl(String nome) {
			return "CampanhaAnuncioResultados";
		}
		
	}
	public static class CampanhaPalavraChaveResultadoRepository extends ModelRepository<CampanhaPalavraChaveResultado> {
		public CampanhaPalavraChaveResultadoRepository() {
			super("CampanhaPalavraChaveResultado", CampanhaPalavraChaveResultado.class);
		}
		public void listaParaResultadoPorIdCampanha(final Integer idCampanha, final ListCallback<CampanhaPalavraChaveResultado> callback) {
			RestContractItem contrato = new RestContractItem("CampanhaAds/:id/campanhaPalavraChaveResultados","GET");
			this.getRestAdapter().getContract().addItem(contrato, "CampanhaPalavraChaveResultado.listaParaResultadoPorIdCampanha");
	        Map<String, Object> params = new HashMap<String, Object>();
	        params.put("id", idCampanha);
	        params.put("filter", "{ \"include\" : \"palavraChaveAds\" }");
	        invokeStaticMethod("listaParaResultadoPorIdCampanha", params,
	                new JsonArrayParser<CampanhaPalavraChaveResultado>(this, callback));
	    }
		@Override
		protected String verificaNomeUrl(String nome) {
			return "CampanhaPalavraChaveResultados";
		}
		
	}
	
	
	
	/*
	public static class AnuncioAdRepository extends ModelRepository<AnuncioAds> {
		public AnuncioAdRepository() {
			super("AnuncioAd", AnuncioAds.class);
		}
	}
	
	public static class ModeloCampanhaAdRepository extends ModelRepository<ModeloCampanhaAds> {
		public ModeloCampanhaAdRepository() {
			super("ModeloCampanhaAd", ModeloCampanhaAds.class);
		}
	}
	
	public static class PalavraChaveAdRepository extends ModelRepository<PalavraChaveAds> {
		public PalavraChaveAdRepository() {
			super("PalavraChaveAd", PalavraChaveAds.class);
		}
	}
	*/
}
