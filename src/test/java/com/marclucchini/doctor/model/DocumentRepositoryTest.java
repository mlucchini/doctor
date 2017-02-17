package com.marclucchini.doctor.model;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentRepositoryTest {

  DocumentRepository repository;
  ApplicationConfiguration configuration;
  Path database;

  @Before
  public void setUp() throws IOException {
    configuration = mock(ApplicationConfiguration.class);
    repository = new DocumentRepository(configuration);
    database = Files.createTempFile("db", "tmp").toAbsolutePath();
  }

  @After
  public void tearDown() throws IOException {
    Files.deleteIfExists(database);
  }

  @Test
  public void testAddDocument() {
    repository.add(new Document("url1", "title1", "text1"));

    Assert.assertThat(repository.getAll().size(), is(1));
  }

  @Test
  public void testAddDuplicateDocuments() {
    repository.add(new Document("url2", "title2", "text2"));
    repository.add(new Document("url2", "title2", "text2"));

    Assert.assertThat(repository.getAll().size(), is(1));
  }

  @Test
  public void testLoadDocuments() throws IOException {
    Files.write(database, "[{\"url\":\"url5\",\"title\":\"title5\",\"html\":\"text5\"}]".getBytes());
    when(configuration.getDatabase()).thenReturn(database.toString());

    repository.load();

    Assert.assertThat(repository.getAll(), hasItem(new Document("url5", "title5", "text5")));
  }

  @Test
  public void testLoadUnexistingDocuments() throws IOException {
    when(configuration.getDatabase()).thenReturn("unexisting-file");

    repository.load();
  }

  @Test
  public void testSaveDocuments() throws IOException {
    when(configuration.getDatabase()).thenReturn(database.toString());

    repository.add(new Document("url3", "title3", "text3"));
    repository.add(new Document("url4", "title4", "text4"));
    repository.save();

    Assert.assertThat(database.toFile().exists(), is(true));
  }
}
