package com.google.cloud.datastore.cf.sb.config;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * Created by saurabhgu on 5/13/17.
 */
@Configuration
public class CatalogConfig {


  @Bean
  public Catalog catalog() {
    return new Catalog(Collections.singletonList(
            new ServiceDefinition(
                    "google-cloud-datastore-broker",
                    "gcds",
                    "A simple service broker implementation for Google Cloud Datastore that provisions a dedicated namespace for your project",
                    true,
                    false,
                    Collections.singletonList(
                            new Plan("standard-plan",
                                    "default",
                                    "This is the standard datastore plan. All capabilities are available.",
                                     getPlanMetadata())),
                    Arrays.asList("clouddatastore", "datastore", "document", "nosql"),
                    getServiceDefinitionMetadata(),
                    null,
                    null)));
  }

  private Map<String, Object> getServiceDefinitionMetadata() {
    Map<String, Object> sdMetadata = new HashMap<>();
    sdMetadata.put("displayName", "Google Cloud Datastore");
    sdMetadata.put("imageUrl", "https://cloud.google.com/_static/images/cloud/products/logos/svg/datastore.svg");
    sdMetadata.put("longDescription", "Cloud Datastore Service");
    sdMetadata.put("providerDisplayName", "Google");
    sdMetadata.put("documentationUrl", "https://cloud.google.com/datastore/docs/");
    sdMetadata.put("supportUrl", "https://cloud.google.com/support/");
    return sdMetadata;
  }

  private Map<String,Object> getPlanMetadata() {
    Map<String,Object> planMetadata = new HashMap<>();
    planMetadata.put("costs", getCosts());
    planMetadata.put("bullets", getBullets());
    return planMetadata;
  }

  private List<Map<String,Object>> getCosts() {
    Map<String,Object> costsMap = new HashMap<>();

    Map<String,Object> amount = new HashMap<>();
    amount.put("usd", 0.0);
    costsMap.put("amount", amount);
    costsMap.put("unit", "MONTHLY");

    return Collections.singletonList(costsMap);
  }

  private List<String> getBullets() {
    return Arrays.asList("Partitioned Datastore service",
            "unlimited storage",
            "unlimited connections");
  }

}
