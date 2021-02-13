package com.ebi.technicaltest.model;

import com.ebi.technicaltest.controller.PersonController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Configuration
public class PersonModelAssembler
    extends RepresentationModelAssemblerSupport<PersonResponse, PersonResponseModel> {

  public PersonModelAssembler() {
    super(PersonController.class, PersonResponseModel.class);
  }

  @Override
  public PersonResponseModel toModel(PersonResponse entity) {
    PersonResponseModel personResponseModel = new PersonResponseModel();
    personResponseModel.setPersonId(entity.getPersonId());
    personResponseModel.setFirstName(entity.getFirstName());
    personResponseModel.setLastName(entity.getLastName());
    personResponseModel.setFavoriteColour(entity.getFavoriteColour());
    personResponseModel.setAge(entity.getAge());
    personResponseModel.add(
        linkTo(methodOn(PersonController.class).getPersonWithId(entity.getPersonId()))
            .withSelfRel());
    return personResponseModel;
  }
}
