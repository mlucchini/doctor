package com.marclucchini.doctor.app;

import com.marclucchini.doctor.model.DocumentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import static org.mockito.Mockito.*;

public class ApplicationStartupTest {

  DocumentService documentService;
  ApplicationStartup startup;

  @Before
  public void setUp() throws Exception {
    documentService = mock(DocumentService.class);
    startup = new ApplicationStartup(documentService);
  }

  @Test
  public void testOnApplicationEvent() throws Exception {
    startup.onApplicationEvent(mock(ApplicationReadyEvent.class));

    verify(documentService, times(1)).init();
  }
}
