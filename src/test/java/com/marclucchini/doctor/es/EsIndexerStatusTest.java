package com.marclucchini.doctor.es;

import io.searchbox.client.JestResult;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;

public class EsIndexerStatusTest {

  @Test
  public void testShouldNotThrowWhenJsonPropertiesAreMissing() {
    JestResult health = mock(JestResult.class);
    JestResult stats = mock(JestResult.class);

    new EsIndexerStatus(health, stats);
  }

  @Test
    public void testDefaultValuesWhenJsonPropertiesAreMissing() {
    JestResult health = mock(JestResult.class);
    JestResult stats = mock(JestResult.class);

    EsIndexerStatus status = new EsIndexerStatus(health, stats);

    Assert.assertThat(status.getStatus(), equalTo("unknown"));
    Assert.assertThat(status.getNodes(), equalTo(-1));
    Assert.assertThat(status.getDocuments(), equalTo(-1));
  }
}
