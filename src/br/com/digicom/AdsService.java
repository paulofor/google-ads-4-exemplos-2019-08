package br.com.digicom;

import static com.google.api.ads.common.lib.utils.Builder.DEFAULT_CONFIGURATION_FILENAME;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.rmi.RemoteException;

import com.beust.jcommander.Parameter;
import com.google.ads.googleads.examples.utils.ArgumentNames;
import com.google.ads.googleads.examples.utils.CodeSampleParams;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v2.errors.GoogleAdsError;
import com.google.ads.googleads.v2.errors.GoogleAdsException;
import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201809.cm.ApiError;
import com.google.api.ads.adwords.axis.v201809.cm.ApiException;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.conf.ConfigurationLoadException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public abstract class AdsService {

	private static class AddCampaignsParams extends CodeSampleParams {
		@Parameter(names = ArgumentNames.CUSTOMER_ID, required = true)
		private Long customerId;
	}

	
	protected HttpTransport setProxy() {
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

	
	protected void executa() {
		AddCampaignsParams params = new AddCampaignsParams();
		params.customerId = Long.parseLong("5328916093");
		setProxy();
		
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
			runExample(googleAdsClient, params.customerId);
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

	protected abstract void runExample(GoogleAdsClient googleAdsClient, long customerId);

}
