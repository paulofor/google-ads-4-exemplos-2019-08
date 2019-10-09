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

import com.beust.jcommander.Parameter;
import com.google.ads.googleads.examples.utils.ArgumentNames;
import com.google.ads.googleads.examples.utils.CodeSampleParams;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.errors.GoogleAdsException;
import com.google.ads.googleads.v2.errors.GoogleAdsError;
import com.google.ads.googleads.v2.services.GoogleAdsRow;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient.SearchPagedResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.ads.googleads.v2.services.SearchGoogleAdsRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Authenticator.RequestorType;

/** Gets all campaigns. To add campaigns, run AddCampaigns.java. */
public class ObtemResultadoCampanhaTst {

	private static final int PAGE_SIZE = 1_000;

	private static class GetCampaignsWithStatsParams extends CodeSampleParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, required = true)
		private Long customerId;
	}

	public static void main(String[] args) throws IOException {
		setProxy();
		GetCampaignsWithStatsParams params = new GetCampaignsWithStatsParams();
		if (!params.parseArguments(args)) {
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
			new ObtemResultadoCampanhaTst().runExample(googleAdsClient, params.customerId);
		} catch (GoogleAdsException gae) {
			System.err.printf("Request ID %s failed due to GoogleAdsException. Underlying errors:%n",
					gae.getRequestId());
			int i = 0;
			for (GoogleAdsError googleAdsError : gae.getGoogleAdsFailure().getErrorsList()) {
				System.err.printf("  Error %d: %s%n", i++, googleAdsError);
			}
		}
	}

	/**
	 * Runs the example.
	 *
	 * @param googleAdsClient
	 *            the Google Ads API client.
	 * @param customerId
	 *            the client customer ID.
	 * @throws GoogleAdsException
	 *             if an API request failed with one or more service errors.
	 */
	private void runExample(GoogleAdsClient googleAdsClient, long customerId) {
		try (GoogleAdsServiceClient googleAdsServiceClient = googleAdsClient.getLatestVersion()
				.createGoogleAdsServiceClient()) {

			String query = "Select  Impressions , Clicks, Cost, CampaignStatus, EndDate, Ctr, AverageCpc , Conversions, ConversionRate, CostPerConversion "
					+ "FROM CAMPAIGN_PERFORMANCE_REPORT " + "Where CampaignId = 6455404024";
			query = "SELECT campaign.id, campaign.name FROM campaign where campaign.id = 6455404024";
			query = "select ActiveViewCtr from  CAMPAIGN_PERFORMANCE_REPORT2";

			query = "SELECT campaign.status, campaign.status" + "FROM campaign where campaign.id = 6455404024";

			// funciona
			query = " SELECT campaign.name, campaign.status, segments.device, metrics.impressions,"
					+ " metrics.clicks, metrics.ctr, metrics.average_cpc, metrics.cost_micros "
					+ " FROM campaign where campaign.id = 6455404024";

			query = " SELECT campaign.name, campaign.status, metrics.impressions,"
					+ " metrics.clicks, metrics.ctr, metrics.average_cpc, metrics.cost_micros, "
					+ " metrics.conversions, metrics.conversions_from_interactions_rate, metrics.cost_per_conversion "
					+ " FROM campaign where campaign.id = 6455404024";

			// como obter os anuncios/grupos de uma campanha
			query = " SELECT campaign.name " + " FROM ad_group " + " Where campaign.id = 6455404024";

			// Pegar grupo
			String query1 = " SELECT " + " ad_group.name, " + " ad_group.id, " + " metrics.impressions, " + " metrics.clicks "
					+ " FROM ad_group " + " Where campaign.id = 6455404024";
			// Anuncio de grupo -- 77477067535

			// Pegar grupo --> totais do grupo.
			String queryGrupo = " SELECT " + " ad_group.name, " + " ad_group.id, " + " metrics.impressions, " + " metrics.clicks "
					+ " FROM ad_group " + " Where ad_group.id = 77477067535";

			query = "SELECT ad_group.id, " + "ad_group_ad.ad.id, " + "ad_group_ad.ad.app_add_info.headline_part1, "
			// + "ad_group_ad.ad.expanded_text_ad.headline_part2, "
					+ "ad_group_ad.status " + "FROM ad_group_ad " + "WHERE ad_group.id = 77477067535 ";

			// 378561703642
			String query2 = " SELECT " + " metrics.impressions, " + " metrics.clicks " + " FROM ads "
					+ " Where ads.id = 378561703642";
			System.out.println(queryGrupo);

			SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
					.setCustomerId(Long.toString(customerId)).setPageSize(PAGE_SIZE).setQuery(queryGrupo).build();

			SearchPagedResponse searchPagedResponse = googleAdsServiceClient.search(request);

			for (GoogleAdsRow googleAdsRow : searchPagedResponse.iterateAll()) {

				System.out.println(googleAdsRow.toString());
			}
		}
	}

	protected static HttpTransport setProxy() {
		System.setProperty("https.proxyHost", "10.21.7.10");
		System.setProperty("https.proxyPort", "82");
		System.setProperty("https.proxyUser", "tr626987");
		System.setProperty("https.proxyPassword", "Lafiti23");

		System.setProperty("http.proxyHost", "10.21.7.10");
		System.setProperty("http.proxyPort", "82");
		System.setProperty("http.proxyUser", "tr626987");
		System.setProperty("http.proxyPassword", "Lafiti23");

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.21.7.10", 82));
		HttpTransport httpTransport = new NetHttpTransport.Builder().setProxy(proxy).build();
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// check that the pasword-requesting site is the proxy server
				if (this.getRequestingHost().contains("10.21.7.10") && this.getRequestingPort() == 82
						&& this.getRequestorType().equals(RequestorType.PROXY)) {
					return new PasswordAuthentication("tr626987", "Lafiti23".toCharArray());
				}
				return super.getPasswordAuthentication();
			}
		});
		return httpTransport;
	}

}
