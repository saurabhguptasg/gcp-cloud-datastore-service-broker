package com.google.cloud.datastore.cf.sb.demo;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.cf.sb.demo.model.InfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by saurabhgu on 5/14/17.
 */
@RestController
public class SimpleController {

  @Autowired
  Datastore datastore;

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<?> getInfo(HttpServletRequest request) {
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("DemoItem");

    Key key = keyFactory.newKey(System.currentTimeMillis());
    String value = new Date(key.getId()).toString();

    Entity entity = Entity.newBuilder(key).set("value", value).build();
    datastore.put(entity);


    InfoResponse response = new InfoResponse(key.getId(), key.getKind(), key.toUrlSafe(), value);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
