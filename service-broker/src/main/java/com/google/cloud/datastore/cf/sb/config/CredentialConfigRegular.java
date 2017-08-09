package com.google.cloud.datastore.cf.sb.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by saurabhgu on 5/13/17.
 */
@Configuration
@Profile("!AppEngine")
public class CredentialConfigRegular {

  @Autowired
  ApplicationCredentialConfig credentialConfig;

  @Bean
  public GoogleCredential getGoogleCredential() throws IOException {
    System.out.println(">>>\n>>>\n>>> invoking non app engine profile");
    System.out.println("credentialConfig.getApplicationCredentials() = " + credentialConfig.getApplicationCredentials());

    return
            GoogleCredential
                    .fromStream(new ByteArrayInputStream(credentialConfig.getApplicationCredentials().getBytes()))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform",
                                                "https://www.googleapis.com/auth/datastore"));
  }

}
