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
package org.springframework.samples.petclinic.customers.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Szarlinski
 * @author Ray Tsang
 */
@RestController
@RequiredArgsConstructor
@Slf4j
class PetResource {
    static private final Logger logger = LoggerFactory.getLogger(PetResource.class);

    private final PetRepository petRepository;

    private final OwnerRepository ownerRepository;

    @GetMapping("/petTypes")
    public List<PetType> getPetTypes() {
    	return Arrays.asList(PetType.values());
    }

    @GetMapping("/owners/{ownerId}/pets")
    public List<PetDetails> listPets(
            @PathVariable("ownerId") String ownerId) {

        logger.info("Getting Pets for Owner: {}", ownerId);
        Owner owner = ownerRepository.findById(ownerId).get();
    	List<Pet> pets = petRepository.findByOwnerId(ownerId);
    	return pets.stream().map(pet -> new PetDetails(owner, pet))
				.collect(Collectors.toList());
    }

    @PostMapping("/owners/{ownerId}/pets")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processCreationForm(
        @RequestBody PetRequest petRequest,
        @PathVariable("ownerId") String ownerId) {

    	logger.info("New Pet for Owner: {}", ownerId);
        final Owner owner = ownerRepository.findById(ownerId).get();
        final Pet pet = new Pet();
        pet.setOwnerId(owner.getId());
        pet.setPetId(UUID.randomUUID().toString());

        save(pet, petRequest);
    }

    /**
     * Delete a Pet
     */
    @DeleteMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable("ownerId") String ownerId, @PathVariable("petId") String petId) {
        logger.info("Deleting Pet for Owner: {}, Pet: {}", ownerId, petId);
        petRepository.deleteById(new String[]{ownerId, petId});
    }

    @PutMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@PathVariable("ownerId") String ownerId, @PathVariable("petId") String petId,
        @RequestBody PetRequest petRequest) {
        logger.info("Updating Pet for Owner: {}, Pet: {}", ownerId, petId);
        save(petRepository.findById(new String[]{ownerId, petId}).get(), petRequest);
    }

    private void save(final Pet pet, final PetRequest petRequest) {
        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());
        pet.setType(petRequest.getType());

        log.info("Saving pet {}", pet);
        petRepository.save(pet);
    }

    @GetMapping("owners/{ownerId}/pets/{petId}")
    public PetDetails findPet(@PathVariable("ownerId") String ownerId, @PathVariable("petId") String petId) {
        logger.info("Getting Pet for Owner: {}, Pet: {}", ownerId, petId);
        Owner owner = ownerRepository.findById(ownerId).get();
        Pet pet = petRepository.findById(new String[]{ownerId, petId}).get();
        return new PetDetails(owner, pet);
    }
}
