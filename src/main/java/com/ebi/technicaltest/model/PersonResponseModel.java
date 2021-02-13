package com.ebi.technicaltest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;

public class PersonResponseModel extends RepresentationModel<PersonResponseModel> {

  @JsonProperty("first_name")
  @ApiModelProperty(value = "first_name", required = true)
  private String firstName;

  @JsonProperty("last_name")
  @ApiModelProperty(value = "last_name", required = true)
  private String lastName;

  @JsonProperty("favorite_colour")
  @ApiModelProperty(value = "favorite_colour")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String favoriteColour;

  @JsonProperty("age")
  @ApiModelProperty(value = "age", required = true)
  private Integer age;

  @JsonProperty("person_id")
  @ApiModelProperty(value = "person_id", required = true)
  private String personId;

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public void setFavoriteColour(final String favoriteColour) {
    this.favoriteColour = favoriteColour;
  }

  public void setAge(final Integer age) {
    this.age = age;
  }

  public void setPersonId(final String personId) {
    this.personId = personId;
  }
}
