package br.com.digicom;

import java.util.List;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import br.com.digicom.adsservice.CampanhaAdsService;
import br.com.digicom.adsservice.CampanhaAplicacaoService;
import br.com.digicom.adsservice.CampanhaAppAdsNovoService;
import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.CampanhaAnuncioResultado;
import br.com.digicom.modelo.CampanhaPalavraChaveResultado;
import br.com.digicom.modelo.repositorio.RepositorioBase;
import br.com.digicom.modelo.util.Util;

public class IntegracaoMundo {

	RestAdapter adapter = new RestAdapter("http://validacao.kinghost.net:21101/api");

	public void criaCampanhaSemSalvar(CampanhaAds campanha) {
		CampanhaAdsService servico = new CampanhaAdsService();
		servico.cria(campanha);
		// campanha.resetSetupCampanha();
	}

	public void criaCampanhaLista(List<CampanhaAds> objects) {
		for (CampanhaAds campanha : objects) {
			//if (campanha.getAnuncioAplicativo() != null) {
			//	criaCampanhaAplicacao(campanha);
			//} else {
			//	criaCampanhaGeral(campanha);
			//}
			criaCampanhaNova(campanha);
		}
	}
	
	private void criaCampanhaNova(CampanhaAds campanha) {
		CampanhaAppAdsNovoService servico = new CampanhaAppAdsNovoService();
		servico.cria(campanha);
	}
	
	public void criaCampanhaAplicacao(CampanhaAds campanha) {
		CampanhaAplicacaoService servico = new CampanhaAplicacaoService();
		servico.cria(campanha);
		System.out.println("IdAds: " + campanha.getIdAds());
		campanha.setDataPublicacao(Util.getDataAtualLoopback());
		campanha.resetSetupCampanha();
		campanha.resetAnuncioAplicativo();
		campanha.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				System.out.print("sucesso - alteracao campanha aplicacao");
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}
		});
	}

	public void criaCampanhaGeral(CampanhaAds campanha) {
		CampanhaAplicacaoService servico = new CampanhaAplicacaoService();
		servico.cria(campanha);
		System.out.println("IdAds: " + campanha.getIdAds());
		campanha.setDataPublicacao(Util.getDataAtualLoopback());
		campanha.resetSetupCampanha();
		campanha.resetAnuncioAplicativo();
		campanha.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				System.out.print("sucesso - alteracao campanha geral");
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}
		});
		salvaAnuncioCampanha(campanha);
		salvaPalavraChaveCampanha(campanha);
	}

	private void salvaAnuncioCampanha(CampanhaAds campanha) {
		int pos = 0;
		RepositorioBase.CampanhaAnuncioResultadoRepository rep = adapter
				.createRepository(RepositorioBase.CampanhaAnuncioResultadoRepository.class);
		for (CampanhaAnuncioResultado anuncio : campanha.getCampanhaAnuncioResultados()) {
			System.out.println((pos++) + " - IDS Anuncio: " + anuncio.getIdAds());
			if (anuncio.getIdAds() != null) {
				anuncio.setRepository(rep);
				anuncio.save(new VoidCallback() {
					@Override
					public void onSuccess() {
						System.out.print("sucesso - alteracao ressultado");
					}

					@Override
					public void onError(Throwable t) {
						// TODO Auto-generated method stub
						t.printStackTrace();
					}
				});
			}
		}
	}

	private void salvaPalavraChaveCampanha(CampanhaAds campanha) {
		int pos = 0;
		RepositorioBase.CampanhaPalavraChaveResultadoRepository rep = adapter
				.createRepository(RepositorioBase.CampanhaPalavraChaveResultadoRepository.class);
		for (CampanhaPalavraChaveResultado palavraChave : campanha.getCampanhaPalavraChaveResultados()) {
			System.out.println((pos++) + " - IDS PalavraChave: " + palavraChave.getIdAds());
			if (palavraChave.getIdAds() != null) {
				palavraChave.setRepository(rep);
				palavraChave.save(new VoidCallback() {
					@Override
					public void onSuccess() {
						System.out.print("sucesso - alteracao resultado id : ");
					}

					@Override
					public void onError(Throwable t) {
						// TODO Auto-generated method stub
						t.printStackTrace();
					}
				});
			}
		}
	}

	public void atualizaCampanha(final CampanhaAds item) {
		CampanhaAdsService servico = new CampanhaAdsService();
		item.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				System.out.print("sucesso" + item.getId());
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}

		});

	}

	public void atualizaAnuncio(CampanhaAnuncioResultado item) {
		item.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				System.out.print("sucesso");
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}

		});
	}

	public void atualizaPalavraChave(CampanhaPalavraChaveResultado item) {
		item.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				System.out.print("sucesso");
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}

		});
	}

}
