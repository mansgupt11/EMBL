package com.ebi.technicaltest.service;

import com.ebi.technicaltest.exception.NotFoundException;
import com.ebi.technicaltest.model.PersonModel;
import com.ebi.technicaltest.model.PersonRequest;
import com.ebi.technicaltest.model.PersonResponse;
import com.ebi.technicaltest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(final PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public PersonModel save(final PersonModel personModel) {
    return personRepository.save(personModel);
  }


  public void update(final PersonRequest personRequest, final String personId) throws NotFoundException{

    PersonModel personModel = getPersonById(personId);

    if (personRequest.getFirst_name() != null && !personRequest.getFirst_name().isEmpty()) {
      personModel.setFirst_name(personRequest.getFirst_name());
    }

    if (personRequest.getLast_name() != null && !personRequest.getLast_name().isEmpty()) {
      personModel.setLast_name(personRequest.getLast_name());
    }

    if (personRequest.getAge() != null) {
      personModel.setAge(personRequest.getAge());
    }

    if (personRequest.getFavorite_colour() != null
        && !personRequest.getFavorite_colour().isEmpty()) {
      personModel.setFavorite_colour(personRequest.getFavorite_colour());
    }

    this.save(personModel);
  }

  public void delete(final String personId) {
    personRepository.deleteById(personId);
  }

  public Page<PersonResponse> getAllPerson(final Pageable pageable) {

    List<PersonModel> templateDocuments = personRepository.findAll();
    List<PersonResponse> templateResponse =
        templateDocuments.stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .map(
                personModel ->
                    new PersonResponse(
                        personModel.getId(),
                        personModel.getFirst_name(),
                        personModel.getLast_name(),
                        personModel.getFavorite_colour(),
                        personModel.getAge()))
            .collect(Collectors.toList());
    return new PageImpl<>(templateResponse, pageable, getCount());
  }

  public PersonResponse getPersonWithId(final String personId) {

    PersonModel personModel = getPersonById(personId);

    PersonResponse personResponse =
        new PersonResponse(
            personModel.getId(),
            personModel.getFirst_name(),
            personModel.getLast_name(),
            personModel.getFavorite_colour(),
            personModel.getAge());

    return personResponse;
  }

  private long getCount() {
    return personRepository.count();
  }

  private PersonModel getPersonById(String personId) throws NotFoundException{

    Optional<PersonModel> personDocument = personRepository.findById(personId);

    if (!personDocument.isPresent()) {
      throw new NotFoundException(PersonModel.class.getSimpleName(), personId);
    }

    return personDocument.get();
  }
}
