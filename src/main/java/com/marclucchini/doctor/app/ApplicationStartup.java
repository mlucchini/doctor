package com.marclucchini.doctor.app;

import com.marclucchini.doctor.model.DocumentService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  private DocumentService documentService;

  public ApplicationStartup(DocumentService documentService) {
    this.documentService = documentService;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    documentService.init();
  }
}
