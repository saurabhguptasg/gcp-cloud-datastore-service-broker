package com.google.cloud.datastore.cf.sb.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by saurabhgu on 5/13/17.
 */
@Configuration
public class DatastoreConfig {

  @Autowired
  ApplicationCredentialConfig credentialConfig;

  @Bean
  public Datastore getDatastore() throws IOException {
    return DatastoreOptions
            .newBuilder()
            .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(credentialConfig.getApplicationCredentials().getBytes())))
            .setNamespace("_GCP_SERVICE_BROKER_NAMESPACE_")
            .build().getService();
  }

  @Bean
  public KeyFactory getServiceInstanceKeyFactory(Datastore datastore) {
    return datastore.newKeyFactory().setKind("ServiceInstance");
  }


}
