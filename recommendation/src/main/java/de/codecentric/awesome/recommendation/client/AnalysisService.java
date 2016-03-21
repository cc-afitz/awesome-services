package de.codecentric.awesome.recommendation.client;

import org.apache.http.client.HttpClient;

// may be deprecated
public class AnalysisService {

//	private String url;
//	private int port;
//	private String serviceName;
	
	private HttpClient httpAnalysisService;
	
//	public AnalysisService(String url, int port, String serviceName) {
//		this.url = url;
//		this.port = port;
//		this.serviceName = serviceName;
//	}
	
	
	public AnalysisService(HttpClient httpAnalysisService) {
		this.httpAnalysisService = httpAnalysisService;
	}

	
	public void getProduct (){
		//tbd!
	}
}
