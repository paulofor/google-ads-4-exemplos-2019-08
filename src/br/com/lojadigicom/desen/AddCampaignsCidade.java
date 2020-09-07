// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package br.com.lojadigicom.desen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.beust.jcommander.Parameter;
import com.google.ads.googleads.examples.utils.ArgumentNames;
import com.google.ads.googleads.examples.utils.CodeSampleParams;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.common.TargetCpa;
import com.google.ads.googleads.v2.common.MaximizeConversionValue;
import com.google.ads.googleads.v2.common.TargetSpend;
import com.google.ads.googleads.v2.enums.AdvertisingChannelSubTypeEnum.AdvertisingChannelSubType;
import com.google.ads.googleads.v2.enums.AdvertisingChannelTypeEnum.AdvertisingChannelType;
import com.google.ads.googleads.v2.enums.AppCampaignAppStoreEnum.AppCampaignAppStore;
import com.google.ads.googleads.v2.enums.AppCampaignBiddingStrategyGoalTypeEnum.AppCampaignBiddingStrategyGoalType;
import com.google.ads.googleads.v2.enums.BudgetDeliveryMethodEnum.BudgetDeliveryMethod;
import com.google.ads.googleads.v2.enums.CampaignStatusEnum.CampaignStatus;
import com.google.ads.googleads.v2.enums.PositiveGeoTargetTypeEnum.PositiveGeoTargetType;
import com.google.ads.googleads.v2.errors.GoogleAdsError;
import com.google.ads.googleads.v2.errors.GoogleAdsException;
import com.google.ads.googleads.v2.resources.BiddingStrategy;
import com.google.ads.googleads.v2.resources.Campaign;
import com.google.ads.googleads.v2.resources.Campaign.AppCampaignSetting;
import com.google.ads.googleads.v2.resources.Campaign.GeoTargetTypeSetting;
import com.google.ads.googleads.v2.resources.Campaign.NetworkSettings;
import com.google.ads.googleads.v2.resources.CampaignCriterion.Builder;
import com.google.ads.googleads.v2.resources.CampaignBudget;
import com.google.ads.googleads.v2.resources.CampaignCriterion;
import com.google.ads.googleads.v2.resources.CampaignName;
import com.google.ads.googleads.v2.resources.GeoTargetConstantName;
import com.google.ads.googleads.v2.services.BiddingStrategyOperation;
import com.google.ads.googleads.v2.services.BiddingStrategyServiceClient;
import com.google.ads.googleads.v2.services.CampaignBudgetOperation;
import com.google.ads.googleads.v2.services.CampaignBudgetServiceClient;
import com.google.ads.googleads.v2.services.CampaignCriterionOperation;
import com.google.ads.googleads.v2.services.CampaignCriterionServiceClient;
import com.google.ads.googleads.v2.services.CampaignOperation;
import com.google.ads.googleads.v2.services.CampaignServiceClient;
import com.google.ads.googleads.v2.services.MutateBiddingStrategiesResponse;
import com.google.ads.googleads.v2.services.MutateBiddingStrategyResult;
import com.google.ads.googleads.v2.services.MutateCampaignBudgetsResponse;
import com.google.ads.googleads.v2.services.MutateCampaignCriteriaResponse;
import com.google.ads.googleads.v2.services.MutateCampaignCriterionResult;
import com.google.ads.googleads.v2.services.MutateCampaignResult;
import com.google.ads.googleads.v2.services.MutateCampaignsResponse;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.protobuf.BoolValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

/** Adds new campaigns to a client account. */
public class AddCampaignsCidade {

	/** The number of campaigns this example will add. */
	private static final int NUMBER_OF_CAMPAIGNS_TO_ADD = 1;

	private static class AddCampaignsParams extends CodeSampleParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, required = true)
		private Long customerId;
	}

	public static void main(String[] args) {
		AddCampaignsParams params = new AddCampaignsParams();
		if (!params.parseArguments(args)) {

			// Either pass the required parameters for this example on the command line, or
			// insert them
			// into the code here. See the parameter class definition above for
			// descriptions.
			params.customerId = Long.parseLong("5328916093");
		}

		GoogleAdsClient googleAdsClient;
		try {
			googleAdsClient = GoogleAdsClient.newBuilder().fromPropertiesFile().build();
		} catch (FileNotFoundException fnfe) {
			System.err.printf("Failed to load GoogleAdsClient configuration from file. Exception: %s%n", fnfe);
			return;
		} catch (IOException ioe) {
			System.err.printf("Failed to create GoogleAdsClient. Exception: %s%n", ioe);
			return;
		}

		try {
			new AddCampaignsCidade().runExample(googleAdsClient, params.customerId);
		} catch (GoogleAdsException gae) {
			// GoogleAdsException is the base class for most exceptions thrown by an API
			// request.
			// Instances of this exception have a message and a GoogleAdsFailure that
			// contains a
			// collection of GoogleAdsErrors that indicate the underlying causes of the
			// GoogleAdsException.
			System.err.printf("Request ID %s failed due to GoogleAdsException. Underlying errors:%n",
					gae.getRequestId());
			int i = 0;
			for (GoogleAdsError googleAdsError : gae.getGoogleAdsFailure().getErrorsList()) {
				System.err.printf("  Error %d: %s%n", i++, googleAdsError);
			}
		}
	}

	/**
	 * Creates a new CampaignBudget in the specified client account.
	 *
	 * @param googleAdsClient the Google Ads API client.
	 * @param customerId      the client customer ID.
	 * @return resource name of the newly created budget.
	 * @throws GoogleAdsException if an API request failed with one or more service
	 *                            errors.
	 */
	private static String addCampaignBudget(GoogleAdsClient googleAdsClient, long customerId) {
		CampaignBudget budget = CampaignBudget.newBuilder()
				.setName(StringValue.of("Interplanetary Cruise Budget #" + System.currentTimeMillis()))
				.setDeliveryMethod(BudgetDeliveryMethod.STANDARD).setExplicitlyShared(BoolValue.of(false))
				.setAmountMicros(Int64Value.of(500_000)).build();

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

	

	// ** Versao Nova

	public static void runExample(GoogleAdsClient googleAdsClient, long customerId) {

		String budgetResourceName = addCampaignBudget(googleAdsClient, customerId);
		String estrategiaNome = createBiddingStrategy(googleAdsClient, customerId);
		List<CampaignOperation> operations = new ArrayList<>(1);

		
		//Location california = new Location();
	    //california.setId(21137L);
		
		AppCampaignSetting universalSetting = AppCampaignSetting.newBuilder()
				.setAppId(StringValue.of("com.labpixies.colordrips"))
				.setAppStore(AppCampaignAppStore.GOOGLE_APP_STORE)
				.setBiddingStrategyGoalType(AppCampaignBiddingStrategyGoalType.OPTIMIZE_INSTALLS_TARGET_INSTALL_COST)
				.build();

		GeoTargetTypeSetting geoSetting = GeoTargetTypeSetting.newBuilder()
				.setPositiveGeoTargetType(PositiveGeoTargetType.PRESENCE_OR_INTEREST)
				.build();
		
		TargetCpa cpa = TargetCpa.newBuilder()
				.setTargetCpaMicros(Int64Value.of(10_000_000L))
				.build();

		// Create the campaign.
		Campaign campaign = Campaign.newBuilder()
				.setName(StringValue.of("Teste de Cidade #" + System.currentTimeMillis()))
				.setStatus(CampaignStatus.PAUSED)
				.setAdvertisingChannelType(AdvertisingChannelType.MULTI_CHANNEL)
				.setAdvertisingChannelSubType(AdvertisingChannelSubType.APP_CAMPAIGN)
				.setStartDate(StringValue.of(new DateTime().plusDays(1).toString("yyyyMMdd")))
				.setEndDate(StringValue.of(new DateTime().plusYears(1).toString("yyyyMMdd")))
				.setAppCampaignSetting(universalSetting).setGeoTargetTypeSetting(geoSetting)
				.setTargetCpa(cpa)
				.setCampaignBudget(StringValue.of(budgetResourceName))
				.build();

		
		CampaignOperation op = CampaignOperation.newBuilder().setCreate(campaign).build();
		operations.add(op);

		try (CampaignServiceClient campaignServiceClient = googleAdsClient.getLatestVersion().createlo.createCampaignServiceClient()) {
			MutateCampaignsResponse response = campaignServiceClient.mutateCampaigns(Long.toString(customerId),	operations);
			System.out.printf("Added %d campaigns:%n", response.getResultsCount());
			for (MutateCampaignResult result : response.getResultsList()) {
				System.out.println(result.getResourceName());
				montandoCriterios(googleAdsClient,customerId, result.getResourceName());
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private static String createBiddingStrategy(GoogleAdsClient googleAdsClient, long customerId) {
		try (BiddingStrategyServiceClient biddingStrategyServiceClient = googleAdsClient.getLatestVersion()
				.createBiddingStrategyServiceClient()) {

			// TargetSpend targetSpend = TargetSpend.newBuilder()
			// .setCpcBidCeilingMicros(Int64Value.of(2_000_000L))
			// .setTargetSpendMicros(Int64Value.of(20_000_000L))
			// .build();

			TargetSpend targetSpend = TargetSpend.newBuilder()
					// .set.setCpcBidCeilingMicros(Int64Value.of(2_000_000L))
					// .setTargetSpendMicros(Int64Value.of(20_000_000L))
					.build();

			BiddingStrategy portfolioBiddingStrategy = BiddingStrategy.newBuilder()
					.setName(StringValue.of("Maximize Clicks #" + System.currentTimeMillis()))
					.setTargetSpend(targetSpend).build();

			BiddingStrategyOperation operation = BiddingStrategyOperation.newBuilder()
					.setCreate(portfolioBiddingStrategy).build();

			MutateBiddingStrategiesResponse response = biddingStrategyServiceClient
					.mutateBiddingStrategies(Long.toString(customerId), Lists.newArrayList(operation));

			MutateBiddingStrategyResult mutateBiddingStrategyResult = response.getResults(0);
			// Prints the resource name of the created object.
			System.out.printf("Created bidding strategy with resource name: '%s'.%n",
					mutateBiddingStrategyResult.getResourceName());

			return mutateBiddingStrategyResult.getResourceName();
		} catch (Exception e) {
			System.out.println("Erro Bidding: " + e.getMessage());
			return null;
		}
	}

	private static void montandoCriterios(GoogleAdsClient googleAdsClient, long customerId, String campaignResourceName) {
		// String campaignResourceName = CampaignName.format(Long.toString(customerId),
		// Long.toString(campaignId));

		List<CampaignCriterionOperation> operations = ImmutableList.of(
				//CampaignCriterionOperation.newBuilder().setCreate(buildLanguageIdCriterion(1014, campaignResourceName))
				//		.build(),
				CampaignCriterionOperation.newBuilder().setCreate(buildLocationIdCriterion(2076, campaignResourceName))
						.build());

		try (CampaignCriterionServiceClient campaignCriterionServiceClient = googleAdsClient.getLatestVersion()
				.createCampaignCriterionServiceClient()) {
			MutateCampaignCriteriaResponse response = campaignCriterionServiceClient
					.mutateCampaignCriteria(Long.toString(customerId), operations);
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
		criterionBuilder.getLocationBuilder()
				.setGeoTargetConstant(StringValue.of(GeoTargetConstantName.format(String.valueOf(linguaId))));
		return criterionBuilder.build();
	}

}
