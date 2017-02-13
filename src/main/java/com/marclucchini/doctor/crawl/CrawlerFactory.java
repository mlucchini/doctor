package com.marclucchini.doctor.crawl;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import com.marclucchini.doctor.model.DocumentRepository;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.springframework.stereotype.Component;

@Component
public class CrawlerFactory implements CrawlController.WebCrawlerFactory<Crawler> {

  private ApplicationConfiguration configuration;
  private DocumentRepository repository;

  public CrawlerFactory(ApplicationConfiguration configuration, DocumentRepository repository) {
    this.configuration = configuration;
    this.repository = repository;
  }

  @Override
  public Crawler newInstance() {
    return new Crawler(configuration, repository);
  }
}
