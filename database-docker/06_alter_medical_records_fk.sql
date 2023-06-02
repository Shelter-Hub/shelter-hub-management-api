alter table medical_record add constraint fk_animal_id foreign key(animal_id)
    references animal(id);