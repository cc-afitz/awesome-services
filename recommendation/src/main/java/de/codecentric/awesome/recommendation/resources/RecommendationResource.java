package de.codecentric.awesome.recommendation.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import de.codecentric.awesome.recommendation.api.Recommendation;
import de.codecentric.awesome.recommendation.client.AnalysisService.AnalysisServiceClient;
import de.codecentric.awesome.recommendation.client.AnalysisService.Products;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/recommendation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecommendationResource {

	private final String defaultProduct;
	private final String defaultUser;
	private final AnalysisServiceClient analysisService;

	public RecommendationResource(String defaultProduct, String defaultUser, AnalysisServiceClient analysisService) {
		this.defaultProduct = defaultProduct;
		this.defaultUser = defaultUser;
		this.analysisService = analysisService;
	}

	@GET
	@Timed
	public Recommendation getRecommendation(@QueryParam("user") Optional<String> user, @QueryParam("product") Optional<String> product) {
		
		String recommedUser = (user.isPresent() ? user.get() : defaultUser);

//		local access
//		Product recommedProduct = RecommendationLookup.getInstance().getRecommendation(
//				(product.isPresent() ? product.get() : defaultProduct)
//				);

		Products recommendProducts = this.analysisService.executeGetProducts((product.isPresent() ? product.get() : this.defaultProduct));

		return new Recommendation(recommedUser, recommendProducts.getProducts());
	}

}
