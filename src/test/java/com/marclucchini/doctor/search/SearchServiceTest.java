package com.marclucchini.doctor.search;

import com.marclucchini.doctor.es.EsClientFactory;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SearchServiceTest {

  JestClient client;
  EsClientFactory factory;
  SearchService searchService;
  SearchResult result;

  @Before
  public void setup() {
    client = mock(JestClient.class);
    factory = mock(EsClientFactory.class);
    result = mock(SearchResult.class);
    given(factory.newInstance()).willReturn(client);
    searchService = new SearchService(factory);
  }

  @Test
  public void testSearchDoesNotThrow() throws IOException {
    List<SearchResult.Hit<Object, Void>> hits = new ArrayList<>();
    when(client.execute(any(Search.class))).thenReturn(result);
    when(result.getHits(any())).thenReturn(hits);

    searchService.search("");
  }
}
