package com.google.cloud.datastore.cf.sb;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceBrokerApplicationTests {

	@Autowired
	Datastore datastore;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testDatastore() {
		KeyFactory keyFactory = datastore.newKeyFactory().setNamespace("_SBTEST_").setKind("TestItem");
		Entity entity = Entity.newBuilder(keyFactory.newKey(System.currentTimeMillis())).set("time", new Date().toString()).build();
		datastore.put(entity);
	}

}
