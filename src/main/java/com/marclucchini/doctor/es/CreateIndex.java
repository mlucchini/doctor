package com.marclucchini.doctor.es;

import io.searchbox.action.GenericResultAbstractAction;

public class CreateIndex extends GenericResultAbstractAction {

  public CreateIndex(String index, String configuration) {
    this.indexName = index;
    this.payload = configuration;
    setURI(buildURI());
  }

  @Override
  public String getRestMethodName() {
    return "PUT";
  }
}
