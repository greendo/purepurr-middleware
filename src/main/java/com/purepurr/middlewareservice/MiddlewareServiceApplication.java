package com.purepurr.middlewareservice;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiddlewareServiceApplication {

	public static ServiceProvider serviceProvider;

	public static void main(String[] args) {

		try {
			registerInZookeeper(9797);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(MiddlewareServiceApplication.class, args);
	}

	private static void registerInZookeeper(int port) throws Exception {
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(5, 1000));
		curatorFramework.start();

		ServiceDiscovery<Object> serviceDiscovery = ServiceDiscoveryBuilder
				.builder(Object.class)
				.basePath("api")
				.client(curatorFramework).build();
		serviceDiscovery.start();

		serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("api-service").build();
		serviceProvider.start();
	}
}
