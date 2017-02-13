package com.marclucchini.doctor.search;

import com.marclucchini.doctor.model.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {

  private SearchService searchService;

  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping("/search")
  @ResponseBody
  List<Document> getQuery(@RequestParam(name = "query") String query) throws IOException {
    return searchService.search(query);
  }
}
