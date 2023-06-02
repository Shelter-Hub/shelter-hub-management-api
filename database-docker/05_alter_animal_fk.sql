alter table animal add constraint fk_medical_record_id foreign key(medical_record_id)
    references medical_record(id);