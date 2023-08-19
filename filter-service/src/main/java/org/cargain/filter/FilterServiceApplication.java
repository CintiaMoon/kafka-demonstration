package org.cargain.filter;

import org.cargain.common.utils.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {Paths.ConfigurationBasePackage, Paths.Filter.ServiceBasePackage})
public class FilterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilterServiceApplication.class, args);
	}

}
