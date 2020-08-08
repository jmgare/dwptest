package com.dwp.userlocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.dwp.userlocator.city.CityLocationService;
import com.dwp.userlocator.city.CityLocationServiceImpl;
import com.dwp.userlocator.city.ProximityCalc;
import com.dwp.userlocator.city.ProximityCalcImpl;
import com.dwp.userlocator.userapi.UserAPIService;
import com.dwp.userlocator.userapi.UserAPIServiceImpl;

@SpringBootApplication
public class UserLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLocatorApplication.class, args);
	}
	
	@Configuration
	public static class Config {
	    	    
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
        
        @Bean
        public UserAPIService userApiService() {
            return new UserAPIServiceImpl();
        }
        
        @Bean
        public CityLocationService cityLocation() {
            return new CityLocationServiceImpl();
        }
        
        @Bean
        public ProximityCalc proximityCalc() {
            return new ProximityCalcImpl();
        }
        
        @Bean
        @ConfigurationProperties("user.locator")
        public UserLocatorProperties userLocatorProperties() {
            return new UserLocatorProperties();
        }
	}
}
