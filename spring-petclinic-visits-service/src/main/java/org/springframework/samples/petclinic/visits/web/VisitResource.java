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
package org.springframework.samples.petclinic.visits.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.visits.model.Visit;
import org.springframework.samples.petclinic.visits.model.VisitRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Maciej Szarlinski
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class VisitResource {

    private final VisitRepository visitRepository;

    @PostMapping("owners/{ownerId}/pets/{petId}/visits")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(
        @Valid @RequestBody Visit visit,
        @PathVariable("ownerId") String ownerId,
        @PathVariable("petId") String petId) {

    	visit.setOwnerId(ownerId);
        visit.setPetId(petId);
        visit.setVisitId(UUID.randomUUID().toString());
        log.info("Saving visit {}", visit);
        visitRepository.save(visit);
    }

    @GetMapping("owners/{ownerId}/pets/{petId}/visits")
    public List<Visit> visits(@PathVariable("ownerId") String ownerId,
            @PathVariable("petId") String petId) {
        return visitRepository.findByOwnerIdAndPetId(ownerId, petId);
    }
}
