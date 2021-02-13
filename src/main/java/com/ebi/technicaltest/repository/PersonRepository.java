package com.ebi.technicaltest.repository;

import com.ebi.technicaltest.model.PersonModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends MongoRepository<PersonModel,String> {
      @Override
      Optional<PersonModel> findById(String personId);

}
