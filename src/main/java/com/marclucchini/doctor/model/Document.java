package com.marclucchini.doctor.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Document implements Comparable<Document> {

  private String url;
  private String title;
  private String html;

  public Document() {

  }

  public Document(String url, String title, String html) {
    this.url = url;
    this.title = title;
    this.html = html;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getHtml() {
    return html;
  }

  public void setHtml(String html) {
    this.html = html;
  }

  /**
   * Should rather be a fuzzy comparison of the content.
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Document) {
      Document o = (Document) other;
      return new EqualsBuilder().append(url.toLowerCase(), o.url.toLowerCase()).isEquals();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(url.toLowerCase()).toHashCode();
  }

  @Override
  public int compareTo(Document o) {
    return new CompareToBuilder().append(url.toLowerCase(), o.url.toLowerCase()).toComparison();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append(url).append(title).toString();
  }
}
