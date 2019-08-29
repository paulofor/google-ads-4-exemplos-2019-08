package br.com.digicom.adsservice;

import java.util.List;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.services.GoogleAdsRow;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v2.services.SearchGoogleAdsRequest;
import com.google.protobuf.Int64Value;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient.SearchPagedResponse;

import br.com.digicom.AdsService;
import br.com.digicom.modelo.CampanhaAds;

public class CampanhaResultNovoService extends AdsService{

	
	private CampanhaAds campanhaResult = null;
	
	public void atualizaResultado(CampanhaAds campanha) {
		this.campanhaResult = campanha;
		super.executa();
	}

	@Override
	protected void runExample(GoogleAdsClient googleAdsClient, long customerId) {
		try (GoogleAdsServiceClient googleAdsServiceClient = googleAdsClient.getLatestVersion()
				.createGoogleAdsServiceClient()) {
		    
		    String query= " SELECT campaign.name, campaign.status, metrics.impressions," +
			       " metrics.clicks, metrics.ctr, metrics.average_cpc, metrics.cost_micros, " +
		    	   " metrics.conversions, metrics.conversions_from_interactions_rate, metrics.cost_per_conversion " +
			       " FROM campaign where campaign.id = " + campanhaResult.getIdAds();
		    
			
			SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
					.setCustomerId(Long.toString(customerId))
					.setQuery(query).build();
			
			SearchPagedResponse searchPagedResponse = googleAdsServiceClient.search(request);

			
			for (GoogleAdsRow googleAdsRow : searchPagedResponse.iterateAll()) {
				
				//System.out.println(googleAdsRow.toString());

				Integer impressao = (int) googleAdsRow.getMetrics().getImpressions().getValue();
				Integer click = (int) googleAdsRow.getMetrics().getClicks().getValue();
				Double custo = (double) googleAdsRow.getMetrics().getCostMicros().getValue();
				custo = custo / 1000000;
				Double ctr = googleAdsRow.getMetrics().getCtr().getValue();
				ctr = ctr * 100;
				//Double ctr = Double.parseDouble(values.get(5).replaceAll("%", ""));
				Double averageCpc = googleAdsRow.getMetrics().getAverageCpc().getValue();
				averageCpc = averageCpc / 1000000;
				Double converions = googleAdsRow.getMetrics().getConversions().getValue();
				Double conversionRate = googleAdsRow.getMetrics().getConversionsFromInteractionsRate().getValue();
				conversionRate = conversionRate * 100;
				Double costPerConversion = googleAdsRow.getMetrics().getCostPerConversion().getValue();
				costPerConversion = costPerConversion / 1000000;
				campanhaResult.setOrcamentoTotalExecutado(custo);
				campanhaResult.setQuantidadeImpressao(impressao);
				campanhaResult.setQuantidadeClique(click);
				campanhaResult.setCtr(ctr);
				campanhaResult.setCpcMedio(averageCpc);
				campanhaResult.setConversao(converions);
				campanhaResult.setTaxaConversao(conversionRate);
				campanhaResult.setCustoConversao(costPerConversion);
			}
		}
		
	}
	
	
	
}
