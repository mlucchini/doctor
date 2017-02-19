package com.marclucchini.doctor.crawl;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import com.marclucchini.doctor.model.DocumentRepository;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CrawlerService {

  private static final Log logger = LogFactory.getLog(CrawlerService.class);
  private CrawlController engine;
  private AtomicBoolean inProgress;
  private CrawlerFactory crawlerFactory;
  private ApplicationConfiguration configuration;
  private DocumentRepository repository;

  public CrawlerService(CrawlerFactory crawlerFactory, ApplicationConfiguration configuration,
                        DocumentRepository repository) {
    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(".");
    config.setUserAgentString("ml-doctor");
    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtServer robotstxtServer = new RobotstxtServer(new RobotstxtConfig(), pageFetcher);
    try {
      this.engine = new CrawlController(config, pageFetcher, robotstxtServer);
    } catch (Exception e) {
      throw new CrawlerException("Could not initialise crawl", e);
    }
    this.inProgress = new AtomicBoolean();
    this.crawlerFactory = crawlerFactory;
    this.configuration = configuration;
    this.repository = repository;
  }

  public void start() throws IOException {
    if (inProgress.compareAndSet(false, true)) {
      try {
        String seed = configuration.getSeed();
        logger.info(String.format("Starting a crawl from seed \"%s\"", seed));
        engine.addSeed(seed);
        engine.start(crawlerFactory, configuration.getParallelism());
        repository.save();
      } finally {
        inProgress.set(false);
      }
    }
  }

  public boolean isInProgress() {
    return inProgress.get();
  }
}
