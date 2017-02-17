package com.marclucchini.doctor.app;

import com.marclucchini.doctor.crawl.CrawlerService;
import com.marclucchini.doctor.es.EsIndexer;
import com.marclucchini.doctor.es.EsIndexerStatus;
import com.marclucchini.doctor.model.Document;
import com.marclucchini.doctor.model.DocumentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationStatusControllerTest {

  CrawlerService crawlerService;
  DocumentRepository repository;
  EsIndexer esIndexer;
  EsIndexerStatus esIndexerStatus;
  ApplicationStatusController controller;

  @Before
  public void setUp() {
    crawlerService = mock(CrawlerService.class);
    repository = mock(DocumentRepository.class);
    esIndexer = mock(EsIndexer.class);
    esIndexerStatus = mock(EsIndexerStatus.class);
    controller = new ApplicationStatusController(crawlerService, repository, esIndexer);
  }

  @Test
  public void testGetStatus() throws IOException {
    Set<Document> documents = new HashSet<>();
    documents.add(new Document("url", "title", "text"));
    when(repository.getAll()).thenReturn(documents);
    when(esIndexerStatus.getStatus()).thenReturn("unknown");
    when(esIndexerStatus.getDocuments()).thenReturn(-1);
    when(esIndexerStatus.getNodes()).thenReturn(-1);
    when(esIndexer.getStatus()).thenReturn(esIndexerStatus);

    ApplicationStatus status = controller.getStatus();

    Assert.assertThat(status.getCrawlerStatus().isInProgress(), is(false));
    Assert.assertThat(status.getCrawlerStatus().getDocuments(), is(1));
    Assert.assertThat(status.getIndexerStatus().getStatus(), is("unknown"));
    Assert.assertThat(status.getIndexerStatus().getDocuments(), is(-1));
    Assert.assertThat(status.getIndexerStatus().getNodes(), is(-1));
  }
}
