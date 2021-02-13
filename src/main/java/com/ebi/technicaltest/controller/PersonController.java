package com.ebi.technicaltest.controller;

import com.ebi.technicaltest.exception.NotFoundException;
import com.ebi.technicaltest.model.PersonModelAssembler;
import com.ebi.technicaltest.model.PersonRequest;
import com.ebi.technicaltest.model.PersonResponse;
import com.ebi.technicaltest.model.PersonResponseModel;
import com.ebi.technicaltest.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class PersonController {

  private final PersonService personService;
  private final PersonModelAssembler personModelAssembler;
  private final PagedResourcesAssembler<PersonResponse> pagedResourcesAssembler;

  @Autowired
  public PersonController(
      PersonService personService,
      PersonModelAssembler personModelAssembler,
      PagedResourcesAssembler<PersonResponse> pagedResourcesAssembler) {
    this.personService = personService;
    this.personModelAssembler = personModelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @ApiOperation("Create a Person")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
  })
  @PostMapping("/persons")
  public ResponseEntity createPerson(@RequestBody @Valid  PersonRequest personRequest) {
    PersonController.log.info("Request : {}", personRequest);
    personService.save(personRequest.toPersonModel());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation("Update a Person")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
    @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found")
  })
  @PatchMapping(value = "/persons/{person_id}")
  public ResponseEntity updatePerson(
      @ApiParam(value = "person_id", required = true) @PathVariable("person_id")  @NotBlank
          final String personId,
      @RequestBody final PersonRequest personRequest)
      throws UnsupportedEncodingException, NotFoundException {

    final String encodedType = URLEncoder.encode(personId, StandardCharsets.UTF_8.toString());
    personService.update(personRequest, encodedType);
    final URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.status(HttpStatus.ACCEPTED).location(location).body("Person Created");
  }

  @ApiOperation("Delete a Person")
  @ApiResponses({
    @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
    @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
    @io.swagger.annotations.ApiResponse(code = 204, message = "No Content")
  })
  @DeleteMapping(value = "/persons/{person_id}")
  public ResponseEntity deletePerson(
      @ApiParam(value = "person_id", required = true) @PathVariable("person_id")  @NotBlank
          final String personId)
      throws UnsupportedEncodingException {
    final String encodedType = URLEncoder.encode(personId, StandardCharsets.UTF_8.toString());
    personService.delete(encodedType);
    final URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
    return ResponseEntity.status(HttpStatus.ACCEPTED).location(location).build();
  }

  @GetMapping(value = "/persons")
  @ResponseStatus(value = HttpStatus.OK)
  @ApiOperation(value = "Retrieve Persons")
  @ApiResponse(code = 200, message = "OK")
  public ResponseEntity<PagedModel<PersonResponseModel>> getAllPersons(
      @PageableDefault final Pageable pageable) {
    PersonController.log.info("Retrieve all persons");
    final Page<PersonResponse> items = personService.getAllPerson(pageable);
    final PagedModel<PersonResponseModel> collModel =
        pagedResourcesAssembler.toModel(items, personModelAssembler);

    return new ResponseEntity(collModel, HttpStatus.OK);
  }

  @GetMapping(value = "/persons/{person_id}", produces = APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  @ApiOperation("Retrieve Persons with personId")
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 404, message = "Not Found")
  })
  public ResponseEntity<PersonResponse> getPersonWithId(
          @ApiParam(value = "personId of type String", required = true)
          @PathVariable("person_id") String personId) {
    log.info("Retrieve Person with Id: {} START", personId);
    return new ResponseEntity<>(
            personService.getPersonWithId(personId), HttpStatus.OK
    );
  }


}
