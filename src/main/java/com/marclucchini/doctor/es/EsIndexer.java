package com.marclucchini.doctor.es;

import com.marclucchini.doctor.model.Document;
import com.marclucchini.doctor.model.DocumentRepository;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.cluster.Health;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.Stats;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class EsIndexer {

  private static final Log logger = LogFactory.getLog(EsIndexer.class);
  private static final String INDEX = "nhs";
  private static final String TYPE = "condition";
  private JestClient client;
  private DocumentRepository repository;

  public EsIndexer(EsClientFactory clientFactory, DocumentRepository repository) {
    this.client = clientFactory.newInstance();
    this.repository = repository;
  }

  public void start() throws IOException {
    setIndex();
    indexDocuments();
  }

  public EsIndexerStatus getStatus() throws IOException {
    JestResult health = client.execute(new Health.Builder().build());
    JestResult stats = client.execute(new Stats.Builder().build());
    return new EsIndexerStatus(health, stats);
  }

  protected void setIndex() throws IOException {
    boolean indexExists = client.execute(new IndicesExists.Builder(INDEX).build()).isSucceeded();
    if (indexExists) {
      logger.info(String.format("The index %s already exists and will not be re-created", INDEX));
    } else {
      String conf = new Scanner(getClass().getResourceAsStream("index-nhs.json")).useDelimiter("\\A").next();
      JestResult result = client.execute(new CreateIndex(INDEX, conf));
      if (! result.isSucceeded()) {
        throw new RuntimeException(result.getErrorMessage());
      }
      logger.info(String.format("Index %s was created", INDEX));
    }
  }

  protected void indexDocuments() throws IOException {
    for (Document d : repository.getAll()) {
      DocumentResult result = client.execute(new Index.Builder(d).index(INDEX).type(TYPE).id(d.getUrl()).build());
      logger.info(String.format("Document with URL \"%s\" indexed: %s %s", d.getUrl(), result.isSucceeded(),
          result.getErrorMessage()));
    }
  }
}
