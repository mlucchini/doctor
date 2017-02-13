package com.marclucchini.doctor.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "doctor")
public class ApplicationConfiguration {

  private String seed;
  private List<String> visit;
  private List<String> document;
  private int parallelism;
  private String database;

  public String getSeed() {
    return seed;
  }

  public void setSeed(String seed) {
    this.seed = seed;
  }

  public List<String> getVisit() {
    return visit;
  }

  public void setVisit(List<String> visit) {
    this.visit = visit;
  }

  public List<String> getDocument() {
    return document;
  }

  public void setDocument(List<String> document) {
    this.document = document;
  }

  public int getParallelism() {
    return parallelism;
  }

  public void setParallelism(int parallelism) {
    this.parallelism = parallelism;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = System.getProperty("user.dir") + File.separator + database;
  }
}
