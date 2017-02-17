package com.marclucchini.doctor.model;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class DocumentTest {

  @Test
  public void testEquals() {
    Assert.assertThat(new Document("url1", "title1", "text1"), equalTo(new Document("url1", "title1", "text1")));
    Assert.assertThat(new Document("url2", "title2", "text2"), equalTo(new Document("Url2", "title1", "text2")));
    Assert.assertThat(new Document("url1", "title1", "text1"), not(new Document("url2", "title1", "text1")));
    Assert.assertThat(new Document("url2", "title2", "text2"), not("url2"));
  }

  @Test
  public void testCompareTo() {
    int oneWithOne = new Document("url1", "title1", "text1").compareTo(new Document("url1", "title1", "text1"));
    int oneWithUpOne = new Document("url1", "title1", "text1").compareTo(new Document("Url1", "title2", "text2"));
    int oneWithTwo = new Document("url1", "title1", "text1").compareTo(new Document("Url2", "title1", "text1"));

    Assert.assertThat(oneWithOne, equalTo(0));
    Assert.assertThat(oneWithUpOne, equalTo(0));
    Assert.assertThat(oneWithTwo, not(0));
  }

  @Test
  public void testHashCode() {
    Assert.assertThat(new Document("url1", "title1", "text1").hashCode(),
        equalTo(new Document("url1", "title2", "text1").hashCode()));
    Assert.assertThat(new Document("url1", "title1", "text1").hashCode(),
        not(new Document("url2", "title1", "text1").hashCode()));
  }

  @Test
  public void testToString() {
    Assert.assertThat(new Document("url1", "title1", "text1").toString(), containsString("url1"));
    Assert.assertThat(new Document("url1", "title1", "text1").toString(), containsString("title1"));
  }
}
