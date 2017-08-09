package com.google.cloud.datastore.cf.sb.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

/**
 * Created by saurabhgu on 5/13/17.
 *
 */
@Configuration
@Profile("AppEngine")
public class CredentialConfigAppEngine {

  @Bean
  public GoogleCredential getGoogleCredential() throws IOException {
    return GoogleCredential.getApplicationDefault();
  }
}
