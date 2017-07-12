package com.kadet.best.book;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BooksCreateRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;


  @Test
  public void should_create_book() throws Exception {
    //
    // 1. Create book
    //

    // given
    val bookToCreate = new Book()
        .setName("Sims 3")
        .setDescription("Game for girls");

    // when
    final ResultActions createBookResult = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    createBookResult.andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

    val createdBook = objectMapper.readValue(
        createBookResult.andReturn().getResponse().getContentAsByteArray(),
        Book.class
    );
    assertThat(createdBook.getId()).isNotNull();
    assertThat(createdBook.getName()).isEqualTo("Sims 3");
    assertThat(createdBook.getDescription()).isEqualTo("Game for girls");

    //
    // 2. Delete book
    //

    deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
  }

  @Test
  public void should_create_book_with_only_name() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = new Book()
        .setName("Sims 3");

    // when
    final ResultActions createBookResult = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    createBookResult.andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

    val createdBook = objectMapper.readValue(
        createBookResult.andReturn().getResponse().getContentAsByteArray(),
        Book.class
    );
    assertThat(createdBook.getId()).isNotNull();
    assertThat(createdBook.getName()).isEqualTo("Sims 3");
    assertThat(createdBook.getDescription()).isNull();

    // 2. Delete book
    deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
  }

  @Test
  public void should_create_book_with_only_description() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = new Book()
        .setDescription("My description");

    // when
    final ResultActions createBookResult = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    createBookResult.andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

    val createdBook = objectMapper.readValue(
        createBookResult.andReturn().getResponse().getContentAsByteArray(),
        Book.class
    );
    assertThat(createdBook.getId()).isNotNull();
    assertThat(createdBook.getName()).isNull();
    assertThat(createdBook.getDescription()).isEqualTo("My description");

    // 2. Delete book
    deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
  }

  @Test
  public void should_create_book_with_no_values_when_id_is_defined() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = new Book()
        .setId("534687435");

    // when
    final ResultActions createBookResult = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    createBookResult.andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

    val createdBook = objectMapper.readValue(
        createBookResult.andReturn().getResponse().getContentAsByteArray(),
        Book.class
    );
    assertThat(createdBook.getId()).isNotNull();
    assertThat(createdBook.getId()).isNotEqualTo("534687435");
    assertThat(createdBook.getName()).isNull();
    assertThat(createdBook.getDescription()).isNull();

    // 2. Delete book
    deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
  }

  @Test
  public void should_create_book_with_no_values_when_no_fields_defined() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = new Book();

    // when
    final ResultActions createBookResult = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    createBookResult.andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

    val createdBook = objectMapper.readValue(
        createBookResult.andReturn().getResponse().getContentAsByteArray(),
        Book.class
    );
    assertThat(createdBook.getId()).isNotNull();
    assertThat(createdBook.getName()).isNull();
    assertThat(createdBook.getDescription()).isNull();

    // 2. Delete book
    deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
  }

  @Test
  public void should_create_book_with_unknown_fields() throws Exception {
    // 1. Create book
    // given
    val unknownFields = ImmutableMap.of("unknown_field", "123");

    // when
    final ResultActions createBookResult = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(unknownFields)));

    // then
    createBookResult.andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

    val createdBook = objectMapper.readValue(
        createBookResult.andReturn().getResponse().getContentAsByteArray(),
        Book.class
    );
    assertThat(createdBook.getId()).isNotNull();
    assertThat(createdBook.getName()).isNull();
    assertThat(createdBook.getDescription()).isNull();

    // 2. Delete book
    deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
  }

  private void deleteBook(final String bookLocation) throws Exception {
    mockMvc.perform(delete(bookLocation));
  }
}
