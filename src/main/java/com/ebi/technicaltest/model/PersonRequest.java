package com.ebi.technicaltest.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.ebi.technicaltest.validator.AliasValidator.*;

@Data
public class PersonRequest {

  @NotEmpty
  @Pattern(regexp = NON_WHITESPACE_PATTERN, message = NON_SPACE_MESSAGE)
  private String first_name;
  @NotEmpty
  @Pattern(regexp = NON_WHITESPACE_PATTERN, message = NON_SPACE_MESSAGE)
  private String last_name;
  @NotNull
  private Integer age;
  @Pattern(regexp = NON_WHITESPACE_PATTERN, message = NON_SPACE_MESSAGE)
  @Pattern(regexp = ALIAS_PATTERN, message = ALIAS_MESSAGE)
  private String favorite_colour;

  public PersonModel toPersonModel() {
    return PersonModel.builder()
        .first_name(this.first_name)
        .last_name(this.last_name)
        .age(this.age)
        .favorite_colour(this.favorite_colour)
        .build();
  }

}
