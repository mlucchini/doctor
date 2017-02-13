package com.marclucchini.doctor.crawl;

public class CrawlerStatus {

  private boolean inProgress;
  private int documents;

  public CrawlerStatus(boolean inProgress, int documents) {
    this.inProgress = inProgress;
    this.documents = documents;
  }

  public boolean isInProgress() {
    return inProgress;
  }

  public void setInProgress(boolean inProgress) {
    this.inProgress = inProgress;
  }

  public int getDocuments() {
    return documents;
  }

  public void setDocuments(int documents) {
    this.documents = documents;
  }
}
