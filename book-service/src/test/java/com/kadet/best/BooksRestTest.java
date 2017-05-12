package com.kadet.best;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.kadet.best.repository.BookRepository;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BooksRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ObjectMapper objectMapper;


  @Before
  public void setUp() throws Exception {
    bookRepository.deleteAll();
  }

  @Test
  public void should_list_books() throws Exception {
    // when
    final ResultActions result = mockMvc.perform(get("/books"));

    // then
    result.andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.books").exists());
  }

  @Test
  public void should_create_book() throws Exception {
    // given
    val bookToCreate = ImmutableMap.of(
        "name", "Sims 3",
        "description", "Game for girls"
    );

    // when
    final ResultActions result = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    result.andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("books/")));
  }

  @Test
  public void should_get_book() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = ImmutableMap.of(
        "name", "Sims 3",
        "description", "Game for girls"
    );

    // when
    final ResultActions resultOfCreate = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    resultOfCreate.andExpect(status().isCreated());

    // 2. Get book
    // given
    String location = resultOfCreate.andReturn().getResponse().getHeader("Location");

    // when
    final ResultActions resultOfGet = mockMvc.perform(get(location));

    // then
    resultOfGet.andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Sims 3"))
        .andExpect(jsonPath("$.description").value("Game for girls"));
  }

  @Test
  public void should_update_book() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = ImmutableMap.of(
        "name", "Sims 3",
        "description", "Game for girls"
    );

    // when
    final ResultActions resultOfCreate = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    resultOfCreate.andExpect(status().isCreated());

    // 2. Update book
    // given
    val location = resultOfCreate.andReturn().getResponse().getHeader("Location");
    val newProduct = ImmutableMap.of(
        "name", "Sims 4",
        "description", "Game for girls and boys"
    );

    // when
    final ResultActions resultOfUpdate = mockMvc.perform(put(location)
        .content(objectMapper.writeValueAsString(newProduct)));

    // then
    resultOfUpdate.andExpect(status().isNoContent());

    // 3. Get updated book
    // when
    final ResultActions resultOfGet = mockMvc.perform(get(location));

    // then
    resultOfGet.andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Sims 4"))
        .andExpect(jsonPath("$.description").value("Game for girls and boys"));
  }

  @Test
  public void should_partially_update_book() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = ImmutableMap.of(
        "name", "Sims 3",
        "description", "Game for girls"
    );

    // when
    final ResultActions resultOfCreate = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    resultOfCreate.andExpect(status().isCreated());

    // 2. Partial update book
    // given
    val location = resultOfCreate.andReturn().getResponse().getHeader("Location");
    val newProduct = ImmutableMap.of(
        "name", "Sims 4"
    );

    // when
    final ResultActions resultOfUpdate = mockMvc.perform(patch(location)
        .content(objectMapper.writeValueAsString(newProduct)));

    // then
    resultOfUpdate.andExpect(status().isNoContent());

    // 3. Get updated book
    // when
    final ResultActions resultOfGet = mockMvc.perform(get(location));

    // then
    resultOfGet.andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Sims 4"))
        .andExpect(jsonPath("$.description").value("Game for girls"));
  }

  @Test
  public void should_delete_book() throws Exception {
    // 1. Create book
    // given
    val bookToCreate = ImmutableMap.of(
        "name", "Sims 3",
        "description", "Game for girls"
    );

    // when
    final ResultActions resultOfCreate = mockMvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(bookToCreate)));

    // then
    resultOfCreate.andExpect(status().isCreated());

    // 2. Delete book
    // given
    val location = resultOfCreate.andReturn().getResponse().getHeader("Location");

    // when
    final ResultActions resultOfDelete = mockMvc.perform(delete(location));

    // then
    resultOfDelete.andExpect(status().isNoContent());

    // 3. Try to get deleted book
    // when
    final ResultActions resultOfGet = mockMvc.perform(get(location));

    // then
    resultOfGet.andExpect(status().isNotFound());
  }
}
