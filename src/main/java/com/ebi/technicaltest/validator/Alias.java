package com.ebi.technicaltest.validator;

import static com.ebi.technicaltest.validator.AliasValidator.ALIAS_MESSAGE;
import static com.ebi.technicaltest.validator.AliasValidator.ALIAS_PATTERN;
import static com.ebi.technicaltest.validator.AliasValidator.NON_SPACE_MESSAGE;
import static com.ebi.technicaltest.validator.AliasValidator.NON_WHITESPACE_PATTERN;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Constraint(validatedBy = AliasValidator.class)
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@NotBlank
@Pattern(regexp = NON_WHITESPACE_PATTERN, message = NON_SPACE_MESSAGE)
@Pattern(regexp = ALIAS_PATTERN, message = ALIAS_MESSAGE)
public @interface Alias {

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
