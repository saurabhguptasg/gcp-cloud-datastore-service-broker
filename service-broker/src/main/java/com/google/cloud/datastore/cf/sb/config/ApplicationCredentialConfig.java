package com.google.cloud.datastore.cf.sb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by saurabhgu on 5/14/17.
 */
@Configuration
@Component
public class ApplicationCredentialConfig {

  @Value("${GOOGLE_CREDENTIALS}")
  private String applicationCredentials;

  public String getApplicationCredentials() {
    return applicationCredentials;
  }
}
