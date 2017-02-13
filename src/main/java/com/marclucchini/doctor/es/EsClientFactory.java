package com.marclucchini.doctor.es;

import com.marclucchini.doctor.app.ApplicationEnvironment;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.stereotype.Component;

@Component
public class EsClientFactory {

  private ApplicationEnvironment environment;

  public EsClientFactory(ApplicationEnvironment environment) {
    this.environment = environment;
  }

  public JestClient newInstance() {
    String esUri = environment.getEnv("BONSAI_URL");
    if (esUri == null || esUri.isEmpty()) {
      throw new IllegalStateException("You must set a BONSAI_URL environment variable pointing to an Elasticsearch server");
    }
    JestClientFactory factory = new JestClientFactory();
    factory.setHttpClientConfig(new HttpClientConfig.Builder(esUri).multiThreaded(true).build());
    return factory.getObject();
  }
}
