package com.ebi.technicaltest.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class PersonModel {

    @Id
    private String id;
    private String first_name;
    private String last_name;
    private int age;
    private String favorite_colour;
}
