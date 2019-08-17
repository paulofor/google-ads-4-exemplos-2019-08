package br.com.digicom.adsservice;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.beust.jcommander.Parameter;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroup;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupAd;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupAdOperation;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupAdReturnValue;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupAdRotationMode;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupAdServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupAdStatus;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupCriterion;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupCriterionOperation;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupOperation;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupReturnValue;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.AdGroupStatus;
import com.google.api.ads.adwords.axis.v201809.cm.AdRotationMode;
import com.google.api.ads.adwords.axis.v201809.cm.AdvertisingChannelSubType;
import com.google.api.ads.adwords.axis.v201809.cm.AdvertisingChannelType;
import com.google.api.ads.adwords.axis.v201809.cm.ApiException;
import com.google.api.ads.adwords.axis.v201809.cm.BiddableAdGroupCriterion;
import com.google.api.ads.adwords.axis.v201809.cm.BiddingStrategyConfiguration;
import com.google.api.ads.adwords.axis.v201809.cm.BiddingStrategyType;
import com.google.api.ads.adwords.axis.v201809.cm.Bids;
import com.google.api.ads.adwords.axis.v201809.cm.Budget;
import com.google.api.ads.adwords.axis.v201809.cm.BudgetBudgetDeliveryMethod;
import com.google.api.ads.adwords.axis.v201809.cm.BudgetOperation;
import com.google.api.ads.adwords.axis.v201809.cm.BudgetServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.Campaign;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignCriterion;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignCriterionOperation;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignOperation;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignReturnValue;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.CampaignStatus;
import com.google.api.ads.adwords.axis.v201809.cm.CpcBid;
import com.google.api.ads.adwords.axis.v201809.cm.Criterion;
import com.google.api.ads.adwords.axis.v201809.cm.ExpandedTextAd;
import com.google.api.ads.adwords.axis.v201809.cm.GeoTargetTypeSetting;
import com.google.api.ads.adwords.axis.v201809.cm.GeoTargetTypeSettingNegativeGeoTargetType;
import com.google.api.ads.adwords.axis.v201809.cm.GeoTargetTypeSettingPositiveGeoTargetType;
import com.google.api.ads.adwords.axis.v201809.cm.Keyword;
import com.google.api.ads.adwords.axis.v201809.cm.KeywordMatchType;
import com.google.api.ads.adwords.axis.v201809.cm.Language;
import com.google.api.ads.adwords.axis.v201809.cm.Location;
import com.google.api.ads.adwords.axis.v201809.cm.MobileApplicationVendor;
import com.google.api.ads.adwords.axis.v201809.cm.Money;
import com.google.api.ads.adwords.axis.v201809.cm.Operator;
import com.google.api.ads.adwords.axis.v201809.cm.Setting;
import com.google.api.ads.adwords.axis.v201809.cm.TargetCpaBiddingScheme;
import com.google.api.ads.adwords.axis.v201809.cm.UniversalAppBiddingStrategyGoalType;
import com.google.api.ads.adwords.axis.v201809.cm.UniversalAppCampaignSetting;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.adwords.lib.utils.examples.ArgumentNames;
import com.google.api.ads.common.lib.utils.examples.CodeSampleParams;

import br.com.digicom.AdsService;
import br.com.digicom.modelo.CampanhaAds;
import br.com.digicom.modelo.CampanhaAnuncioResultado;
import br.com.digicom.modelo.CampanhaPalavraChaveResultado;

public class CampanhaAplicacaoService extends AdsService {

	private CampanhaAds campanha = null;

	
	@Override
	protected void runExample(AdWordsServicesInterface adWordsServices, AdWordsSession session)
			throws RemoteException, ApiException {
		
		// Get the BudgetService.
		BudgetServiceInterface budgetService =
		    adWordsServices.get(session, BudgetServiceInterface.class);

		// Create the campaign budget.
		Budget budget = new Budget();
		budget.setName("Interplanetary Cruise App Budget #" + System.currentTimeMillis());
		Money budgetAmount = new Money();
		budgetAmount.setMicroAmount(50000000L);
		budget.setAmount(budgetAmount);
		budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);

		// Universal app campaigns don't support shared budgets.
		budget.setIsExplicitlyShared(false);
		BudgetOperation budgetOperation = new BudgetOperation();
		budgetOperation.setOperand(budget);
		budgetOperation.setOperator(Operator.ADD);

		// Add the budget
		Budget addedBudget = budgetService.mutate(new BudgetOperation[] {budgetOperation}).getValue(0);
		
		
		// Get the CampaignService.
		CampaignServiceInterface campaignService =
		    adWordsServices.get(session, CampaignServiceInterface.class);

		// Create the campaign.
		Campaign campaign = new Campaign();
		campaign.setName("Interplanetary Cruise App #" + System.currentTimeMillis());

		// Recommendation: Set the campaign to PAUSED when creating it to prevent
		// the ads from immediately serving. Set to ENABLED once you've added
		// targeting and the ads are ready to serve.
		campaign.setStatus(CampaignStatus.PAUSED);

		// Set the advertising channel and subchannel types for universal app campaigns.
		campaign.setAdvertisingChannelType(AdvertisingChannelType.MULTI_CHANNEL);
		campaign.setAdvertisingChannelSubType(AdvertisingChannelSubType.UNIVERSAL_APP_CAMPAIGN);

		// Set the campaign's bidding strategy. universal app campaigns
		// only support TARGET_CPA bidding strategy.
		BiddingStrategyConfiguration biddingConfig = new BiddingStrategyConfiguration();
		biddingConfig.setBiddingStrategyType(BiddingStrategyType.TARGET_CPA);

		// Set the target CPA to $1 / app install.
		TargetCpaBiddingScheme biddingScheme = new TargetCpaBiddingScheme();
		biddingScheme.setTargetCpa(new Money());
		biddingScheme.getTargetCpa().setMicroAmount(1000000L);

		biddingConfig.setBiddingScheme(biddingScheme);
		campaign.setBiddingStrategyConfiguration(biddingConfig);

		// Set the campaign's budget.
		campaign.setBudget(new Budget());
		campaign.getBudget().setBudgetId(createBudget(adWordsServices, session));

		// Optional: Set the start date.
		campaign.setStartDate(new DateTime().plusDays(1).toString("yyyyMMdd"));

		// Optional: Set the end date.
		campaign.setEndDate(new DateTime().plusYears(1).toString("yyyyMMdd"));
		
		// Set the campaign's assets and ad text ideas. These values will be used to
		// generate ads.
		UniversalAppCampaignSetting universalAppSetting = new UniversalAppCampaignSetting();
		universalAppSetting.setAppId("com.labpixies.colordrips");
		universalAppSetting.setAppVendor(MobileApplicationVendor.VENDOR_GOOGLE_MARKET);
		universalAppSetting.setDescription1("A cool puzzle game");
		universalAppSetting.setDescription2("Remove connected blocks");
		universalAppSetting.setDescription3("3 difficulty levels");
		universalAppSetting.setDescription4("4 colorful fun skins");

		// Optional: You can set up to 20 image assets for your campaign.
		// See UploadImage.java for an example on how to upload images.
		//
		// universalAppSetting.setImageMediaIds(new long[] { INSERT_IMAGE_MEDIA_ID_HERE });
		    
		// Optimize this campaign for getting new users for your app.
		//universalAppSetting.setUniversalAppBiddingStrategyGoalType(
		//    UniversalAppBiddingStrategyGoalType.OPTIMIZE_FOR_INSTALL_CONVERSION_VOLUME);

		// If you select the OPTIMIZE_FOR_IN_APP_CONVERSION_VOLUME goal type, then also specify
		// your in-app conversion types so AdWords can focus your campaign on people who are
		// most likely to complete the corresponding in-app actions.
		// Conversion type IDs can be retrieved using ConversionTrackerService.get.
		//
		// campaign.selectiveOptimization = new SelectiveOptimization();
		// campaign.selectiveOptimization.conversionTypeIds =
//		    new long[] { INSERT_CONVERSION_TYPE_ID_1_HERE, INSERT_CONVERSION_TYPE_ID_2_HERE };

		// Optional: Set the campaign settings for Advanced location options.
		GeoTargetTypeSetting geoSetting = new GeoTargetTypeSetting();
		geoSetting.setPositiveGeoTargetType(
		    GeoTargetTypeSettingPositiveGeoTargetType.LOCATION_OF_PRESENCE);
		geoSetting.setNegativeGeoTargetType(GeoTargetTypeSettingNegativeGeoTargetType.DONT_CARE);

		campaign.setSettings(new Setting[] {universalAppSetting, geoSetting});
		   
		// Create the operation.
		CampaignOperation operation = new CampaignOperation();
		operation.setOperand(campaign);
		operation.setOperator(Operator.ADD);

		CampaignOperation[] operations = new CampaignOperation[] {operation};

		// Add the campaign.
		CampaignReturnValue result = campaignService.mutate(operations);

		// Display the results.
		for (Campaign newCampaign : result.getValue()) {
		  System.out.printf(
		      "Universal app campaign with name '%s' and ID %d was added.%n",
		      newCampaign.getName(), newCampaign.getId());

		  // Optional: Set the campaign's location and language targeting. No other targeting
		  // criteria can be used for universal app campaigns.
		  setCampaignTargetingCriteria(newCampaign, adWordsServices, session);
		}
		    
	
	}
	

	/** Sets the campaign's targeting criteria. */
	private void setCampaignTargetingCriteria(Campaign campaign, AdWordsServicesInterface adWordsServices,
			AdWordsSession session) throws ApiException, RemoteException {
		// Get the CampaignCriterionService.
		CampaignCriterionServiceInterface campaignCriterionService = adWordsServices.get(session,
				CampaignCriterionServiceInterface.class);

		// Create locations. The IDs can be found in the documentation or
		// retrieved with the LocationCriterionService.
		Location pais = new Location(); 
		pais.setId(2076L); 
		Language lingua = new Language(); 
		lingua.setId(1014L);


		List<Criterion> criteria = new ArrayList<>(Arrays.asList(pais,lingua));

		// Create operations to add each of the criteria above.
		List<CampaignCriterionOperation> operations = new ArrayList<>();
		for (Criterion criterion : criteria) {
			CampaignCriterionOperation operation = new CampaignCriterionOperation();

			CampaignCriterion campaignCriterion = new CampaignCriterion();
			campaignCriterion.setCampaignId(campaign.getId());
			campaignCriterion.setCriterion(criterion);
			operation.setOperand(campaignCriterion);

			operation.setOperator(Operator.ADD);

			operations.add(operation);
		}

		
		
		
		// Set the campaign targets.
		CampaignCriterionReturnValue returnValue = campaignCriterionService
				.mutate(operations.toArray(new CampaignCriterionOperation[operations.size()]));

		if (returnValue != null && returnValue.getValue() != null) {
			// Display added campaign targets.
			for (CampaignCriterion campaignCriterion : returnValue.getValue()) {
				System.out.printf("Campaign criteria of type '%s' and ID %d was added.%n",
						campaignCriterion.getCriterion().getCriterionType(), campaignCriterion.getCriterion().getId());
			}
		}
	}

	private Long createBudget(AdWordsServicesInterface adWordsServices, AdWordsSession session)
			throws RemoteException, ApiException {
		// Get the BudgetService.
		BudgetServiceInterface budgetService = adWordsServices.get(session, BudgetServiceInterface.class);

		// Create the campaign budget.
		Budget budget = new Budget();
		budget.setName("Interplanetary Cruise App Budget #" + System.currentTimeMillis());

		// Money
		Money budgetAmount = new Money();
		Long valor = (long) (this.campanha.getSetupCampanha().getBudgetDiario() * 1000000);

		budgetAmount.setMicroAmount(valor);
		budget.setAmount(budgetAmount);
		budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);

		// Universal app campaigns don't support shared budgets.
		budget.setIsExplicitlyShared(false);
		BudgetOperation budgetOperation = new BudgetOperation();
		budgetOperation.setOperand(budget);
		budgetOperation.setOperator(Operator.ADD);

		// Add the budget
		Budget addedBudget = budgetService.mutate(new BudgetOperation[] { budgetOperation }).getValue(0);
		return addedBudget.getBudgetId();
	}

	private Long criaBudget(Budget budget, AdWordsServicesInterface adWordsServices, AdWordsSession session)
			throws RemoteException, ApiException {
		BudgetOperation budgetOperation = new BudgetOperation();
		budgetOperation.setOperand(budget);
		budgetOperation.setOperator(Operator.ADD);

		BudgetServiceInterface budgetService = adWordsServices.get(session, BudgetServiceInterface.class);
		// Add the budget
		Long budgetId = budgetService.mutate(new BudgetOperation[] { budgetOperation }).getValue(0).getBudgetId();
		return budgetId;
	}

	private static class AddAdGroupsParams extends CodeSampleParams {
		@Parameter(names = ArgumentNames.CAMPAIGN_ID, required = true)
		private Long campaignId;
	}

	private static class AddExpandedTextAdsParams extends CodeSampleParams {
		@Parameter(names = ArgumentNames.AD_GROUP_ID, required = true)
		private Long adGroupId;
	}

	private static class AddKeywordsParams extends CodeSampleParams {
		@Parameter(names = ArgumentNames.AD_GROUP_ID, required = true)
		private Long adGroupId;
	}

	private void criaAnuncio(CampanhaAds campanha, Long idGrupo, AdWordsServicesInterface adWordsServices,
			AdWordsSession session) throws ApiException, RemoteException {

		AddExpandedTextAdsParams params = new AddExpandedTextAdsParams();
		params.adGroupId = idGrupo;

		// Get the AdGroupAdService.
		AdGroupAdServiceInterface adGroupAdService = adWordsServices.get(session, AdGroupAdServiceInterface.class);

		List<AdGroupAdOperation> operations = new ArrayList<>();

		List<CampanhaAnuncioResultado> anuncios = campanha.getCampanhaAnuncioResultados();
		for (CampanhaAnuncioResultado anuncio : anuncios) {
			// Create expanded text ad.
			ExpandedTextAd expandedTextAd = new ExpandedTextAd();
			expandedTextAd.setHeadlinePart1(anuncio.getAnuncioAds().getTitulo1());
			expandedTextAd.setHeadlinePart2(anuncio.getAnuncioAds().getTitulo2());
			expandedTextAd.setDescription(anuncio.getAnuncioAds().getDescricao1());
			expandedTextAd.setFinalUrls(new String[] { campanha.getUrlAlvo() });
			expandedTextAd.setFinalMobileUrls(new String[] { campanha.getUrlAlvoMobile() });

			// Create ad group ad.
			AdGroupAd expandedTextAdGroupAd = new AdGroupAd();
			expandedTextAdGroupAd.setAdGroupId(idGrupo);
			expandedTextAdGroupAd.setAd(expandedTextAd);

			// Optional: set the status.
			expandedTextAdGroupAd.setStatus(AdGroupAdStatus.ENABLED);

			// Create the operation.
			AdGroupAdOperation adGroupAdOperation = new AdGroupAdOperation();
			adGroupAdOperation.setOperand(expandedTextAdGroupAd);
			adGroupAdOperation.setOperator(Operator.ADD);

			operations.add(adGroupAdOperation);
		}
		// Add ads.
		AdGroupAdReturnValue result = adGroupAdService
				.mutate(operations.toArray(new AdGroupAdOperation[operations.size()]));

		// Display ads.
		int posicao = 0;
		for (AdGroupAd adGroupAdResult : result.getValue()) {
			ExpandedTextAd newAd = (ExpandedTextAd) adGroupAdResult.getAd();
			System.out.printf("Expanded text ad with ID %d and headline '%s - %s' was added.%n", newAd.getId(),
					newAd.getHeadlinePart1(), newAd.getHeadlinePart2());
			// Nao tenho certeza da ordem
			campanha.getCampanhaAnuncioResultados().get(posicao).setIdAds("" + newAd.getId());
			posicao++;
		}

	}

	private void criaPalavraChave(CampanhaAds campanha, Long idGrupo, AdWordsServicesInterface adWordsServices,
			AdWordsSession session) throws ApiException, RemoteException {
		AddKeywordsParams params = new AddKeywordsParams();
		params.adGroupId = idGrupo;
		AdGroupCriterionServiceInterface adGroupCriterionService = adWordsServices.get(session,
				AdGroupCriterionServiceInterface.class);

		List<AdGroupCriterionOperation> listaOperacao = new ArrayList<AdGroupCriterionOperation>();

		List<CampanhaPalavraChaveResultado> palavras = campanha.getCampanhaPalavraChaveResultados();
		for (CampanhaPalavraChaveResultado palavra : palavras) {

			// Create keywords.
			Keyword keyword1 = new Keyword();
			keyword1.setText(palavra.getPalavraChaveGoogleId());
			KeywordMatchType tipoMatch = KeywordMatchType
					.fromString(this.campanha.getSetupCampanha().getMatchPalavra());
			keyword1.setMatchType(tipoMatch);
			// Create biddable ad group criterion.
			BiddableAdGroupCriterion keywordBiddableAdGroupCriterion1 = new BiddableAdGroupCriterion();
			keywordBiddableAdGroupCriterion1.setAdGroupId(idGrupo);
			keywordBiddableAdGroupCriterion1.setCriterion(keyword1);
			// Create operations.
			AdGroupCriterionOperation keywordAdGroupCriterionOperation1 = new AdGroupCriterionOperation();
			keywordAdGroupCriterionOperation1.setOperand(keywordBiddableAdGroupCriterion1);
			keywordAdGroupCriterionOperation1.setOperator(Operator.ADD);
			listaOperacao.add(keywordAdGroupCriterionOperation1);

			/*
			 * Keyword keyword2 = new Keyword(); keyword2.setText(palavra.getPalavra());
			 * keyword2.setMatchType(KeywordMatchType.PHRASE); BiddableAdGroupCriterion
			 * keywordBiddableAdGroupCriterion2 = new BiddableAdGroupCriterion();
			 * keywordBiddableAdGroupCriterion2.setAdGroupId(idGrupo);
			 * keywordBiddableAdGroupCriterion2.setCriterion(keyword2);
			 * AdGroupCriterionOperation keywordAdGroupCriterionOperation2 = new
			 * AdGroupCriterionOperation(); keywordAdGroupCriterionOperation2.setOperand
			 * (keywordBiddableAdGroupCriterion2 );
			 * keywordAdGroupCriterionOperation2.setOperator(Operator.ADD);
			 * listaOperacao.add(keywordAdGroupCriterionOperation2);
			 */
		}
		// AdGroupCriterionOperation keywordAdGroupCriterionOperation2 = new
		// AdGroupCriterionOperation();
		// keywordAdGroupCriterionOperation2.setOperand(keywordNegativeAdGroupCriterion2);
		// keywordAdGroupCriterionOperation2.setOperator(Operator.ADD);

		// AdGroupCriterionOperation[] operations = new
		// AdGroupCriterionOperation[] { keywordAdGroupCriterionOperation1 };
		AdGroupCriterionOperation[] operations = listaOperacao.toArray(new AdGroupCriterionOperation[0]);

		// Add keywords.
		AdGroupCriterionReturnValue result = adGroupCriterionService.mutate(operations);

		// Display results.
		int posicao = 0;
		for (AdGroupCriterion adGroupCriterionResult : result.getValue()) {
			System.out.printf(
					"Keyword ad group criterion with ad group ID %d, criterion ID %d, "
							+ "text '%s', and match type '%s' was added.%n",
					adGroupCriterionResult.getAdGroupId(), adGroupCriterionResult.getCriterion().getId(),
					((Keyword) adGroupCriterionResult.getCriterion()).getText(),
					((Keyword) adGroupCriterionResult.getCriterion()).getMatchType());
			campanha.getCampanhaPalavraChaveResultados().get(posicao)
					.setIdAds("" + adGroupCriterionResult.getCriterion().getId());
			posicao++;
		}

	}

	private void criarGrupoAnuncio(CampanhaAds campanha, Long idCampanha, AdWordsServicesInterface adWordsServices,
			AdWordsSession session) throws RemoteException, ApiException {
		AddAdGroupsParams params = new AddAdGroupsParams();

		params.campaignId = idCampanha;
		// Get the AdGroupService.
		AdGroupServiceInterface adGroupService = adWordsServices.get(session, AdGroupServiceInterface.class);

		// Create ad group.
		AdGroup adGroup = new AdGroup();
		adGroup.setName("Grp_" + this.campanha.getNome() + "_" + System.currentTimeMillis());
		adGroup.setStatus(AdGroupStatus.ENABLED);
		adGroup.setCampaignId(idCampanha);

		// Create ad group bid.
		Long valor = (long) (campanha.getSetupCampanha().getMaxCpcGrupoAnuncio() * 1000000);
		BiddingStrategyConfiguration biddingStrategyConfiguration = new BiddingStrategyConfiguration();
		Money cpcBidMoney = new Money();
		cpcBidMoney.setMicroAmount(valor);
		CpcBid bid = new CpcBid();
		bid.setBid(cpcBidMoney);
		biddingStrategyConfiguration.setBids(new Bids[] { bid });
		adGroup.setBiddingStrategyConfiguration(biddingStrategyConfiguration);

		// Set the rotation mode.
		AdRotationMode tipoRotacao = AdRotationMode.fromString(this.campanha.getSetupCampanha().getRotacaoAnuncio());
		AdGroupAdRotationMode rotationMode = new AdGroupAdRotationMode(tipoRotacao);
		adGroup.setAdGroupAdRotationMode(rotationMode);

		AdGroupOperation operation = new AdGroupOperation();
		operation.setOperand(adGroup);
		operation.setOperator(Operator.ADD);

		AdGroupOperation[] operations = new AdGroupOperation[] { operation };

		// Add ad groups.
		AdGroupReturnValue result = adGroupService.mutate(operations);

		// Display new ad groups.
		for (AdGroup adGroupResult : result.getValue()) {
			System.out.printf("Ad group with name '%s' and ID %d was added.%n", adGroupResult.getName(),
					adGroupResult.getId());
			this.criaAnuncio(campanha, adGroupResult.getId(), adWordsServices, session);
			this.criaPalavraChave(campanha, adGroupResult.getId(), adWordsServices, session);
		}

	}

	public void cria(CampanhaAds campanha) {
		// TODO Auto-generated method stub
		this.campanha = campanha;
		super.executa();
	}

	

}
