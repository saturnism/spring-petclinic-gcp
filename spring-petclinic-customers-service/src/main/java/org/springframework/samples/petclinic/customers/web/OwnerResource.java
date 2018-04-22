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
import org.springframework.samples.petclinic.customers.model.Owner;
import org.springframework.samples.petclinic.customers.model.OwnerRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Maciej Szarlinski
 * @author Ray Tsang
 */
@RequestMapping("/owners")
@RestController
@RequiredArgsConstructor
@Slf4j
class OwnerResource {
    static private final Logger logger = LoggerFactory.getLogger(OwnerResource.class);

    private final OwnerRepository ownerRepository;

    /**
     * Create Owner
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOwner(@Valid @RequestBody Owner owner) {
    	owner.setId(UUID.randomUUID().toString());
        ownerRepository.save(owner);
    }

    /**
     * Read single Owner
     */
    @GetMapping(value = "/{ownerId}")
    public Owner findOwner(@PathVariable("ownerId") String ownerId) {
        logger.info("Getting Owner ID: {}", ownerId);
        return ownerRepository.findById(ownerId).get();
    }

    /**
     * Delete an Owner
     */
    @DeleteMapping(value = "/{ownerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwner(
            @PathVariable("ownerId") String ownerId) {
        logger.info("Deleting Owner ID: {}", ownerId);
        ownerRepository.deleteById(ownerId);
    }

    /**
     * Read List of Owners
     */
    @GetMapping
    public Iterable<Owner> findAll() {
        logger.info("Getting All Owners");
        return ownerRepository.findAll();
    }

    /**
     * Update Owner
     */
    @PutMapping(value = "/{ownerId}")
    public Owner updateOwner(@PathVariable("ownerId") String ownerId, @Valid @RequestBody Owner ownerRequest) {
        logger.info("Updating Owner: {}", ownerId);
        final Owner ownerModel = ownerRepository.findById(ownerId).get();
        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
        ownerModel.setFirstName(ownerRequest.getFirstName());
        ownerModel.setLastName(ownerRequest.getLastName());
        ownerModel.setCity(ownerRequest.getCity());
        ownerModel.setAddress(ownerRequest.getAddress());
        ownerModel.setTelephone(ownerRequest.getTelephone());
        log.info("Saving owner {}", ownerModel);
        return ownerRepository.save(ownerModel);
    }
}
