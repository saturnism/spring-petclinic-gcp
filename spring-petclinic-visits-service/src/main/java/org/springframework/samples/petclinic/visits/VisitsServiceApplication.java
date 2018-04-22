/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.visits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.springframework.samples.petclinic.visits.model.VisitRepository;

import java.time.Instant;
import java.util.Date;

/**
 * @author Maciej Szarlinski
 * @author Ray Tsang
 */
@EnableDiscoveryClient
@SpringBootApplication
public class VisitsServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(VisitsServiceApplication.class);

    @Bean
    public CommandLineRunner init(VisitRepository visitRepository) {
        return (args) -> {
            if (visitRepository.count() == 0) {
                logger.info("Initializing Data...");
                Visit visit = new Visit();
                visit.setOwnerId("1");
                visit.setPetId("1");
                visit.setVisitId("1");
                visit.setDate(Date.from(Instant.parse("2017-12-25T00:00:00Z")));
                visit.setDescription("Take a quick look!");
                visitRepository.save(visit);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VisitsServiceApplication.class, args);
    }
}
