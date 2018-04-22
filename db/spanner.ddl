CREATE TABLE owners (
    owner_id STRING(36) NOT NULL,
    first_name STRING(128) NOT NULL,
    last_name STRING(128) NOT NULL,
    address STRING(256) NOT NULL,
    city STRING(128) NOT NULL,
    telephone STRING(20) NOT NULL
) PRIMARY KEY (owner_id);

CREATE TABLE pets (
    owner_id STRING(36) NOT NULL,
    pet_id STRING(36) NOT NULL,
    name STRING(128) NOT NULL,
    birth_date DATE NOT NULL,
    type STRING(16) NOT NULL
) PRIMARY KEY (owner_id, pet_id),
  INTERLEAVE IN PARENT owners ON DELETE CASCADE;

CREATE TABLE vets (
    vet_id STRING(36) NOT NULL,
    first_name STRING(128) NOT NULL,
    last_name STRING(128) NOT NULL,
    specialties ARRAY<STRING(32)> NOT NULL
) PRIMARY KEY (vet_id);

CREATE INDEX vets_by_last_name ON vets(last_name);

CREATE TABLE visits (
    owner_id STRING(36) NOT NULL,
    pet_id STRING(36) NOT NULL,
    visit_id STRING(36) NOT NULL,
    date DATE NOT NULL,
    description STRING(MAX) NOT NULL
) PRIMARY KEY (owner_id, pet_id, visit_id),
  INTERLEAVE IN PARENT pets ON DELETE CASCADE;

