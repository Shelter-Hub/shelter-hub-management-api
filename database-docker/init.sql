CREATE DATABASE postgres;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table animal
(
    id uuid default uuid_generate_v4(),
    name text,
    age smallint,
    medical_record_id UUID,
    animal_type text,
    primary key (id)
);

create table medical_record
(
    id uuid default uuid_generate_v4(),
    animal_id UUID,
    primary key (id)

);

alter table animal add constraint fk_medical_record_id foreign key(medical_record_id)
    references medical_record(id);

alter table medical_record add constraint fk_animal_id foreign key(animal_id)
    references animal(id);