package de.codecentric.awesome.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.codecentric.awesome.recommendation.client.AnalysisService.AnalysisServiceFactory;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class RecommendationConfiguration extends Configuration{

    @NotEmpty
    private String defaultProduct;
    
    @NotEmpty
    private String defaultUser;

	@Valid
    @NotNull
    private AnalysisServiceFactory analysisService = new AnalysisServiceFactory();

	
	@JsonProperty("analysisService")
    public AnalysisServiceFactory getAnalysisServiceFactory(){
        return analysisService;
    }

    @JsonProperty("analysisService")
    public void setAnalysisService(AnalysisServiceFactory factory){
        this.analysisService = factory;
    }

    @JsonProperty
    public String getDefaultUser() {
		return defaultUser;
	}

    @JsonProperty
	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}

	@JsonProperty
    public String getDefaultProduct() {
        return defaultProduct;
    }

    @JsonProperty
    public void setDefaultProduct(String Product) {
        this.defaultProduct= Product;
    }
}
