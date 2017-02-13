package com.marclucchini.doctor.crawl;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import com.marclucchini.doctor.model.Document;
import com.marclucchini.doctor.model.DocumentRepository;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Crawler extends WebCrawler {

  private DocumentRepository repository;
  private List<Pattern> visitUrls;
  private List<Pattern> documentUrls;

  public Crawler(ApplicationConfiguration configuration, DocumentRepository repository) {
    visitUrls = configuration.getVisit().stream().map(Pattern::compile).collect(Collectors.toList());
    documentUrls = configuration.getDocument().stream().map(Pattern::compile).collect(Collectors.toList());
    this.repository = repository;
  }

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL();
    return visitUrls.stream().anyMatch((p) -> p.matcher(href).matches())
      || documentUrls.stream().anyMatch((p) -> p.matcher(href).matches());
  }

  @Override
  public void visit(Page page) {
    if (page.getParseData() instanceof HtmlParseData) {
      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
      String url = page.getWebURL().getURL();
      String title = htmlParseData.getTitle();
      String text = htmlParseData.getHtml();
      repository.add(new Document(url, title, text));
    }
  }
}
