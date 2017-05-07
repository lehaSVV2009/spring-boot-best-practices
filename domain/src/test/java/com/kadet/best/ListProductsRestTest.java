package com.kadet.best;

import com.kadet.best.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ListProductsRestTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductRepository productRepository;

  @Before
  public void deleteAllBeforeTests() throws Exception {
    productRepository.deleteAll();
  }

  @Test
  public void shouldListProducts() throws Exception {

    mockMvc.perform(get("/products"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.products").exists());
  }

  @Test
  public void shouldCreateProduct() throws Exception {

    mockMvc.perform(post("/products")
        .content("{\"name\": \"Sims 3\", \"description\":\"Game for girls\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("products/")));
  }

  @Test
  public void shouldGetProduct() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/products")
        .content("{\"name\": \"Sims 3\", \"description\":\"Game for girls\"}"))
        .andExpect(status().isCreated())
        .andReturn();

    String location = mvcResult.getResponse().getHeader("Location");
    mockMvc.perform(get(location))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Sims 3"))
        .andExpect(jsonPath("$.description").value("Game for girls"));
  }

  @Test
  public void shouldUpdateProduct() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/products")
        .content("{\"name\": \"Sims 3\", \"description\":\"Game for girls\"}"))
        .andExpect(status().isCreated())
        .andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(put(location)
        .content("{\"name\": \"Sims 4\", \"description\":\"Game for girls\"}"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get(location))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Sims 4"))
        .andExpect(jsonPath("$.description").value("Game for girls"));
  }

  @Test
  public void shouldPartiallyUpdateProduct() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/products")
        .content("{\"name\": \"Sims 3\", \"description\":\"Game for girls\"}"))
        .andExpect(status().isCreated())
        .andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(patch(location)
        .content("{\"name\": \"Sims 4\"}"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get(location))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Sims 4"))
        .andExpect(jsonPath("$.description").value("Game for girls"));
  }

  @Test
  public void shouldDeleteProduct() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/products")
        .content("{ \"name\": \"Sims 4\", \"description\":\"Game for girls\"}"))
        .andExpect(status().isCreated())
        .andReturn();

    String location = mvcResult.getResponse().getHeader("Location");
    mockMvc.perform(delete(location)).andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isNotFound());
  }
}
