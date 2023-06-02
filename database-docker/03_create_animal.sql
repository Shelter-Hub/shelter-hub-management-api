create table animal
(
    id uuid default uuid_generate_v4(),
    name text,
    age smallint,
    medical_record_id UUID,
    animal_type text,
    primary key (id)
);