package de.codecentric.awesome.recommendation.health;

import com.codahale.metrics.health.HealthCheck;
import de.codecentric.awesome.recommendation.client.AnalysisService.AnalysisServiceClient;
import org.apache.http.client.HttpClient;

/**
 * Created by afitz on 21.03.16.
 */
public class UpStreamHealthCheck extends HealthCheck {

    private AnalysisServiceClient analysisService;

    public UpStreamHealthCheck(AnalysisServiceClient analysisService) {
        this.analysisService = analysisService;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
