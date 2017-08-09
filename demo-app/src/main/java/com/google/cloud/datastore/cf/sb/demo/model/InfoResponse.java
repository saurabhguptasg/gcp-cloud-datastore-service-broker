package com.google.cloud.datastore.cf.sb.demo.model;

/**
 * Created by saurabhgu on 5/14/17.
 */
public class InfoResponse {
  private Long id;
  private String kind;
  private String key;
  private String value;

  public InfoResponse() {
  }

  public InfoResponse(Long id, String kind, String key, String value) {
    this.id = id;
    this.kind = kind;
    this.key = key;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public String getKind() {
    return kind;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}
