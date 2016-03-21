package de.codecentric.awesome.recommendation;

import io.dropwizard.lifecycle.Managed;
import org.apache.http.client.HttpClient;

import de.codecentric.awesome.recommendation.client.AnalysisService;
import de.codecentric.awesome.recommendation.core.Product;
import de.codecentric.awesome.recommendation.core.User;
import de.codecentric.awesome.recommendation.resources.RecommendationResource;
import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecommendationService extends Application<RecommendationConfiguration>{

	private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

	public static void main(String[] args) throws Exception {
		new RecommendationService().run(args);
	}

	@Override
	public String getName() {
		return "Awesome Recommendation Service";
	}

	@Override
	public void initialize(Bootstrap<RecommendationConfiguration> bootstrap) {
		// nothing to do yet
	}

	@Override
	public void run(RecommendationConfiguration recommendationConfiguration,
					Environment recommendationEnvironment) {

		// create & register client(s): AnalysisServce
		final HttpClient httpAnalysisService = new HttpClientBuilder(recommendationEnvironment).
				using(recommendationConfiguration.getHttpClientConfiguration())
                .build("analysis-service");

		//recommendationEnvironment.lifecycle().manage((Managed) httpAnalysisService);
		
		final RecommendationResource recommendationResource = new RecommendationResource(
				recommendationConfiguration.getDefaultProduct(),
				recommendationConfiguration.getDefaultUser(),
				httpAnalysisService
				);
		

		//register resources
		recommendationEnvironment.jersey().register(new AnalysisService(httpAnalysisService));
		recommendationEnvironment.jersey().register(recommendationResource);

		//		tbd: turn on!
//		final TemplateHealthCheck healthCheck =
//				new TemplateHealthCheck(configuration.getTemplate());
//		environment.healthChecks().register("template", healthCheck);


	}

}
