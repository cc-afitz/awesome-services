package de.codecentric.awesome.recommendation.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import de.codecentric.awesome.recommendation.api.Recommendation;
import de.codecentric.awesome.recommendation.client.Products;
import de.codecentric.awesome.recommendation.core.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;


@Path("/recommendation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecommendationResource {

	private final String defaultProduct;
	private final String defaultUser;
	private final HttpClient httpAnalysisService; 
	
	public RecommendationResource(String defaultProduct, String defaultUser, HttpClient httpAnalysisService) {
		this.defaultProduct = defaultProduct;
		this.defaultUser = defaultUser;
		this.httpAnalysisService = httpAnalysisService;
	}

	@GET
	@Timed
	public Recommendation getRecommendation(@QueryParam("user") Optional<String> user, @QueryParam("product") Optional<String> product) {
		
		String recommedUser = (user.isPresent() ? user.get() : defaultUser);

//		local access
//		Product recommedProduct = RecommendationLookup.getInstance().getRecommendation(
//				(product.isPresent() ? product.get() : defaultProduct)
//				);

		URI uri = null;
		try {
			uri = new URIBuilder()
			        .setScheme("http")
			        .setHost("localhost")
			        .setPort(8102)
			        .setPath("/recommendation-db")
			        .setParameter("product", (product.isPresent() ? product.get().toString() : defaultProduct))
			        .build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpGet get = new HttpGet(uri);
		get.addHeader("accept", "application/json");

		// Create a custom response handler
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(
                    final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };

		String responseBody = null;
		try {
			responseBody = httpAnalysisService.execute(get, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
			System.out.println("----------------------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Products recommendProducts = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
//			Products products = objectMapper.readValue(responseBody, Products.class);
			recommendProducts = objectMapper.readValue(responseBody , Products.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Recommendation(recommedUser, recommendProducts.getProducts());
	}

}
