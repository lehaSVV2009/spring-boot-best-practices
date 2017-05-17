package com.kadet.best.book;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrudErrorsRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void should_return_400_when_required_entity_has_no_body() throws Exception {
    // when
    final ResultActions result = mockMvc.perform(post("/books"));

    // then
    result.andExpect(status().isBadRequest());
  }

  @Test
  @Ignore("404 vs http://docs.spring.io/spring-data/rest/docs/2.2.0.M1/reference/htmlsingle/#repository-resources.collection-resource")
  public void should_return_405_when_http_method_is_not_allowed() throws Exception {
    // when
    final ResultActions result = mockMvc.perform(put("/books").content("123"));

    // then
    result.andExpect(status().isMethodNotAllowed());
  }

  @Test
  @Ignore("Handle HttpMessageNotReadableException")
  public void should_return_415_when_media_type_is_unsupported() throws Exception {
    // when
    final ResultActions result = mockMvc.perform(post("/books")
        .contentType(MediaType.APPLICATION_XML)
        .content("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><name>bla</name>"));

    // then
    result.andExpect(status().isUnsupportedMediaType());
  }
}
