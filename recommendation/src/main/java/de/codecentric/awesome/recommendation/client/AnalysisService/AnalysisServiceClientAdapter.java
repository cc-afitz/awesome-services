package de.codecentric.awesome.recommendation.client.AnalysisService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by afitz on 22.03.16.
 */
public class AnalysisServiceClientAdapter implements AnalysisServiceClient {

    private String host = null;
    private int port = 0;
    HttpClient httpAnalysisService;

    public AnalysisServiceClientAdapter(String host, int port, HttpClient httpClient) {
        this.port = port;
        this.host = host;
        this.httpAnalysisService = httpClient;
    }

    @Override
    public Products executeGetProducts(String product) {
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8102)
                    .setPath("/recommendation-db")
                    .setParameter("product", product)
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
            recommendProducts = objectMapper.readValue(responseBody, Products.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recommendProducts;
    }
}
