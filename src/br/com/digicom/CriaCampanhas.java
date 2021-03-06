package br.com.digicom;

import java.util.List;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.remoting.Repository;

import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.repositorio.RepositorioBase;

public class CriaCampanhas {

	private static RestAdapter adapter;
	private static Repository testClass;

	public static void main(String[] args) {
		System.out.println("Ola Mundo");
		RestAdapter adapter = new RestAdapter("https://www.digicom.inf.br:21101/api");
		RepositorioBase.CampanhaAdRepository rep = adapter.createRepository(RepositorioBase.CampanhaAdRepository.class);
		
		rep.listaPendente(new ListCallback<CampanhaAds>() { 
            
			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}
			@Override
			public void onSuccess(List<CampanhaAds> objects) {
				System.out.println("Total Campanha: " + objects.size());
				IntegracaoMundo integra = new IntegracaoMundo();
				integra.criaCampanhaLista(objects);
				
			} 
        });
        //try {
		//	Thread.sleep(5 * 60 * 1000);
		//	System.exit(0);
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
        
		
	}


}
