package com.marclucchini.doctor.crawl;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import com.marclucchini.doctor.model.DocumentRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CrawlerServiceTest {

  CrawlerFactory crawlerFactory;
  ApplicationConfiguration configuration;
  DocumentRepository repository;
  CrawlerService crawlerService;

  @Before
  public void setUp() {
    crawlerFactory = mock(CrawlerFactory.class);
    configuration = mock(ApplicationConfiguration.class);
    repository = mock(DocumentRepository.class);
  }

  @Test
  public void testConfigureCrawler() {
    when(configuration.getSeed()).thenReturn("http://example.com");
    when(configuration.getParallelism()).thenReturn(42);

    crawlerService = new CrawlerService(crawlerFactory, configuration, repository);
  }
}
