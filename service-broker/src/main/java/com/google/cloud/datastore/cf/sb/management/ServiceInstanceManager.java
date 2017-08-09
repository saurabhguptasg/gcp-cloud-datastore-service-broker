package com.google.cloud.datastore.cf.sb.management;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by saurabhgu on 5/13/17.
 */
@Component
public class ServiceInstanceManager implements ServiceInstanceService {
  private static final Logger LOGGER = Logger.getLogger(ServiceInstanceManager.class.getName());

  @Autowired
  Datastore datastore;

  @Autowired
  KeyFactory serviceInstanceKeyFactory;

  @Autowired
  GoogleCredential googleCredential;

  @Override
  public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
    System.out.println("request.getServiceInstanceId() = " + request.getServiceInstanceId());
    System.out.println("googleCredential.getRefreshToken() = " + googleCredential.getRefreshToken());
    System.out.println("datastore = " + datastore);
    CreateServiceInstanceResponse response = new CreateServiceInstanceResponse();

    try {
      Entity entity =
              Entity.newBuilder(serviceInstanceKeyFactory.newKey(request.getServiceInstanceId()))
                      .set("organizationGuid", request.getOrganizationGuid())
                      .set("spaceGuid", request.getSpaceGuid())
                      .set("serviceDefinitionId", request.getServiceDefinitionId())
                      .set("planId", request.getPlanId())
                      .build();
      datastore.put(entity);

      System.out.println("created entity with key: " + entity.getKey());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    return response;
  }

  @Override
  public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
    GetLastServiceOperationResponse response = new GetLastServiceOperationResponse();
    response.withOperationState(OperationState.SUCCEEDED);
    return response;
  }

  @Override
  public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
    Entity entity = datastore.get(serviceInstanceKeyFactory.newKey(request.getServiceInstanceId()));
    if(entity != null) {
      entity = Entity.newBuilder(entity).set("deleted",true).build();
      datastore.update(entity);
    }
    DeleteServiceInstanceResponse response = new DeleteServiceInstanceResponse();
    response.withAsync(false).withOperation(OperationState.SUCCEEDED.getValue());
    return response;
  }

  @Override
  public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
    UpdateServiceInstanceResponse response =  new UpdateServiceInstanceResponse();
    response.withAsync(false);
    return response;
  }
}
