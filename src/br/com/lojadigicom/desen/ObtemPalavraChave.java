package br.com.lojadigicom.desen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.Parameter;
import com.google.ads.googleads.examples.utils.ArgumentNames;
import com.google.ads.googleads.examples.utils.CodeSampleParams;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.errors.GoogleAdsError;
import com.google.ads.googleads.v2.errors.GoogleAdsException;
import com.google.ads.googleads.v2.services.GoogleAdsServiceClient;
import com.google.api.ads.adwords.axis.v201806.o.SearchParameter;
import com.google.api.ads.adwords.axis.v201806.o.TargetingIdeaServiceInterface;
import com.google.api.ads.adwords.axis.v201809.cm.Money;
import com.google.api.ads.adwords.axis.v201809.cm.Paging;
import com.google.api.ads.adwords.axis.v201809.o.Attribute;
import com.google.api.ads.adwords.axis.v201809.o.AttributeType;
import com.google.api.ads.adwords.axis.v201809.o.DoubleAttribute;
import com.google.api.ads.adwords.axis.v201809.o.IdeaType;
import com.google.api.ads.adwords.axis.v201809.o.IntegerSetAttribute;
import com.google.api.ads.adwords.axis.v201809.o.LongAttribute;
import com.google.api.ads.adwords.axis.v201809.o.MoneyAttribute;
import com.google.api.ads.adwords.axis.v201809.o.RelatedToQuerySearchParameter;
import com.google.api.ads.adwords.axis.v201809.o.RequestType;
import com.google.api.ads.adwords.axis.v201809.o.StringAttribute;
import com.google.api.ads.adwords.axis.v201809.o.TargetingIdea;
import com.google.api.ads.adwords.axis.v201809.o.TargetingIdeaPage;
import com.google.api.ads.adwords.axis.v201809.o.TargetingIdeaSelector;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Maps;
import com.google.common.primitives.Ints;





public class ObtemPalavraChave {

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
			new ObtemPalavraChave().runExample(googleAdsClient, params.customerId);
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
			googleAdsClient.
			
		    TargetingIdeaServiceInterface targetingIdeaService =
		            adWordsServices.get(session, TargetingIdeaServiceInterface.class);
			
		    TargetingIdeaSelector selector = new TargetingIdeaSelector();
			selector.setRequestType(RequestType.IDEAS);
			selector.setIdeaType(IdeaType.KEYWORD);
			
			selector.setRequestedAttributeTypes(new AttributeType[] {
				    AttributeType.KEYWORD_TEXT,
				    AttributeType.SEARCH_VOLUME,
				    AttributeType.AVERAGE_CPC,
				    AttributeType.COMPETITION,
				    AttributeType.CATEGORY_PRODUCTS_AND_SERVICES});
			// Set selector paging (required for targeting idea service).
			Paging paging = new Paging();
			paging.setStartIndex(0);
			paging.setNumberResults(10);
			selector.setPaging(paging);
			
			List<SearchParameter> searchParameters = new ArrayList<>();
			// Create related to query search parameter.
			RelatedToQuerySearchParameter relatedToQuerySearchParameter =
			    new RelatedToQuerySearchParameter();
			relatedToQuerySearchParameter.setQueries(new String[] {"bakery", "pastries", "birthday cake"});
			searchParameters.add(relatedToQuerySearchParameter);
			
			
			// Get keyword ideas.
			TargetingIdeaPage page = targetingIdeaService.get(selector);
			
			// Display keyword ideas.
			for (TargetingIdea targetingIdea : page.getEntries()) {
			  Map<AttributeType, Attribute> data = Maps.toMap(targetingIdea.getData());
			  StringAttribute keyword = (StringAttribute) data.get(AttributeType.KEYWORD_TEXT);

			  IntegerSetAttribute categories =
			      (IntegerSetAttribute) data.get(AttributeType.CATEGORY_PRODUCTS_AND_SERVICES);
			  String categoriesString = "(none)";
			  if (categories != null && categories.getValue() != null) {
			    categoriesString = Joiner.on(", ").join(Ints.asList(categories.getValue()));
			  }
			  Long averageMonthlySearches =
			      ((LongAttribute) data.get(AttributeType.SEARCH_VOLUME))
			          .getValue();
			  Money averageCpc =
			      ((MoneyAttribute) data.get(AttributeType.AVERAGE_CPC)).getValue();
			  Double competition =
			      ((DoubleAttribute) data.get(AttributeType.COMPETITION)).getValue();
			  System.out.printf("Keyword with text '%s', average monthly search volume %d, "
			      + "average CPC %d, and competition %.2f "
			      + "was found with categories: %s%n", keyword.getValue(), averageMonthlySearches,
			      averageCpc.getMicroAmount(), competition,
			      categoriesString);
			}
		}
	}
}
