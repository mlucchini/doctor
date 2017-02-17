package com.marclucchini.doctor.model;

import com.marclucchini.doctor.crawl.CrawlerService;
import com.marclucchini.doctor.es.EsIndexer;
import com.marclucchini.doctor.es.EsIndexerStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class DocumentServiceTest {

  CrawlerService crawlerService;
  DocumentRepository repository;
  DocumentService documentService;
  EsIndexerStatus esIndexerStatus;
  EsIndexer esIndexer;

  @Before
  public void setUp() {
    crawlerService = mock(CrawlerService.class);
    repository = mock(DocumentRepository.class);
    esIndexerStatus = mock(EsIndexerStatus.class);
    esIndexer = mock(EsIndexer.class);
    documentService = new DocumentService(crawlerService, repository, esIndexer);
  }

  @Test
  public void testInitDocumentsIndexedNoCrawlNoIndexing() throws IOException {
    when(esIndexer.getStatus()).thenReturn(esIndexerStatus);
    when(esIndexerStatus.getDocuments()).thenReturn(100);

    documentService.init();

    verify(repository, never()).load();
    verify(crawlerService, never()).start();
  }

  @Test
  public void testInitNoDocumentsIndexedLoadAndIndex() throws IOException {
    when(esIndexer.getStatus()).thenReturn(esIndexerStatus);
    when(esIndexerStatus.getDocuments()).thenReturn(0);

    documentService.init();

    verify(repository, times(1)).load();
    verify(esIndexer, times(1)).start();
  }

  @Test
  public void testInitNoDocumentsIndexedNoLoadCrawlAndIndex() throws IOException {
    when(esIndexer.getStatus()).thenReturn(esIndexerStatus);
    when(esIndexerStatus.getDocuments()).thenReturn(0);
    when(repository.isEmpty()).thenReturn(true);

    documentService.init();

    verify(repository, times(1)).load();
    verify(crawlerService, times(1)).start();
    verify(esIndexer, times(1)).start();
  }

  @Test
  public void testShouldNotPropagateExceptions() throws IOException {
    when(esIndexer.getStatus()).thenReturn(esIndexerStatus);
    doThrow(new IOException()).when(repository).load();

    documentService.init();
  }
}
