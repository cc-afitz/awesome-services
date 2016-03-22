package de.codecentric.awesome.recommendation;

import de.codecentric.awesome.recommendation.client.AnalysisService.AnalysisServiceClient;
import de.codecentric.awesome.recommendation.health.UpStreamHealthCheck;

import de.codecentric.awesome.recommendation.resources.RecommendationResource;
import io.dropwizard.Application;
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

		// create & register client(s):
		//*** AnalysisServce ***
		//*** factory will automatically tie our AnalysisServiceClient connection to the lifecycle of our recommendation’s Environment.

		AnalysisServiceClient analysisService = recommendationConfiguration.getAnalysisServiceFactory().build(recommendationEnvironment);

		final RecommendationResource recommendationResource = new RecommendationResource(
				recommendationConfiguration.getDefaultProduct(),
				recommendationConfiguration.getDefaultUser(),
				analysisService
				);

		//register resources
		recommendationEnvironment.jersey().register(recommendationResource);

		//create & register health-checks
		recommendationEnvironment.healthChecks().register("upstream", new UpStreamHealthCheck(analysisService));


	}

}
