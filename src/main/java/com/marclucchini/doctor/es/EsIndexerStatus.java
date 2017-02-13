package com.marclucchini.doctor.es;

import com.google.gson.JsonObject;
import io.searchbox.client.JestResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EsIndexerStatus {

  private static final Log logger = LogFactory.getLog(EsIndexerStatus.class);

  private String status = "unknown";
  private int nodes = -1;
  private int documents = -1;

  public EsIndexerStatus(JestResult health, JestResult stats) {
    try {
      status = health.getJsonObject().get("status").getAsString();
      nodes = health.getJsonObject().get("number_of_nodes").getAsInt();
    } catch (RuntimeException e) {
      logger.warn(String.format("Unable to retrieve info from health object: %s", health.getJsonString()), e);
    }
    try {
      JsonObject total = stats.getJsonObject().get("_all").getAsJsonObject().get("total").getAsJsonObject();
      if (total.get("docs") != null) {
        documents = total.get("docs").getAsJsonObject().get("count").getAsInt();
      } else {
        documents = 0;
      }
    } catch (RuntimeException e) {
      logger.warn(String.format("Unable to retrieve info from stats object: %s", stats.getJsonString()), e);
    }
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getNodes() {
    return nodes;
  }

  public void setNodes(int nodes) {
    this.nodes = nodes;
  }

  public int getDocuments() {
    return documents;
  }

  public void setDocuments(int documents) {
    this.documents = documents;
  }
}
