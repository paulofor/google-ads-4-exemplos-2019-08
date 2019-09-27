package br.com.lojadigicom.desen;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.beust.jcommander.Parameter;
import com.google.ads.googleads.examples.utils.ArgumentNames;
import com.google.ads.googleads.examples.utils.CodeSampleParams;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.errors.GoogleAdsError;
import com.google.ads.googleads.v2.errors.GoogleAdsException;
import com.google.ads.googleads.v2.services.GoogleAdsRow;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v2.services.SearchGoogleAdsRequest;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient.SearchPagedResponse;





public class EsqueletoAplicacaoAds {

	private static final int PAGE_SIZE = 1_000;

	private static class GetCampaignsWithStatsParams extends CodeSampleParams {

		@Parameter(names = ArgumentNames.CUSTOMER_ID, required = true)
		private Long customerId;
	}

	public static void main(String[] args) throws IOException {
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
			new EsqueletoAplicacaoAds().runExample(googleAdsClient, params.customerId);
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
	 * @param googleAdsClient the Google Ads API client.
	 * @param customerId      the client customer ID.
	 * @throws GoogleAdsException if an API request failed with one or more service
	 *                            errors.
	 */
	private void runExample(GoogleAdsClient googleAdsClient, long customerId) {
		try (GoogleAdsServiceClient googleAdsServiceClient = googleAdsClient.getLatestVersion()
				.createGoogleAdsServiceClient()) {

		   
		}
	}
}
