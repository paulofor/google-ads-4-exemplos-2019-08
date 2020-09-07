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
import com.google.api.ads.adwords.axis.v201809.cm.Location;
import com.google.api.ads.adwords.axis.v201809.cm.LocationCriterion;
import com.google.api.ads.adwords.axis.v201809.cm.Predicate;
import com.google.api.ads.adwords.axis.v201809.cm.PredicateOperator;
import com.google.api.ads.adwords.axis.v201809.cm.Selector;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.protobuf.BoolValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;

/** Adds new campaigns to a client account. */
public class PesquisaLugarServico {

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
			new PesquisaLugarServico().runExample(googleAdsClient, params.customerId);
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

	// ** Versao Nova

	public static void runExample(GoogleAdsClient googleAdsClient, long customerId) {
		// TODO Auto-generated method stub

		googleAdsClient.getLatestVersion().
		String[] locationNames = new String[] { "Quebec" };

		Selector selector = new Selector();
		selector.setFields(
				new String[] { "Id", "LocationName", "CanonicalName", "DisplayType", "ParentLocations", "Reach" });

		selector.setPredicates(new Predicate[] {
				// Location names must match exactly, only EQUALS and IN are
				// supported.
				new Predicate("LocationName", PredicateOperator.IN, locationNames),
				// Set the locale of the returned location names.
				new Predicate("Locale", PredicateOperator.EQUALS, new String[] { "en" }) });

		// Make the get request.
		LocationCriterion[] locationCriteria = locationCriterionService.get(selector);

		// Display the resulting location criteria.
		for (LocationCriterion locationCriterion : locationCriteria) {
			String parentString = getParentLocationString(locationCriterion.getLocation().getParentLocations());
			System.out.printf(
					"The search term '%s' returned the location '%s (%d)' of type '%s' "
							+ "with parent locations '%s' and reach '%d'.\n",
					locationCriterion.getSearchTerm(), locationCriterion.getLocation().getLocationName(),
					locationCriterion.getLocation().getId(), locationCriterion.getLocation().getDisplayType(),
					parentString, locationCriterion.getReach());
		}

	}

	/**
	 * Helper function to format a string for parent locations.
	 *
	 * @param parents List of parent locations.
	 * @return Formatted string representing parent locations.
	 */
	public static String getParentLocationString(Location[] parents) {
		StringBuilder sb = new StringBuilder();
		if (parents != null) {
			for (Location parent : parents) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append(String.format("%s (%s)", parent.getLocationName(), parent.getDisplayType()));
			}
		} else {
			sb.append("N/A");
		}
		return sb.toString();
	}
}
