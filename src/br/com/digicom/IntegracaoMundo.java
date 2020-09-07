package br.com.digicom;

import java.util.List;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import br.com.digicom.adsservice.CampanhaAppAdsNovoService;
import br.com.digicom.adsservice.CampanhaGeralAdsNovoService;
import br.com.digicom.modelo.AnuncioAplicacaoResultado;
import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.CampanhaAnuncioResultado;
import br.com.digicom.modelo.CampanhaPalavraChaveResultado;
import br.com.digicom.modelo.repositorio.RepositorioBase;
import br.com.digicom.modelo.util.Util;

public class IntegracaoMundo {

	RestAdapter adapter = new RestAdapter("https://www.digicom.inf.br:21101/api");
	
	RepositorioBase.CampanhaAdRepository repCampanha = adapter
			.createRepository(RepositorioBase.CampanhaAdRepository.class);


	public void criaCampanhaSemSalvar(CampanhaAds campanha) {
		CampanhaAppAdsNovoService servico = new CampanhaAppAdsNovoService();
		servico.cria(campanha);
		// campanha.resetSetupCampanha();
	}

	public void criaCampanhaLista(List<CampanhaAds> objects) {
		
		for (CampanhaAds campanha : objects) {
			if (campanha.getAnuncioAplicativo() != null) {
				criaCampanhaAplicacao(campanha);
			} else {
				criaCampanhaGeral(campanha);
			}
		}
	}
	
	private void criaCampanhaNova(CampanhaAds campanha) {
		
	}
	
	public void criaCampanhaAplicacao(final CampanhaAds campanha) {
		CampanhaAppAdsNovoService servico = new CampanhaAppAdsNovoService();
		servico.cria(campanha);
		System.out.println("IdAds: " + campanha.getIdAds());
		campanha.setDataPublicacao(Util.getDataAtualLoopback());
		campanha.resetSetupCampanha();
		campanha.resetAnuncioAplicativo();
		//campanha.resetAnuncioAplicacaoResultado();
		campanha.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				System.out.print("sucesso - alteracao campanha aplicacao");
				repCampanha.criaValorEtapaFunil((int)campanha.getId(), new VoidCallback() {
					@Override
					public void onSuccess() {
						System.out.println("criou valor etapa funil");
					}
					@Override
					public void onError(Throwable t) {
						t.printStackTrace();
					}
					
				});
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}
		});
		salvaAnuncioAplicativo(campanha);
	}

	public void criaCampanhaGeral(CampanhaAds campanha) {
		CampanhaGeralAdsNovoService servico = new CampanhaGeralAdsNovoService();
		servico.cria(campanha);

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
			anuncio.resetAnuncioAds();
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
	
	private void salvaAnuncioAplicativo(CampanhaAds campanha) {
		int pos = 0;
		RepositorioBase.AnuncioAplicacaoResultadoRepository rep = adapter
				.createRepository(RepositorioBase.AnuncioAplicacaoResultadoRepository.class);
		for (AnuncioAplicacaoResultado anuncio : campanha.getAnuncioAplicacaoResultados()) {
			System.out.println((pos++) + " - IDS Anuncio: " + anuncio.getIdAds());
			if (anuncio.getIdAds() != null) {
				anuncio.resetAnuncioAplicativo();
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

	/*
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
	*/

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

	public void atualizaCampanha(final CampanhaAds item) {
		item.save(new VoidCallback() {
			@Override
			public void onSuccess() {
				System.out.println("Sucesso Campanha: " + item.getId());
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

		});
		atualizaGrupoAnuncioCampanha(item);
	}

	public void atualizaGrupoAnuncioCampanha(final CampanhaAds item) {
		//RepositorioBase.AnuncioAplicacaoResultadoRepository rep = adapter
		//		.createRepository(RepositorioBase.AnuncioAplicacaoResultadoRepository.class);
		for (AnuncioAplicacaoResultado grupo : item.getAnuncioAplicacaoResultados()) {
			//grupo.setRepository(rep);
			grupo.resetAnuncioAplicativo();
			grupo.save(new VoidCallback() {
				@Override
				public void onSuccess() {
					System.out.println("Sucesso GrupoAnuncio " + item.getId());
				}

				@Override
				public void onError(Throwable t) {
					t.printStackTrace();
				}

			});
		}
	}
}
