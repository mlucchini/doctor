package com.marclucchini.doctor.crawl;

import com.marclucchini.doctor.app.ApplicationConfiguration;
import com.marclucchini.doctor.model.DocumentRepository;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CrawlerTest {

  ApplicationConfiguration configuration;
  DocumentRepository documentRepository;
  Crawler crawler;

  @Before
  public void setUp() throws IOException {
    configuration = mock(ApplicationConfiguration.class);
    documentRepository = mock(DocumentRepository.class);
    given(configuration.getVisit()).willReturn(Collections.singletonList(
        "http://www\\.nhs\\.uk/[cC]onditions/Pages/BodyMap\\.aspx\\?Index=[A-Z0-9-]+"));
    given(configuration.getDocument()).willReturn(Arrays.asList(
        "http://www\\.nhs\\.uk/[cC]onditions/[^/\\.]+",
        "http://www\\.nhs\\.uk/[cC]onditions/.+\\.aspx$"));

    crawler = new Crawler(configuration, documentRepository);
  }

  @Test
  public void testShouldNotVisit() {
    List<String> urls = Arrays.asList(
        "https://www.babylonhealth.com",
        "https://healthunlocked.com/nhschoices",
        "http://www.nhs.uk/NHSEngland/Pages/NHSEngland.aspx",
        "http://www.nhs.uk/conditions/Pages/bodymap.aspx?LevelOne=Head&Gender=Female",
        "http://www.nhs.uk/Conditions/cosmetic-treatments-guide/PublishingImages/liposuction_364x200_157589112.jpg",
        "http://www.nhs.uk/conditions/Munchausens-syndrome.png",
        "http://www.nhs.uk/conditions/Munchausens-syndrome.zip",
        "http://www.nhs.uk/css/base.css");

    urls.stream().forEach(url -> {
      WebURL webUrl = new WebURL();
      webUrl.setURL(url);
      Assert.assertThat(crawler.shouldVisit(mock(Page.class), webUrl), is(false));
    });
  }

  @Test
  public void testShouldVisit() {
    List<String> urls = Arrays.asList(
        "http://www.nhs.uk/conditions/Pages/BodyMap.aspx?Index=J",
        "http://www.nhs.uk/conditions/Pages/BodyMap.aspx?Index=0-9",
        "http://www.nhs.uk/conditions/psoriatic-arthritis/Pages/Introduction.aspx",
        "http://www.nhs.uk/Conditions/Chickenpox/Pages/Prevention.aspx",
        "http://www.nhs.uk/Conditions/lactose-intolerance/Pages/Zoesstory.aspx",
        "http://www.nhs.uk/Conditions/fatty-liver-disease/Pages/clinical-trial.aspx",
        "http://www.nhs.uk/conditions/pregnancy-and-baby/pages/treating-high-temperature-children.aspx",
        "http://www.nhs.uk/conditions/Munchausens-syndrome",
        "http://www.nhs.uk/conditions/Cystitis");

    urls.stream().forEach(url -> {
      WebURL webUrl = new WebURL();
      webUrl.setURL(url);
      Assert.assertThat(crawler.shouldVisit(mock(Page.class), webUrl), is(true));
    });
  }

  @Test
  public void testVisitAndAdd() {
    Page page = mock(Page.class);
    when(page.getParseData()).thenReturn(mock(HtmlParseData.class));
    when(page.getWebURL()).thenReturn(mock(WebURL.class));

    crawler.visit(page);

    verify(documentRepository, times(1)).add(any());
  }

  @Test
  public void testVisitAndIgnore() {
    Page page = mock(Page.class);
    when(page.getParseData()).thenReturn(mock(BinaryParseData.class));
    when(page.getWebURL()).thenReturn(mock(WebURL.class));

    crawler.visit(page);

    verify(documentRepository, never()).add(any());
  }
}
