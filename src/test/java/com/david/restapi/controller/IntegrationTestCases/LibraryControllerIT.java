package com.david.restapi.controller.IntegrationTestCases;


import com.fasterxml.jackson.core.type.TypeReference;

import com.david.restapi.models.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LibraryControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void testGetBookByIDShouldReturnBookWhenExists() throws Exception {
        mockMvc.perform(get("/getBooks/xyz456")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("xyz456"))
                .andExpect(jsonPath("$.book_name").value("Java Concurrency"))
                .andExpect(jsonPath("$.isbn").value("xyz"))
                .andExpect(jsonPath("$.aisle").value(456))
                .andExpect(jsonPath("$.author").value("Brian Goetz"));

    }

    @Test
    void testGetBookByIdShouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(get("/getBooks/notexist"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testGetBooksByAuthorShouldReturnBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/getBooks/author")
                                .param("authorName" , "Brian Goetz")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        System.out.println(result);
        String jsonResponse = result.getResponse().getContentAsString();

        System.out.println(jsonResponse);

        //JSON array into List<Library>

        ObjectMapper mapper = new ObjectMapper();
        List<Library> books = mapper.readValue(jsonResponse, new TypeReference<List<Library>>() {});
        assertEquals(2,books.size());
        assertEquals("Brian Goetz", books.get(0).getAuthor());

    }



    @Test
    void testUpdateBookShouldUpdateBook() throws Exception {

        String bookID = "def789";

        Library updateBook = new Library();
        updateBook.setAuthor("Cameron");

        mockMvc.perform(put("/updateBook/{id}",bookID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Cameron"))
                .andExpect(jsonPath("$.id").value(bookID));
    }


    @Test
    void testDeleteBookByID() throws Exception {

        mockMvc.perform(delete("/deleteBook/def789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));

    }









    @Test
    void testGetAllBooks() throws Exception {

        MvcResult result = mockMvc.perform(get("/allBooks"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        System.out.println(jsonResult);
        ObjectMapper mapper = new ObjectMapper();
        List<Library> allBooks = mapper.readValue(jsonResult, new TypeReference<List<Library>>() {});
        assertEquals(3,allBooks.size());
    }



}
