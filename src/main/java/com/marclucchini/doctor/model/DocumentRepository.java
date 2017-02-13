package com.marclucchini.doctor.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marclucchini.doctor.app.ApplicationConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Repository
public class DocumentRepository {

  private static final Log logger = LogFactory.getLog(DocumentRepository.class);
  private ApplicationConfiguration configuration;
  private ConcurrentSkipListSet<Document> documents = new ConcurrentSkipListSet<>();

  public DocumentRepository(ApplicationConfiguration configuration) {
    this.configuration = configuration;
  }

  public void add(Document document) {
    documents.add(document);
    logger.info(String.format("Document with URL \"%s\" added [%d]", document.getUrl(), documents.size()));
  }

  public void load() throws IOException {
    String database = configuration.getDatabase();
    File file = new File(database);
    if (file.exists()) {
      logger.info(String.format("Loading document repository from file at \"%s\"",
          file.toPath().toAbsolutePath().toString()));
      documents = new ObjectMapper().readValue(file, new TypeReference<ConcurrentSkipListSet<Document>>() {});
    }
  }

  public void save() throws IOException {
    String database = configuration.getDatabase();
    File file = new File(database);
    new ObjectMapper().writeValue(file, documents);
  }

  public Set<Document> getAll() {
    return documents;
  }

  public boolean isEmpty() {
    return documents.isEmpty();
  }
}
