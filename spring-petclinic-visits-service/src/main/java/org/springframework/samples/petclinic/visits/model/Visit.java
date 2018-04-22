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
package org.springframework.samples.petclinic.visits.model;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Column;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Ken Krebs
 * @author Maciej Szarlinski
 */
@Table(name = "visits")
@Data
@ToString
public class Visit {
    @PrimaryKey(keyOrder = 1)
    @Column(name = "owner_id")
    private String ownerId;

    @PrimaryKey(keyOrder = 2)
    @Column(name = "pet_id")
    private String petId;

    @PrimaryKey(keyOrder = 3)
	@Column(name = "visit_id")
    private String visitId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Size(max = 8192)
    private String description;

}
