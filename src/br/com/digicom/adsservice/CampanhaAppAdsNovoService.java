package br.com.digicom.adsservice;

import java.util.ArrayList;
import java.util.List;

import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.common.AdTextAsset;
import com.google.ads.googleads.v2.common.AppAdInfo;
import com.google.ads.googleads.v2.common.TargetCpa;
import com.google.ads.googleads.v2.enums.AdGroupAdStatusEnum.AdGroupAdStatus;
import com.google.ads.googleads.v2.enums.AdGroupStatusEnum.AdGroupStatus;
import com.google.ads.googleads.v2.enums.AdvertisingChannelSubTypeEnum.AdvertisingChannelSubType;
import com.google.ads.googleads.v2.enums.AdvertisingChannelTypeEnum.AdvertisingChannelType;
import com.google.ads.googleads.v2.enums.AppCampaignAppStoreEnum.AppCampaignAppStore;
import com.google.ads.googleads.v2.enums.AppCampaignBiddingStrategyGoalTypeEnum.AppCampaignBiddingStrategyGoalType;
import com.google.ads.googleads.v2.enums.BudgetDeliveryMethodEnum.BudgetDeliveryMethod;
import com.google.ads.googleads.v2.enums.CampaignStatusEnum.CampaignStatus;
import com.google.ads.googleads.v2.enums.PositiveGeoTargetTypeEnum.PositiveGeoTargetType;
import com.google.ads.googleads.v2.resources.Ad;
import com.google.ads.googleads.v2.resources.AdGroup;
import com.google.ads.googleads.v2.resources.AdGroupAd;
import com.google.ads.googleads.v2.resources.Campaign;
import com.google.ads.googleads.v2.resources.Campaign.AppCampaignSetting;
import com.google.ads.googleads.v2.resources.Campaign.GeoTargetTypeSetting;
import com.google.ads.googleads.v2.resources.CampaignBudget;
import com.google.ads.googleads.v2.resources.CampaignCriterion;
import com.google.ads.googleads.v2.resources.CampaignCriterion.Builder;
import com.google.ads.googleads.v2.resources.GeoTargetConstantName;
import com.google.ads.googleads.v2.resources.LanguageConstantName;
import com.google.ads.googleads.v2.services.AdGroupAdOperation;
import com.google.ads.googleads.v2.services.AdGroupAdServiceClient;
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
import com.google.ads.googleads.v2.services.MutateAdGroupResult;
import com.google.ads.googleads.v2.services.MutateAdGroupsResponse;
import com.google.ads.googleads.v2.services.MutateCampaignBudgetsResponse;
import com.google.ads.googleads.v2.services.MutateCampaignCriteriaResponse;
import com.google.ads.googleads.v2.services.MutateCampaignCriterionResult;
import com.google.ads.googleads.v2.services.MutateCampaignResult;
import com.google.ads.googleads.v2.services.MutateCampaignsResponse;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

import br.com.digicom.AdsService;
import br.com.digicom.modelo.CampanhaAds;

public class CampanhaAppAdsNovoService extends AdsService {

	private CampanhaAds campanha = null;

	
	public void cria(CampanhaAds campanha) {
		this.campanha = campanha;
		super.executa();
	}
	
	@Override
	protected void runExample(GoogleAdsClient googleAdsClient, long customerId) {
		
		String budgetResourceName = addCampaignBudget(googleAdsClient, customerId);
		List<CampaignOperation> operations = new ArrayList<>(1);
		
		
		AppCampaignSetting universalSetting = AppCampaignSetting.newBuilder()
				.setAppId(StringValue.of(this.campanha.getVersaoApp().getPacoteApp()))
				.setAppStore(AppCampaignAppStore.GOOGLE_APP_STORE)
				.setBiddingStrategyGoalType(AppCampaignBiddingStrategyGoalType.OPTIMIZE_INSTALLS_TARGET_INSTALL_COST)
				.build();

		GeoTargetTypeSetting geoSetting = GeoTargetTypeSetting.newBuilder()
				.setPositiveGeoTargetType(PositiveGeoTargetType.PRESENCE_OR_INTEREST)
				.build();
	
		
		Long valorCpa = (long) (this.campanha.getSetupCampanha().getCustoInstalacao() * 1000000);
		TargetCpa cpa = TargetCpa.newBuilder()
				.setTargetCpaMicros(Int64Value.of(valorCpa))
				.build();
		UtilAds.setCampanha(campanha);
		Campaign campaign = Campaign.newBuilder()
				.setName(StringValue.of(this.campanha.getNome() + "_" + System.currentTimeMillis()))
				.setStatus(CampaignStatus.PAUSED)
				.setAdvertisingChannelType(AdvertisingChannelType.MULTI_CHANNEL)
				.setAdvertisingChannelSubType(AdvertisingChannelSubType.APP_CAMPAIGN)
				.setStartDate(StringValue.of(UtilAds.getDataInicial()))
				.setEndDate(StringValue.of(UtilAds.getDataFinal()))
				.setAppCampaignSetting(universalSetting).setGeoTargetTypeSetting(geoSetting)
				.setTargetCpa(cpa)
				.setCampaignBudget(StringValue.of(budgetResourceName))
				.build();
		
		
		CampaignOperation op = CampaignOperation.newBuilder()
				.setCreate(campaign)
				.build();
		operations.add(op);
		
		try (CampaignServiceClient campaignServiceClient = googleAdsClient.getLatestVersion().createCampaignServiceClient()) {
			MutateCampaignsResponse response = campaignServiceClient.mutateCampaigns(Long.toString(customerId),	operations);
			System.out.printf("Added %d campaigns:%n", response.getResultsCount());
			for (MutateCampaignResult result : response.getResultsList()) {
				System.out.println(result.getResourceName());
				campanha.setIdAds(getIdAds(result));
				campanha.setDataInicial(UtilAds.getDataInicial());
				campanha.setDataFinal(UtilAds.getDataFinal());
				montandoCriterios(googleAdsClient,customerId, result.getResourceName());
				adicionaGrupo(googleAdsClient,customerId, result.getResourceName());
			}
		}
		
	}
	
	
	private String getIdAds(MutateCampaignResult result) {
		String[] palavras = result.getResourceName().split("/");
		return palavras[3];
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
		List<AdGroupAdOperation> operations = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			AdTextAsset titulo1 = AdTextAsset.newBuilder().setText(StringValue.of(this.campanha.getAnuncioAplicativo().getTitulo1())).build();
			AdTextAsset titulo2 = AdTextAsset.newBuilder().setText(StringValue.of(this.campanha.getAnuncioAplicativo().getTitulo2())).build();
			AdTextAsset titulo3 = AdTextAsset.newBuilder().setText(StringValue.of(this.campanha.getAnuncioAplicativo().getTitulo3())).build();
			AdTextAsset titulo4 = AdTextAsset.newBuilder().setText(StringValue.of(this.campanha.getAnuncioAplicativo().getTitulo4())).build();

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
			}
		}

	}
}
