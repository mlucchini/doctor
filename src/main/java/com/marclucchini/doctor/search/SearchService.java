package com.marclucchini.doctor.search;

import com.marclucchini.doctor.es.EsClientFactory;
import com.marclucchini.doctor.model.Document;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

  private JestClient client;

  public SearchService(EsClientFactory factory) {
    this.client = factory.newInstance();
  }

  public List<Document> search(String query) throws IOException {
    String[] fields = new String[]{ "url", "title" };
    String searchSource = new SearchSourceBuilder().fetchSource(fields, null)
        .query(QueryBuilders.matchQuery("html", query)).toString();
    Search search = new Search.Builder(searchSource).addIndex("nhs").build();
    SearchResult result = client.execute(search);
    return result.getHits(Document.class).stream().map(s -> s.source).collect(Collectors.toList());
  }
}
