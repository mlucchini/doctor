package com.marclucchini.doctor.app;

import com.marclucchini.doctor.crawl.CrawlerStatus;
import com.marclucchini.doctor.es.EsIndexerStatus;

public class ApplicationStatus {

  private CrawlerStatus crawlerStatus;
  private EsIndexerStatus indexerStatus;

  public ApplicationStatus(CrawlerStatus crawlerStatus, EsIndexerStatus indexerStatus) {
    this.crawlerStatus = crawlerStatus;
    this.indexerStatus = indexerStatus;
  }

  public CrawlerStatus getCrawlerStatus() {
    return crawlerStatus;
  }

  public EsIndexerStatus getIndexerStatus() {
    return indexerStatus;
  }
}
