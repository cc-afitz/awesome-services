package de.codecentric.awesome.recommendation.client;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

// may be deprecated
public class AnalysisServiceFactory {
	@NotEmpty
    private String url;

	@Min(1)
    @Max(65535)
    private int port;

    @NotEmpty
    private String serviceName;
	
    @JsonProperty
    public String getUrl() {
		return url;
	}

    @JsonProperty
	public void setUrl(String url) {
		this.url = url;
	}

    @JsonProperty
	public int getPort() {
		return port;
	}

    @JsonProperty
	public void setPort(int port) {
		this.port = port;
	}

    @JsonProperty
	public String getServiceName() {
		return serviceName;
	}

    @JsonProperty
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

//    public AnalysisService build(Environment environment) {
//    	AnalysisService client = new AnalysisService(getUrl(), getPort(), getServiceName());
//       
//    	environment.lifecycle().manage(new Managed() {
////            @Override
//            public void start() {
////            	tbd!
//            }
//
////            @Override
//            public void stop() {
////               tbd!
//            }
//        });
//        return client;
//    }
    
}
