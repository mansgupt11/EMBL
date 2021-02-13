package com.ebi.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonResponse {

  private final String firstName;
  private final String lastName;
  private final String favoriteColour;
  private final Integer age;
  private final String personId;

  @JsonCreator
  public PersonResponse(
      @JsonProperty("person_id") String personId,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName,
      @JsonProperty("favorite_colour") String favoriteColour,
      @JsonProperty("age") Integer age) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.favoriteColour = favoriteColour;
    this.age = age;
    this.personId = personId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFavoriteColour() {
    return favoriteColour;
  }

  public Integer getAge() {
    return age;
  }

  public String getPersonId() {
    return personId;
  }
}
