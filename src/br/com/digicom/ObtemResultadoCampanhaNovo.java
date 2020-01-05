package br.com.digicom;

import java.util.List;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;

import br.com.digicom.adsservice.CampanhaResultNovoService;
import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.repositorio.RepositorioBase;
import br.com.digicom.modelo.util.Util;

public class ObtemResultadoCampanhaNovo {

	private static RestAdapter adapter = new RestAdapter("https://www.digicom.inf.br:21101/api");

	public static void main(String[] args) {
		processa();
	}

	private static void processa() {
		System.out.println("Ola Mundo");
		RepositorioBase.CampanhaAdRepository rep = adapter.createRepository(RepositorioBase.CampanhaAdRepository.class);
		rep.listaParaResultado(new ListCallback<CampanhaAds>() {
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onSuccess(List<CampanhaAds> objects) {
				System.out.println("Lista pra resultado contendo " + objects.size() + " campanhas.");
				for (CampanhaAds item : objects) {
					// processaAnuncios(item);
					processaCampanha(item);
					// processaPalavraChave(item);
				}
			}
		});
		try {
			Thread.sleep(5 * 60 * 1000);
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void processaCampanha(CampanhaAds campanha) {
		System.out.println("Atualizar campanha " + campanha.getNome());
		CampanhaResultNovoService srv = new CampanhaResultNovoService();
		srv.atualizaResultado(campanha);
		campanha.setDataResultado(Util.getDataAtualLoopback());

		IntegracaoMundo facade = new IntegracaoMundo();
		facade.atualizaCampanha(campanha);

	}

	/*
	 * private static void processaAnuncios(CampanhaAds campanha) {
	 * RepositorioBase.CampanhaAnuncioResultadoRepository rep = adapter
	 * .createRepository(RepositorioBase.CampanhaAnuncioResultadoRepository.class);
	 * rep.listaParaResultadoPorIdCampanha((Integer) campanha.getId(), new
	 * ListCallback<CampanhaAnuncioResultado>() {
	 * 
	 * @Override public void onError(Throwable t) { t.printStackTrace(); }
	 * 
	 * @Override public void onSuccess(List<CampanhaAnuncioResultado> objects) {
	 * System.out.println("Lista pra resultado contendo " + objects.size() +
	 * " anuncios."); for (CampanhaAnuncioResultado item : objects) {
	 * processaAnuncio(item); } }
	 * 
	 * }); }
	 * 
	 * private static void processaPalavraChave(final CampanhaAds campanha) {
	 * RepositorioBase.CampanhaPalavraChaveResultadoRepository rep = adapter
	 * .createRepository(RepositorioBase.CampanhaPalavraChaveResultadoRepository.
	 * class); rep.listaParaResultadoPorIdCampanha((Integer) campanha.getId(), new
	 * ListCallback<CampanhaPalavraChaveResultado>() {
	 * 
	 * @Override public void onError(Throwable t) { t.printStackTrace(); }
	 * 
	 * @Override public void onSuccess(List<CampanhaPalavraChaveResultado> objects)
	 * { System.out.println("Lista pra resultado contendo " + objects.size() +
	 * " palavras chaves."); for (CampanhaPalavraChaveResultado item : objects) {
	 * processaPalavraChave(item, campanha); } }
	 * 
	 * }); }
	 * 
	 * private static void processaAnuncio(CampanhaAnuncioResultado item) {
	 * System.out.println("Atualizar anuncio " + item.getIdAds());
	 * AnuncioResultService srv = new AnuncioResultService();
	 * srv.atualizaResultado(item);
	 * 
	 * IntegracaoMundo facade = new IntegracaoMundo(); facade.atualizaAnuncio(item);
	 * }
	 * 
	 * private static void processaPalavraChave(CampanhaPalavraChaveResultado item,
	 * CampanhaAds campanha) { System.out.println("Atualizar palavra-chave " +
	 * item.getIdAds()); PalavraChaveResultService srv = new
	 * PalavraChaveResultService(); srv.atualizaResultado(item, campanha);
	 * 
	 * IntegracaoMundo facade = new IntegracaoMundo();
	 * facade.atualizaPalavraChave(item); }
	 */

}
