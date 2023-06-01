create table medical_record
(
    id uuid default uuid_generate_v4(),
    animal_id UUID,
    primary key (id)

);