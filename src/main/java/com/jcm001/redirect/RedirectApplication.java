package com.jcm001.redirect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jcm001.redirect.service.EndpointService;

@SpringBootApplication
public class RedirectApplication {

	public static void main(String[] args) {
		try {
			EndpointService endpointService = new EndpointService();
			endpointService.resetService();
			SpringApplication.run(RedirectApplication.class, args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
