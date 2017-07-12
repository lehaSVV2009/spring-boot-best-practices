package com.kadet.best.book;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
public class BooksUpdateRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void should_update_only_name_book() throws Exception{

        //
        //1. create book item
        //
        val bookToCreate = new Book()
                .setName("Sims 2")
                .setDescription("Game for girls");

        //
        //2. send created boor item to database
        //
        final ResultActions createBookResult = mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(bookToCreate)));

        //
        //3. confirm creation
        //
        createBookResult.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

        val createdBook = objectMapper.readValue(
                createBookResult.andReturn().getResponse().getContentAsByteArray(),
                Book.class
        );

        //
        //4. assert right creation
        //
        assertThat(createdBook.getId()).isNotNull();
        assertThat(createdBook.getName()).isEqualTo("Sims 2");
        assertThat(createdBook.getDescription()).isEqualTo("Game for girls");


        //
        //5. change name book
        //
        bookToCreate.setName("Sims 3");

        //
        //6. send patch book item to database
        //
        final ResultActions resultUpdateBook = mockMvc.perform(patch("/books/"+createdBook.getId())
                .content(objectMapper.writeValueAsString(bookToCreate)));

        //
        //7. confirm patch
        //
        resultUpdateBook.andExpect(status().isOk());
        //this unneeded, because we don't get this header
        //.andExpect(header().string(HttpHeaders.LOCATION, containsString("books/"+createdBook.getId())));

        val updatedBook = objectMapper.readValue(resultUpdateBook.andReturn().getResponse().getContentAsByteArray(),
                Book.class);

        //
        //8. assert right patch
        assertThat(updatedBook.getId()).isEqualTo(createdBook.getId());
        assertThat(updatedBook.getName()).isEqualTo("Sims 3");
        assertThat(updatedBook.getDescription()).isEqualTo("Game for girls");

        //
        //9. Delete book
        //
        deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
    }


    @Test
    public void should_update_only_description() throws Exception{

        //
        //1. create book item
        //
        val bookToCreate = new Book()
                .setName("Sims 2")
                .setDescription("Game for girls");

        //
        //2. send created boor item to database
        //
        final ResultActions createBookResult = mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(bookToCreate)));

        //
        //3. confirm creation
        //
        createBookResult.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

        val createdBook = objectMapper.readValue(
                createBookResult.andReturn().getResponse().getContentAsByteArray(),
                Book.class
        );

        //
        //4. assert right creation
        //
        assertThat(createdBook.getId()).isNotNull();
        assertThat(createdBook.getName()).isEqualTo("Sims 2");
        assertThat(createdBook.getDescription()).isEqualTo("Game for girls");

        //
        //5. change name description
        //
        bookToCreate.setDescription("Game not for only girls");

        //
        //6. send patch book item to database
        //
        final ResultActions resultUpdateBook = mockMvc.perform(patch("/books/"+createdBook.getId())
                .content(objectMapper.writeValueAsString(bookToCreate)));

        //
        //7. confirm patch
        //
        resultUpdateBook.andExpect(status().isOk());
        //this unneeded, because we don't get this header
        //.andExpect(header().string(HttpHeaders.LOCATION, containsString("books/"+createdBook.getId())));

        val updatedBook = objectMapper.readValue(resultUpdateBook.andReturn().getResponse().getContentAsByteArray(),
                Book.class);

        //
        //8. assert right patch
        assertThat(updatedBook.getId()).isEqualTo(createdBook.getId());
        assertThat(updatedBook.getName()).isEqualTo("Sims 2");
        assertThat(updatedBook.getDescription()).isEqualTo("Game not for only girls");

        //
        //9. Delete book
        //
        deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void do_not_should_update_empty_book() throws Exception{
        //
        //1. create book item
        //
        val bookToCreate = new Book()
                .setName("Sims 2")
                .setDescription("Game for girls");

        //
        //2. send created boor item to database
        //
        final ResultActions createBookResult = mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(bookToCreate)));

        //
        //3. confirm creation
        //
        createBookResult.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("books/")));

        val createdBook = objectMapper.readValue(
                createBookResult.andReturn().getResponse().getContentAsByteArray(),
                Book.class
        );

        //
        //4. assert right creation
        //
        assertThat(createdBook.getId()).isNotNull();
        assertThat(createdBook.getName()).isEqualTo("Sims 2");
        assertThat(createdBook.getDescription()).isEqualTo("Game for girls");

        //
        //5. create empty book
        //
        val bookEmpty = new Book();

        //
        //6. send empty book
        //
        final ResultActions emptyBookUpdateResult = mockMvc.perform(patch("/books/"+createdBook.getId())
                .content(objectMapper.writeValueAsString(bookEmpty)));

        //
        //7. confirm sending
        //
        emptyBookUpdateResult.andExpect(status().isOk());

        val resultUpdate = objectMapper.readValue(
                emptyBookUpdateResult.andReturn().getResponse().getContentAsByteArray(),
                Book.class
        );

        //
        //8. assert right behaviour
        //
        assertThat(resultUpdate.getId()).isNotNull();
        assertThat(resultUpdate.getName()).isEqualTo("Sims 2");
        assertThat(resultUpdate.getDescription()).isEqualTo("Game for girls");

        //
        //9. Delete book
        //
        deleteBook(createBookResult.andReturn().getResponse().getHeader(HttpHeaders.LOCATION));
    }



    private void deleteBook(final String bookLocation) throws Exception {
        mockMvc.perform(delete(bookLocation));
    }

}
