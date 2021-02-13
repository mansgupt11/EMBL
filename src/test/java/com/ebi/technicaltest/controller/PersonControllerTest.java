package com.ebi.technicaltest.controller;

import com.ebi.technicaltest.model.PersonModel;
import com.ebi.technicaltest.model.PersonModelAssembler;
import com.ebi.technicaltest.model.PersonRequest;
import com.ebi.technicaltest.model.PersonResponse;
import com.ebi.technicaltest.repository.PersonRepository;
import com.ebi.technicaltest.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PersonControllerTest {

  private static final String IDENTITY_HEADER = "Bearer";
  private static final String personId = UUID.randomUUID().toString();
  private MockMvc mockMvc;
  private PersonController controller;
  private PersonResponse personResponse;
  private PersonService personService;
  @MockBean private PersonModelAssembler personModelAssembler;
  @MockBean private PagedResourcesAssembler<PersonResponse> pagedResourcesAssembler;
  @MockBean private PersonRepository personRepository;
  @MockBean private PersonModel personModel;

  @Before
  public void setUp() {
    personService = new PersonService(personRepository);
    controller = new PersonController(personService, personModelAssembler, pagedResourcesAssembler);
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .build();
    personResponse = new PersonResponse(personId, "TestUser", "lastName", "black", 29);
  }

  @Test
  public void shouldReturnDefaultPagination() throws Exception {

    assertThat(
        mockMvc
            .perform(servletRequestBuilder("/persons?page=-1&size=-10"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn());
  }

  @Test
  public void shouldReturnCustomPagination() throws Exception {

    assertThat(
        mockMvc
            .perform(servletRequestBuilder("/persons?page=1&size=10"))
            .andExpect(MockMvcResultMatchers.status().isOk()));
  }

  @Test
  public void shouldReturnPersons() throws Exception {
    assertThat(
        mockMvc
            .perform(servletRequestBuilder("/persons"))
            .andExpect(MockMvcResultMatchers.status().isOk()));
  }

  @Test
  public void givenGetByPersonId_whenCorrectPersonId_thenReturnSuccess() throws Exception {
    when(personRepository.findById(personId)).thenReturn(Optional.of(personModel));

    assertThat(
        mockMvc
            .perform(servletRequestBuilder("/persons/" + personId))
            .andExpect(MockMvcResultMatchers.status().isOk()));
  }

  @Test
  public void givenUpdatePerson_whenWrongPersonId_thenThrowException() {
    assertThatThrownBy(
            () ->
                mockMvc.perform(
                    servletRequestPatchBuilder("/persons/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)))
        .isInstanceOf(Exception.class);
  }

  @Test
  public void givenUpdatePerson_whenCorrectPersonId_thenReturnAccepted() throws Exception {
    when(personRepository.findById(personId)).thenReturn(Optional.of(personModel));

    assertThat(
        mockMvc
            .perform(
                servletRequestPatchBuilder("/persons/" + personId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted()));
  }

  private PersonRequest getPersonRequest() {
    PersonRequest personRequest = new PersonRequest();
    personRequest.setAge(20);
    personRequest.setFirst_name("testuser");
    personRequest.setLast_name("lastname");
    personRequest.setFavorite_colour("red");
    return personRequest;
  }

  public String fromResource(String resource) {
    try {
      return IOUtils.toString(
          Objects.requireNonNull(
              PersonControllerTest.class.getClassLoader().getResourceAsStream(resource)),
          StandardCharsets.UTF_8);
    } catch (IOException ignore) {
      return null;
    }
  }

  private MockHttpServletRequestBuilder servletRequestBuilder(String url) {
    return MockMvcRequestBuilders.get(url);
  }

  private MockHttpServletRequestBuilder servletRequestPatchBuilder(String url)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(getPersonRequest());
    return MockMvcRequestBuilders.patch(url).content(requestJson);
  }
}
