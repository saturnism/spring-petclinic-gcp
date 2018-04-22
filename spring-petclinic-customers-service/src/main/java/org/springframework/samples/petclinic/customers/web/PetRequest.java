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

import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.samples.petclinic.customers.model.PetType;

/**
 * @author mszarlinski@bravurasolutions.com on 2016-12-05.
 * @author Ray Tsang
 */
@Data
class PetRequest {
	@NotEmpty
	private String ownerId;

    private String petId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Size(min = 1)
    private String name;

    @NotNull
    private PetType type;
}
