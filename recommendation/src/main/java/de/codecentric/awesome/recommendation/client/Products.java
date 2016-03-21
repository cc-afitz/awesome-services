package de.codecentric.awesome.recommendation.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Created by afitz on 18.03.16.
 */
public class Products {

    @JsonProperty("products")
        private List<String> products;


        @JsonProperty
        public List<String> getProducts() {
            return products;
        }

        @JsonProperty
        public void setProducts(List<String> products) {
            this.products = products;
        }

        public Products() {
            // Jackson deserialization
        }

        public Products(List<String> products){
            this.products = products;
        }

}
