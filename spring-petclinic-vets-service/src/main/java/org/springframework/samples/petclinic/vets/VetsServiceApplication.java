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
package org.springframework.samples.petclinic.vets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.vets.model.Specialty;
import org.springframework.samples.petclinic.vets.model.Vet;
import org.springframework.samples.petclinic.vets.model.VetRepository;
import org.springframework.samples.petclinic.vets.system.VetsProperties;

import java.util.Arrays;
import java.util.List;

/**
 * @author Maciej Szarlinski
 */
@SpringBootApplication
@EnableConfigurationProperties(VetsProperties.class)
public class VetsServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(VetsServiceApplication.class);

	@Bean
	public CommandLineRunner init(VetRepository vetRepository) {
		return (args) -> {
			if (vetRepository.count() == 0) {
				logger.info("Initializing Data...");
				Vet vet = new Vet();
				vet.setId("1");
				vet.setFirstName("James");
				vet.setLastName("Carter");
				List<Specialty> specialties = Arrays.asList(Specialty.INTERNAL_MEDICINE, Specialty.RADIOLOGY);
				vet.setSpecialties(specialties);
				vetRepository.save(vet);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VetsServiceApplication.class, args);
	}
}
