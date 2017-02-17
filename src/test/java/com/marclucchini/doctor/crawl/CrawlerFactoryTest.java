package com.marclucchini.doctor.crawl;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import com.marclucchini.doctor.model.DocumentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.mock;

public class CrawlerFactoryTest {

  CrawlerFactory factory;

  @Before
  public void setUp() {
    ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    DocumentRepository repository = mock(DocumentRepository.class);
    factory = new CrawlerFactory(configuration, repository);
  }

  @Test
  public void testNewInstance() {
    Assert.assertThat(factory.newInstance(), any(Crawler.class));
  }
}
