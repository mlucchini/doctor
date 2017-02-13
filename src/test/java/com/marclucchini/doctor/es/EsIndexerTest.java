package com.marclucchini.doctor.es;

import com.marclucchini.doctor.model.Document;
import com.marclucchini.doctor.model.DocumentRepository;
import io.searchbox.client.JestClient;
import io.searchbox.cluster.Health;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.Stats;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class EsIndexerTest {

  JestClient client;
  EsClientFactory factory;
  DocumentRepository repository;
  EsIndexer esIndexer;
  DocumentResult resultSuccessful;
  DocumentResult resultFailure;

  @Before
  public void setUp() throws Exception {
    client = mock(JestClient.class);
    factory = mock(EsClientFactory.class);
    repository = mock(DocumentRepository.class);
    given(factory.newInstance()).willReturn(client);
    resultSuccessful = mock(DocumentResult.class);
    resultFailure = mock(DocumentResult.class);

    esIndexer = new EsIndexer(factory, repository);

    when(client.execute(isA(Health.class))).thenReturn(mock(DocumentResult.class));
    when(client.execute(isA(Stats.class))).thenReturn(mock(DocumentResult.class));
    when(client.execute(isA(Index.class))).thenReturn(mock(DocumentResult.class));
    when(resultSuccessful.isSucceeded()).thenReturn(true);
    when(resultFailure.isSucceeded()).thenReturn(false);
  }

  @Test
  public void testStartCreateIndexIfItDoesntExist() throws IOException {
    Set<Document> documents = new HashSet<>();
    documents.add(new Document("url1", "title1", "text1"));
    when(repository.getAll()).thenReturn(documents);
    when(client.execute(isA(IndicesExists.class))).thenReturn(resultFailure);
    when(client.execute(isA(CreateIndex.class))).thenReturn(resultSuccessful);

    esIndexer.start();

    verify(client, times(1)).execute(isA(CreateIndex.class));
  }

  @Test
  public void testStartDoesNotCreateIndexIfItExists() throws IOException {
    Set<Document> documents = new HashSet<>();
    documents.add(new Document("url1", "title1", "text1"));
    when(repository.getAll()).thenReturn(documents);
    when(client.execute(isA(IndicesExists.class))).thenReturn(resultSuccessful);

    esIndexer.start();

    verify(client, never()).execute(isA(CreateIndex.class));
  }

  @Test
  public void testStartIndexesAllDocuments() throws IOException {
    Set<Document> documents = new HashSet<>();
    documents.add(new Document("url1", "title1", "text1"));
    documents.add(new Document("url2", "title2", "text2"));
    documents.add(new Document("url3", "title3", "text3"));
    when(repository.getAll()).thenReturn(documents);
    when(client.execute(any())).thenReturn(resultSuccessful);

    esIndexer.start();

    verify(client, times(documents.size())).execute(isA(Index.class));
  }
}
