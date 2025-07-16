package com.david.restapi.controller.IntegrationTestCases;

import com.david.restapi.models.AddResponse;
import com.david.restapi.models.Library;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryControllerRestTemplateIT {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testAddBookWithRestTemplate(){

        Library book = new Library();
        book.setId("a123");
        String id = "a123";
        book.setIsbn("a");
        book.setAisle(123);
        book.setAuthor("A1");
        book.setBook_name("B1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Library> payload = new HttpEntity<>(book ,headers);

        ResponseEntity<AddResponse> response = restTemplate.exchange("http://localhost:8080/addBook" , HttpMethod.POST ,payload  ,AddResponse.class);
        assertEquals("Book saved Successfully" , response.getBody().getMsg());
        assertEquals(id , response.getBody().getId());



    }

    @Test
    void testAddBookWhenBookExistWithRestTemplate(){
        Library book = new Library();
        book.setId("abc123");
        String id = "abc123";
        book.setIsbn("abc");
        book.setAisle(123);
        book.setAuthor("Craig Walls");
        book.setBook_name("Spring in Action");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Library> payload = new HttpEntity<>(book ,headers );

        ResponseEntity<AddResponse> response = restTemplate.exchange("http://localhost:8080/addBook" , HttpMethod.POST , payload , AddResponse.class);

        assertEquals("Book Already Present" , response.getBody().getMsg());
        assertEquals(id , response.getBody().getId());


    }





    @Test
    void testUpdateBookShouldUpdateBook2(){

        String bookID = "def789";

        Library updateBook = new Library();
        updateBook.setAuthor("Cameron");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Library> payload = new HttpEntity<>(updateBook,headers);

        ResponseEntity<Library> response = restTemplate.exchange("http://localhost:8080/updateBook/"+bookID , HttpMethod.PUT, payload , Library.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Library responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Cameron", responseBody.getAuthor());
        assertEquals(bookID, responseBody.getId());

    }

    @Test
    void testDeleteBook() {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/deleteBook/xyz456", HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted successfully", response.getBody());
    }


}
