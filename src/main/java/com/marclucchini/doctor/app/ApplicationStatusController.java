package com.marclucchini.doctor.app;

import com.marclucchini.doctor.crawl.CrawlerService;
import com.marclucchini.doctor.crawl.CrawlerStatus;
import com.marclucchini.doctor.es.EsIndexer;
import com.marclucchini.doctor.es.EsIndexerStatus;
import com.marclucchini.doctor.model.DocumentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ApplicationStatusController {

  private CrawlerService crawlerService;
  private DocumentRepository repository;
  private EsIndexer esIndexer;

  public ApplicationStatusController(CrawlerService crawlerService, DocumentRepository repository,
                                     EsIndexer esIndexer) {
    this.crawlerService = crawlerService;
    this.repository = repository;
    this.esIndexer = esIndexer;
  }

  @GetMapping("/status")
  @ResponseBody
  public ApplicationStatus getStatus() throws IOException {
    EsIndexerStatus indexerStatus = esIndexer.getStatus();
    CrawlerStatus crawlerStatus = new CrawlerStatus(crawlerService.isInProgress(), repository.getAll().size());
    return new ApplicationStatus(crawlerStatus, indexerStatus);
  }
}
