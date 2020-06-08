package com.folies;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.folies.jpa"})
@ComponentScan(basePackages = {"com.folies.services.impl"})
@EntityScan(basePackages = {"com.folies.entity"})
public class TestConfig {
}
