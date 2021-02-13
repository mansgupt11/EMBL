package com.ebi.technicaltest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AliasValidator implements ConstraintValidator<Alias, String> {

  public static final String NON_WHITESPACE_PATTERN = "^[^\\s]*[^\\s]+$";
  public static final String NON_SPACE_MESSAGE = "[${formatter.format('%.40s', validatedValue)}] "
      + "must not contain any leading, trailing and inner whitespace(s)";

  public static final String ALIAS_PATTERN = "^[A-Za-z]+$";
  public static final String ALIAS_MESSAGE = "[${formatter.format('%.40s', validatedValue)}] "
      + "must only contain alphabets";

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    return true;
  }
}
