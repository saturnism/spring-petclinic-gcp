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
package org.springframework.samples.petclinic.customers;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.customers.model.*;

import java.time.Instant;

/**
 * @author Maciej Szarlinski
 * @author Ray Tsang
 */
@SpringBootApplication
public class CustomersServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(CustomersServiceApplication.class);

	@Bean
	public CommandLineRunner initialize(OwnerRepository ownerRepository, PetRepository petRepository) {
		return (args) -> {
			if (ownerRepository.count() == 0 && petRepository.count() == 0) {
				logger.info("Initializing data...");
				Owner owner = new Owner();
				owner.setId("1");
				owner.setFirstName("Ray");
				owner.setLastName("Tsang");
				owner.setAddress("111 8th Ave.");
				owner.setCity("New York");
				owner.setTelephone("1111111111");
				ownerRepository.save(owner);

				logger.info("Owner: {}", owner);

				Pet pet = new Pet();
				pet.setType(PetType.DOG);
				pet.setOwnerId("1");
				pet.setPetId("1");
				pet.setName("Harley");
				pet.setBirthDate(Date.from(Instant.parse("2007-12-03T11:11:11Z")));
				petRepository.save(pet);
				logger.info("Pet: {}", pet);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomersServiceApplication.class, args);
	}
}
