package com.marclucchini.doctor.model;

import com.marclucchini.doctor.crawl.CrawlerService;
import com.marclucchini.doctor.es.EsIndexer;
import com.marclucchini.doctor.es.EsIndexerStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentService {

  private static final Log logger = LogFactory.getLog(DocumentService.class);
  private CrawlerService crawlerService;
  private DocumentRepository repository;
  private EsIndexer esIndexer;

  public DocumentService(CrawlerService crawlerService, DocumentRepository repository, EsIndexer esIndexer) {
    this.crawlerService = crawlerService;
    this.repository = repository;
    this.esIndexer = esIndexer;
  }

  public void init() {
    try {
      if (noDocumentsIndexed()) {
        loadOrCrawl();
        index();
      }
    } catch (IOException e) {
      logger.error("Unable to initialise the document repository or its index", e);
    }
  }

  protected void loadOrCrawl() throws IOException {
    repository.load();
    if (repository.isEmpty()) {
      crawlerService.start();
    }
  }

  protected void index() throws IOException {
    esIndexer.start();
  }

  protected boolean noDocumentsIndexed() throws IOException {
    EsIndexerStatus status = esIndexer.getStatus();
    return status.getDocuments() <= 0;
  }
}
