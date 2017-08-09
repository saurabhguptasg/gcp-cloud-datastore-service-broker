package com.google.cloud.datastore.cf.sb.management;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.cf.sb.config.ApplicationCredentialConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saurabhgu on 5/13/17.
 */
@Component
public class ServiceInstanceBindingManager implements ServiceInstanceBindingService {

  @Autowired
  Datastore datastore;

  @Autowired
  KeyFactory serviceInstanceKeyFactory;

  @Autowired
  GoogleCredential googleCredential;

  @Autowired
  ApplicationCredentialConfig credentialConfig;

  public KeyFactory getBindingInstanceKeyFactory(Datastore datastore,
                                                 Key ancestor) {
    return datastore.newKeyFactory().setKind("BindingInstance").addAncestor(PathElement.of(ancestor.getKind(), ancestor.getName()));
  }


  @Override
  public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
    CreateServiceInstanceAppBindingResponse response = new CreateServiceInstanceAppBindingResponse();
    response.withBindingExisted(false);

    Entity serviceInstance = datastore.get(serviceInstanceKeyFactory.newKey(request.getServiceInstanceId()));
    System.out.println("serviceInstance = " + serviceInstance);
    System.out.println("credentialConfig.getApplicationCredentials() = " + credentialConfig.getApplicationCredentials());
    if(serviceInstance != null) {
      try {
        String refreshToken = googleCredential.getRefreshToken();
        String accessToken = googleCredential.getAccessToken();
        KeyFactory bindingInstanceKeyFactory = getBindingInstanceKeyFactory(datastore, serviceInstance.getKey());
        Key key = bindingInstanceKeyFactory.newKey(request.getBindingId());
        Entity bindingInstance =
                Entity.newBuilder(key)
                        .set("time", System.currentTimeMillis())
                        .build();
        datastore.put(bindingInstance);

        Map<String,Object> credentials = new HashMap<>();
        credentials.put("bindingId", request.getBindingId());
        credentials.put("serviceInstanceId", request.getServiceInstanceId());
        credentials.put("applicationCredentials", credentialConfig.getApplicationCredentials());
        response.withCredentials(credentials);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }

    }

    return response;
  }

  @Override
  public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
    Key serviceInstanceKey = serviceInstanceKeyFactory.newKey(request.getServiceInstanceId());
    Key bindingInstanceKey =
            getBindingInstanceKeyFactory(datastore,
                                         serviceInstanceKey)
                    .newKey(request.getBindingId());
    Entity bindingEntity = datastore.get(bindingInstanceKey);
    if(bindingEntity != null) {
      bindingEntity = Entity.newBuilder(bindingEntity).set("deleted", true).build();
      datastore.update(bindingEntity);
    }
  }
}
