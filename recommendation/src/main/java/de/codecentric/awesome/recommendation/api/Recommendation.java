package de.codecentric.awesome.recommendation.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.codecentric.awesome.recommendation.core.Product;
import de.codecentric.awesome.recommendation.core.User;

import java.util.List;

public class Recommendation {

	private String user;
	private List<String> products;

	@JsonProperty
	public String getUser() {
		return user;
	}

	@JsonProperty
	public void setUser(String user) {
		this.user = user;
	}	
	
	@JsonProperty
	public List<String> getProducts() {
		return products;
	}

	@JsonProperty
	public void setProduct(List<String> products) {
		this.products = products;
	}

	public Recommendation(){
		 // Jackson deserialization
	}

	public Recommendation(String user, List<String> products){
		 this.user = user;
		 this.products = products;
	}

}
