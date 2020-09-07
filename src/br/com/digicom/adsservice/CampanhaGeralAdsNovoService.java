package br.com.digicom.adsservice;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.common.AdScheduleInfo;
import com.google.ads.googleads.v2.common.AdTextAsset;
import com.google.ads.googleads.v2.common.AppAdInfo;
import com.google.ads.googleads.v2.common.ExpandedTextAdInfo;
import com.google.ads.googleads.v2.common.KeywordInfo;
import com.google.ads.googleads.v2.common.ManualCpc;
import com.google.ads.googleads.v2.common.TargetCpa;
import com.google.ads.googleads.v2.enums.AdGroupAdStatusEnum.AdGroupAdStatus;
import com.google.ads.googleads.v2.enums.AdGroupCriterionStatusEnum.AdGroupCriterionStatus;
import com.google.ads.googleads.v2.enums.AdGroupStatusEnum.AdGroupStatus;
import com.google.ads.googleads.v2.enums.AdGroupTypeEnum.AdGroupType;
import com.google.ads.googleads.v2.enums.AdvertisingChannelSubTypeEnum.AdvertisingChannelSubType;
import com.google.ads.googleads.v2.enums.AdvertisingChannelTypeEnum.AdvertisingChannelType;
import com.google.ads.googleads.v2.enums.AppCampaignAppStoreEnum.AppCampaignAppStore;
import com.google.ads.googleads.v2.enums.AppCampaignBiddingStrategyGoalTypeEnum.AppCampaignBiddingStrategyGoalType;
import com.google.ads.googleads.v2.enums.BudgetDeliveryMethodEnum.BudgetDeliveryMethod;
import com.google.ads.googleads.v2.enums.CampaignStatusEnum.CampaignStatus;
import com.google.ads.googleads.v2.enums.DayOfWeekEnum.DayOfWeek;
import com.google.ads.googleads.v2.enums.KeywordMatchTypeEnum.KeywordMatchType;
import com.google.ads.googleads.v2.enums.MinuteOfHourEnum.MinuteOfHour;
import com.google.ads.googleads.v2.enums.PositiveGeoTargetTypeEnum.PositiveGeoTargetType;
import com.google.ads.googleads.v2.resources.Ad;
import com.google.ads.googleads.v2.resources.AdGroup;
import com.google.ads.googleads.v2.resources.AdGroupAd;
import com.google.ads.googleads.v2.resources.AdGroupCriterion;
import com.google.ads.googleads.v2.resources.Campaign;
import com.google.ads.googleads.v2.resources.Campaign.AppCampaignSetting;
import com.google.ads.googleads.v2.resources.Campaign.GeoTargetTypeSetting;
import com.google.ads.googleads.v2.resources.Campaign.NetworkSettings;
import com.google.ads.googleads.v2.resources.CampaignBudget;
import com.google.ads.googleads.v2.resources.CampaignCriterion;
import com.google.ads.googleads.v2.resources.CampaignCriterion.Builder;
import com.google.ads.googleads.v2.resources.GeoTargetConstantName;
import com.google.ads.googleads.v2.resources.LanguageConstantName;
import com.google.ads.googleads.v2.services.AdGroupAdOperation;
import com.google.ads.googleads.v2.services.AdGroupAdServiceClient;
import com.google.ads.googleads.v2.services.AdGroupCriterionOperation;
import com.google.ads.googleads.v2.services.AdGroupCriterionServiceClient;
import com.google.ads.googleads.v2.services.AdGroupOperation;
import com.google.ads.googleads.v2.services.AdGroupServiceClient;
import com.google.ads.googleads.v2.services.CampaignBudgetOperation;
import com.google.ads.googleads.v2.services.CampaignBudgetServiceClient;
import com.google.ads.googleads.v2.services.CampaignCriterionOperation;
import com.google.ads.googleads.v2.services.CampaignCriterionServiceClient;
import com.google.ads.googleads.v2.services.CampaignOperation;
import com.google.ads.googleads.v2.services.CampaignServiceClient;
import com.google.ads.googleads.v2.services.MutateAdGroupAdResult;
import com.google.ads.googleads.v2.services.MutateAdGroupAdsResponse;
import com.google.ads.googleads.v2.services.MutateAdGroupCriteriaResponse;
import com.google.ads.googleads.v2.services.MutateAdGroupCriterionResult;
import com.google.ads.googleads.v2.services.MutateAdGroupResult;
import com.google.ads.googleads.v2.services.MutateAdGroupsResponse;
import com.google.ads.googleads.v2.services.MutateCampaignBudgetsResponse;
import com.google.ads.googleads.v2.services.MutateCampaignCriteriaResponse;
import com.google.ads.googleads.v2.services.MutateCampaignCriterionResult;
import com.google.ads.googleads.v2.services.MutateCampaignResult;
import com.google.ads.googleads.v2.services.MutateCampaignsResponse;
import com.google.ads.googleads.v2.utils.ResourceNames;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

import br.com.digicom.AdsService;
import br.com.digicom.modelo.AnuncioAds;
import br.com.digicom.modelo.AnuncioAplicacaoResultado;
import br.com.digicom.modelo.AnuncioAplicativo;
import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.CampanhaAnuncioResultado;
import br.com.digicom.modelo.CampanhaPalavraChaveResultado;

public class CampanhaGeralAdsNovoService extends AdsService {

	private CampanhaAds campanha = null;

	
	public void cria(CampanhaAds campanha) {
		this.campanha = campanha;
		super.executa();
	}
	
	@Override
	protected void runExample(GoogleAdsClient googleAdsClient, long customerId) {
		String campaignResourceName = null;
		
		System.out.println("Entrando no cria campanha geral");
		
		UtilAds.setCampanha(campanha);
		String budgetResourceName = addCampaignBudget(googleAdsClient, customerId);
		
		List<CampaignOperation> operations = new ArrayList<>(1);
		
		// Configures the campaign network options
		NetworkSettings networkSettings = NetworkSettings.newBuilder()
				.setTargetGoogleSearch(BoolValue.of(false))
				.setTargetSearchNetwork(BoolValue.of(false))
				.setTargetContentNetwork(BoolValue.of(false))
				.setTargetPartnerSearchNetwork(BoolValue.of(true))
				.build();
		// Creates the campaign.
	    Campaign campaign =
	        Campaign.newBuilder()
	              .setName(StringValue.of("geral_" + System.currentTimeMillis() + "_" + this.campanha.getNome()))
	              .setAdvertisingChannelType(AdvertisingChannelType.DISPLAY)
	              // Recommendation: Set the campaign to PAUSED when creating it to prevent
	              // the ads from immediately serving. Set to ENABLED once you've added
	              // targeting and the ads are ready to serve
	              .setStatus(CampaignStatus.PAUSED)
	              // Sets the bidding strategy and budget.
	              .setManualCpc(ManualCpc.newBuilder().build())
	              .setCampaignBudget(StringValue.of(budgetResourceName))

	              // Adds the networkSettings configured above.
	              //.setNetworkSettings(networkSettings)
	              // Optional: Sets the start & end dates.
	              .setStartDate(StringValue.of(UtilAds.getDataInicial()))
	              .setEndDate(StringValue.of(UtilAds.getDataFinal()))
	              .build();
	    CampaignOperation op = CampaignOperation.newBuilder().setCreate(campaign).build();
	    operations.add(op);
	    
		try (CampaignServiceClient campaignServiceClient = googleAdsClient.getLatestVersion()
				.createCampaignServiceClient()) {
			MutateCampaignsResponse response = campaignServiceClient.mutateCampaigns(Long.toString(customerId),
					operations);
			System.out.printf("Added %d campaigns:%n", response.getResultsCount());
			for (MutateCampaignResult result : response.getResultsList()) {
				campaignResourceName = result.getResourceName();
				System.out.println(result.getResourceName());
				campanha.setIdAds(getIdAds(result));
				campanha.setDataInicial(UtilAds.getDataInicial());
				campanha.setDataFinal(UtilAds.getDataFinal());
			}
		}
		
		long idGrupo = this.insereGrupo(googleAdsClient, customerId, campaignResourceName);
		List<CampanhaPalavraChaveResultado> listaPalavra = this.campanha.getCampanhaPalavraChaveResultados();
		for (int i=0;i<listaPalavra.size();i++) {
			CampanhaPalavraChaveResultado palavra = listaPalavra.get(i);
			this.inserePalavraChave(googleAdsClient, customerId, campaignResourceName, idGrupo, palavra.getPalavraChaveGoogleId(), i);
		}
		this.insereAnuncio(googleAdsClient, customerId, campaignResourceName, idGrupo);
		this.montandoCriterios(googleAdsClient, customerId, campaignResourceName);
		
		System.out.println("Mostrar o campanha");
		
	}
	
	
	/*
	private void trataHorarios(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName) {
		AdScheduleInfo adScheduleInfo = AdScheduleInfo.newBuilder()
				.setDayOfWeek(DayOfWeek.FRIDAY)
				.setStartHour(Int32Value.of(0))
				.setStartMinute(MinuteOfHour.ZERO)
				.setEndHour(Int32Value.of(23))
				.setEndMinute(MinuteOfHour.ZERO)
				.build();
		
		Builder criterionBuilder = CampaignCriterion.newBuilder().setCampaign(StringValue.of(campaignResourceName));
		criterionBuilder.getAdScheduleBuilder().getLanguageBuilder()
				.setLanguageConstant(StringValue.of(LanguageConstantName.format(String.valueOf(linguaId))));
		
		
		List<CampaignCriterionOperation> operations = ImmutableList.of(
				CampaignCriterionOperation.newBuilder().setCreate(adScheduleInfo)
						.build(),
				CampaignCriterionOperation.newBuilder().setCreate(buildLocationIdCriterion(2076, campaignResourceName))
						.build());

		try (CampaignCriterionServiceClient campaignCriterionServiceClient = googleAdsClient.getLatestVersion().createCampaignCriterionServiceClient()) {
			MutateCampaignCriteriaResponse response = campaignCriterionServiceClient.mutateCampaignCriteria(Long.toString(customerId), operations);
			System.out.printf("Added %d campaign criteria:%n", response.getResultsCount());
			for (MutateCampaignCriterionResult result : response.getResultsList()) {
				System.out.println(result.getResourceName());
			}
		}
	}
	*/
	
	private long insereGrupo(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName) {
		String nomeGrupo = null;
		// Creates an ad group, setting an optional CPC value.
	    AdGroup adGroup1 =
	        AdGroup.newBuilder()
	            .setName(StringValue.of("GrupoUnico_" + System.currentTimeMillis()))
	            .setStatus(AdGroupStatus.ENABLED)
	            .setCampaign(StringValue.of(campaignResourceName))
	            .setType(AdGroupType.DISPLAY_STANDARD)
	            //.setCpcBidMicros(Int64Value.of(10_000_000L))
	            .build();
	    List<AdGroupOperation> operations = new ArrayList<>();
	    operations.add(AdGroupOperation.newBuilder().setCreate(adGroup1).build());
	    
	    try (AdGroupServiceClient adGroupServiceClient =
	        googleAdsClient.getLatestVersion().createAdGroupServiceClient()) {
	      MutateAdGroupsResponse response =
	          adGroupServiceClient.mutateAdGroups(Long.toString(customerId), operations);
	      System.out.printf("Added %d ad groups:%n", response.getResultsCount());
	      for (MutateAdGroupResult result : response.getResultsList()) {
	    	  nomeGrupo = result.getResourceName();
	    	  System.out.println(result.getResourceName());
	      }
	    }
	    System.out.println("Grupo: " + nomeGrupo);
	    String[] listaCodigo = nomeGrupo.split("/");
	    long saida = Long.parseLong(listaCodigo[listaCodigo.length - 1]);
	    System.out.println("Codigo: " + saida);
	    return saida;
	}

	
	
	private void inserePalavraChave(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName, long nomeGrupo,String keywordText, int index) {

		String idAds = null;
		
		// Configures the keywordText text and match type settings.
		KeywordInfo keywordInfo = KeywordInfo.newBuilder().setText(StringValue.of(keywordText))
				.setMatchType(KeywordMatchType.EXACT).build();

		String adGroupResourceName = ResourceNames.adGroup(customerId, nomeGrupo);

		// Constructs an ad group criterion using the keywordText configuration above.
		AdGroupCriterion criterion = AdGroupCriterion.newBuilder().setAdGroup(StringValue.of(adGroupResourceName))
				.setStatus(AdGroupCriterionStatus.ENABLED).setKeyword(keywordInfo).build();

		AdGroupCriterionOperation op = AdGroupCriterionOperation.newBuilder().setCreate(criterion).build();

		try (AdGroupCriterionServiceClient agcServiceClient = googleAdsClient.getLatestVersion()
				.createAdGroupCriterionServiceClient()) {
			MutateAdGroupCriteriaResponse response = agcServiceClient.mutateAdGroupCriteria(Long.toString(customerId),
					ImmutableList.of(op));
			System.out.printf("Adicionada %d palavra-chave:%n", response.getResultsCount());
			for (MutateAdGroupCriterionResult result : response.getResultsList()) {
				System.out.println(result.getResourceName());
				idAds = this.getIdAds(result); 
				this.campanha.getCampanhaPalavraChaveResultados().get(index).setIdAds(idAds);
			}
		}
	}
	


	private void insereAnuncio(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName, long idGrupo) {
	    String adGroupResourceName = ResourceNames.adGroup(customerId, idGrupo);

	    List<AdGroupAdOperation> operations = new ArrayList<>();
	    
	    List<CampanhaAnuncioResultado> listaAnuncio = this.campanha.getCampanhaAnuncioResultados();
	    
	    String idAds = null;

	    for (int i = 0; i < listaAnuncio.size(); i++) {
	    	
	    AnuncioAds anuncio = listaAnuncio.get(i).getAnuncioAds();
	    
	      // Creates the expanded text ad info.
	      ExpandedTextAdInfo expandedTextAdInfo =
	          ExpandedTextAdInfo.newBuilder()
	              .setHeadlinePart1(StringValue.of(anuncio.getTitulo1()))
	              .setHeadlinePart2(StringValue.of(anuncio.getTitulo2()))
	              .setDescription(StringValue.of(anuncio.getDescricao1()))
	              .build();

	      // Wraps the info in an Ad object.
	      Ad ad =
	          Ad.newBuilder()
	              .setExpandedTextAd(expandedTextAdInfo)
	              .addFinalUrls(StringValue.of(campanha.getUrlAlvo()))
	              .build();

	      // Builds the final ad group ad representation.
	      AdGroupAd adGroupAd =
	          AdGroupAd.newBuilder()
	              .setAdGroup(StringValue.of(adGroupResourceName))
	              .setStatus(AdGroupAdStatus.PAUSED)
	              .setAd(ad)
	              .build();

	      AdGroupAdOperation op = AdGroupAdOperation.newBuilder().setCreate(adGroupAd).build();
	      operations.add(op);
	      
	      try (AdGroupAdServiceClient adGroupAdServiceClient =
	  	        googleAdsClient.getLatestVersion().createAdGroupAdServiceClient()) {
	  	      MutateAdGroupAdsResponse response =
	  	          adGroupAdServiceClient.mutateAdGroupAds(Long.toString(customerId), operations);
	  	      for (MutateAdGroupAdResult result : response.getResultsList()) {
	  	        System.out.printf("Expanded text ad created with resource name: %s%n", result.getResourceName());
	  	        //anuncio.setIdAds(this.getIdAds(result));
	  	        idAds = this.getIdAds(result);
	  	        this.campanha.getCampanhaAnuncioResultados().get(i).setIdAds(idAds);
	  	      }
	  	    }
	      operations.clear();
	    }
	    
	    

	    
	}
	
	private String getIdAds(MutateAdGroupCriterionResult result) {
		String[] palavras = result.getResourceName().split("/");
		String campanhaGrupo = palavras[palavras.length-1];
		return campanhaGrupo.split("~")[1];
	}
	
	private String getIdAds(MutateCampaignResult result) {
		String[] palavras = result.getResourceName().split("/");
		return palavras[palavras.length-1];
	}
	private String getIdAds(MutateAdGroupAdResult result) {
		String[] palavras = result.getResourceName().split("/");
		String campanhaGrupo = palavras[palavras.length-1];
		return campanhaGrupo.split("~")[1];
	}
	
	private void montandoCriterios(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName) {
		// String campaignResourceName = CampaignName.format(Long.toString(customerId),
		// Long.toString(campaignId));

		List<CampaignCriterionOperation> operations = ImmutableList.of(
				CampaignCriterionOperation.newBuilder().setCreate(buildLanguageIdCriterion(1014, campaignResourceName))
						.build(),
				CampaignCriterionOperation.newBuilder().setCreate(buildLocationIdCriterion(2076, campaignResourceName))
						.build());

		try (CampaignCriterionServiceClient campaignCriterionServiceClient = googleAdsClient.getLatestVersion().createCampaignCriterionServiceClient()) {
			MutateCampaignCriteriaResponse response = campaignCriterionServiceClient.mutateCampaignCriteria(Long.toString(customerId), operations);
			System.out.printf("Added %d campaign criteria:%n", response.getResultsCount());
			for (MutateCampaignCriterionResult result : response.getResultsList()) {
				System.out.println(result.getResourceName());
			}
		}

	}
	private static CampaignCriterion buildLocationIdCriterion(long locationId, String campaignResourceName) {
		Builder criterionBuilder = CampaignCriterion.newBuilder().setCampaign(StringValue.of(campaignResourceName));
		criterionBuilder.getLocationBuilder()
				.setGeoTargetConstant(StringValue.of(GeoTargetConstantName.format(String.valueOf(locationId))));
		return criterionBuilder.build();
	}
	private static CampaignCriterion buildLanguageIdCriterion(long linguaId, String campaignResourceName) {
		Builder criterionBuilder = CampaignCriterion.newBuilder().setCampaign(StringValue.of(campaignResourceName));
		criterionBuilder.getLanguageBuilder()
				.setLanguageConstant(StringValue.of(LanguageConstantName.format(String.valueOf(linguaId))));
		return criterionBuilder.build();
	}
	
	
	
	
	
	private String addCampaignBudget(GoogleAdsClient googleAdsClient, long customerId) {
		
		Long valor = (long) (this.campanha.getSetupCampanha().getBudgetDiario() * 1000000);
		
		CampaignBudget budget = CampaignBudget.newBuilder()
				.setName(StringValue.of(campanha.getNome() + "_CustoDia_" + System.currentTimeMillis()))
				.setDeliveryMethod(BudgetDeliveryMethod.STANDARD).setExplicitlyShared(BoolValue.of(false))
				.setAmountMicros(Int64Value.of(valor))
				.build();

		CampaignBudgetOperation op = CampaignBudgetOperation.newBuilder().setCreate(budget).build();

		try (CampaignBudgetServiceClient campaignBudgetServiceClient = googleAdsClient.getLatestVersion()
				.createCampaignBudgetServiceClient()) {
			MutateCampaignBudgetsResponse response = campaignBudgetServiceClient
					.mutateCampaignBudgets(Long.toString(customerId), ImmutableList.of(op));
			String budgetResourceName = response.getResults(0).getResourceName();
			System.out.printf("Added budget: %s%n", budgetResourceName);
			return budgetResourceName;
		}
	}
	
	private void adicionaGrupo(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName) {
	    // Creates an ad group, setting an optional CPC value.
	    AdGroup adGroup1 =
	        AdGroup.newBuilder()
	            .setName(StringValue.of(campanha.getNome() + "_Grp_" + System.currentTimeMillis()))
	            .setStatus(AdGroupStatus.ENABLED)
	            .setCampaign(StringValue.of(campaignResourceName))
	            .build();

	  
	    List<AdGroupOperation> operations = new ArrayList<>();
	    operations.add(AdGroupOperation.newBuilder().setCreate(adGroup1).build());
	 
	    try (AdGroupServiceClient adGroupServiceClient = googleAdsClient.getLatestVersion().createAdGroupServiceClient()) {
	      MutateAdGroupsResponse response = adGroupServiceClient.mutateAdGroups(Long.toString(customerId), operations);
	      System.out.printf("Added %d ad groups:%n", response.getResultsCount());
	      for (MutateAdGroupResult result : response.getResultsList()) {
	        System.out.println(result.getResourceName());
	        adicionaAnuncio(googleAdsClient,customerId, result.getResourceName());
	      }
	    }
	  
	}

	private void adicionaAnuncio(GoogleAdsClient googleAdsClient, long customerId, String adGroupResourceName) {
		//AnuncioAplicativo anuncio = this.campanha.getAnuncioAplicativo();
		AnuncioAplicacaoResultado rel = this.campanha.getAnuncioAplicacaoResultados().get(0);
		AnuncioAplicativo anuncio = rel.getAnuncioAplicativo();
		List<AdGroupAdOperation> operations = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			AdTextAsset titulo1 = AdTextAsset.newBuilder().setText(StringValue.of(anuncio.getTitulo1())).build();
			AdTextAsset titulo2 = AdTextAsset.newBuilder().setText(StringValue.of(anuncio.getTitulo2())).build();
			AdTextAsset titulo3 = AdTextAsset.newBuilder().setText(StringValue.of(anuncio.getTitulo3())).build();
			AdTextAsset titulo4 = AdTextAsset.newBuilder().setText(StringValue.of(anuncio.getTitulo4())).build();

			// Creates the expanded text ad info.
			AppAdInfo appAd = AppAdInfo.newBuilder()
					.addHeadlines(titulo1)
					.addHeadlines(titulo2)
					.addDescriptions(titulo3)
					.addDescriptions(titulo4)
					.build();
			

			Ad ad = Ad.newBuilder().setAppAd(appAd).build();

			// Builds the final ad group ad representation.
			AdGroupAd adGroupAd = AdGroupAd.newBuilder()
					.setAdGroup(StringValue.of(adGroupResourceName))
					.setAd(ad)
					.build();

			AdGroupAdOperation op = AdGroupAdOperation.newBuilder().setCreate(adGroupAd).build();
			operations.add(op);
		}

		try (AdGroupAdServiceClient adGroupAdServiceClient = googleAdsClient.getLatestVersion()
				.createAdGroupAdServiceClient()) {
			MutateAdGroupAdsResponse response = adGroupAdServiceClient.mutateAdGroupAds(Long.toString(customerId),
					operations);
			for (MutateAdGroupAdResult result : response.getResultsList()) {
				System.out.printf("Adicionou anuncio: %s%n", result.getResourceName());
				rel.setIdAds(this.getIdAds(result));
				
			}
		}

	}
}
