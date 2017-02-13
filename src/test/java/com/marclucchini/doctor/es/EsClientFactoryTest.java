package com.marclucchini.doctor.es;

import com.marclucchini.doctor.app.ApplicationEnvironment;
import io.searchbox.client.JestClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EsClientFactoryTest {

  ApplicationEnvironment environment;
  EsClientFactory factory;

  @Before
  public void setup() {
    environment = mock(ApplicationEnvironment.class);
    factory = new EsClientFactory(environment);
  }

  @Test(expected = RuntimeException.class)
  public void testThrowIfEsInstanceUrlIsMissing() {
    when(environment.getEnv("BONSAI_URL")).thenReturn(null);

    factory.newInstance();
  }

  @Test
  public void testNewInstance() {
    when(environment.getEnv("BONSAI_URL")).thenReturn("http://localhost:9200");

    JestClient client = factory.newInstance();

    Assert.assertThat(client, any(JestClient.class));
  }
}
