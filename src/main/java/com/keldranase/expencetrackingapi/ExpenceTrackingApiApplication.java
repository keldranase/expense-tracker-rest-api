package com.keldranase.expencetrackingapi;

import com.keldranase.expencetrackingapi.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.FilterRegistration;

@SpringBootApplication
public class ExpenceTrackingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenceTrackingApiApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		AuthFilter authFilter = new AuthFilter();
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>(authFilter);
		// all points, for managing categories must be protected
		// so, any URL, starting with /api/categories must be protected
		registrationBean.addUrlPatterns("/api/categories/*");

		return registrationBean;
	}
}
