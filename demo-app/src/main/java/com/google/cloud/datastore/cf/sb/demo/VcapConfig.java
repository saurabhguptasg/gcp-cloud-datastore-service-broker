package com.google.cloud.datastore.cf.sb.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by saurabhgu on 5/14/17.
 */
@Configuration
public class VcapConfig {

  @Value("${VCAP_SERVICES:{}}")
  private String vcapServicesStr;

  private JsonObject vcapServicesObject;
  private GoogleCredentials googleCredentials;
  private String projectId;
  private String serviceInstanceId;

  @PostConstruct
  private void processVcapServices() throws IOException {
    System.out.println(">>> >>> vcapServicesStr = " + vcapServicesStr);
    vcapServicesObject = new JsonParser().parse(vcapServicesStr).getAsJsonObject();

    if(vcapServicesObject.has("gcds")) {
      JsonArray gcds = vcapServicesObject.getAsJsonArray("gcds");
      JsonObject params = gcds.get(0).getAsJsonObject();
      JsonObject credentials = params.getAsJsonObject("credentials");

      String googleCredentialStr = credentials.get("applicationCredentials").getAsString();
      System.out.println("googleCredentialStr = " + googleCredentialStr);

      try {
        googleCredentials = GoogleCredentials.fromStream(new ByteArrayInputStream(googleCredentialStr.getBytes()));
        System.out.println("googleCredentials = " + googleCredentials);
      } catch (IOException e) {
        e.printStackTrace();
        throw e;
      }

      serviceInstanceId = credentials.get("serviceInstanceId").getAsString();
      System.out.println("serviceInstanceId = " + serviceInstanceId);

      projectId = credentials.get("projectId").getAsString();
    }
    else {
      System.out.println("-->> -->> no gcds object!!");
    }
  }

  @Bean
  public JsonObject getVcapServicesObject() {
    return vcapServicesObject;
  }

  @Bean
  public Datastore getDatastore() {
    if(googleCredentials != null) {
      System.out.println(">> >> >> >> CREATING DATASTORE with namespace: " + serviceInstanceId);
      return DatastoreOptions
              .newBuilder()
              .setProjectId(projectId)
              .setCredentials(googleCredentials)
              .setNamespace(serviceInstanceId)
              .build().getService();
    }
    return null;
  }
}
