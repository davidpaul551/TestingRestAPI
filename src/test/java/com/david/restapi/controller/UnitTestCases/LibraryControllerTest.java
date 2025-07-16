package com.david.restapi.controller.UnitTestCases;

import com.david.restapi.controller.LibraryController;
import com.david.restapi.models.AddResponse;
import com.david.restapi.models.Library;
import com.david.restapi.repository.LibraryRepository;
import com.david.restapi.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryControllerTest {


    @Mock
    LibraryService libraryService;

    @Mock
    LibraryRepository libraryRepository;

    @InjectMocks
    LibraryController libraryController;

    @Test
    void testAddBookWhenBookDoesNotExist(){

        Library library = new Library();
        library.setAisle(23);
        library.setIsbn("abc");
        String id = "abc23";

        when(libraryService.buildUniqueID("abc",23)).thenReturn(id);
        when(libraryService.checkBookAlreadyExist(id)).thenReturn(false);
        when(libraryRepository.save(any(Library.class))).thenReturn(library);

        ResponseEntity<AddResponse> response = libraryController.addBook(library);

        AddResponse responseBody = response.getBody();
        assertEquals(HttpStatus.CREATED , response.getStatusCode());
        assertEquals("Book saved Successfully",responseBody.getMsg());
        assertEquals(id, response.getBody().getId());
        assertEquals(id,response.getHeaders().getFirst("uniqueID"));


    }

    @Test
    void testAddBookWhenBookDoesExist(){

        Library library = new Library();
        library.setAisle(23);
        library.setIsbn("abc");
        String id = "abc23";
        library.setAuthor("magnus");
        library.setBook_name("opus");

        when(libraryService.buildUniqueID("abc",23)).thenReturn(id);
        when(libraryService.checkBookAlreadyExist(id)).thenReturn(true);

        ResponseEntity<AddResponse> response = libraryController.addBook(library);

        assertEquals("Book Already Present",response.getBody().getMsg());
        assertEquals(id,response.getBody().getId());
        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
    }

    @Test
    void testGetBookByID(){

        Library library = new Library();
        library.setAisle(23);
        library.setIsbn("abc");
        String id = "abc23";
        library.setAuthor("magnus");
        library.setBook_name("opus");

        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));

        Library bookByID = libraryController.getBookByID(id);
        assertEquals(library.getId(), bookByID.getId());
        assertEquals(library.getAuthor() , bookByID.getAuthor());

    }

    @Test
    void testGetBookByIDWhenBookNotExist(){
        Library library = new Library();
        library.setAisle(23);
        library.setIsbn("abc");
        String id = "abc23";

        when(libraryRepository.findById(id)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            libraryController.getBookByID(id);
        });
        assertEquals(HttpStatus.NOT_FOUND , exception.getStatusCode());

    }

    @Test
    void testGetBooksByAuthor(){

        Library library = new Library();
        library.setAisle(23);
        library.setIsbn("abc");
        library.setAuthor("magnus");
        library.setBook_name("opus");

        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library);

        when(libraryRepository.findAllByName("magnus")).thenReturn(libraryList);
        List<Library> booksByName = libraryController.getBooksByAuthor("magnus");
        assertEquals("magnus" ,booksByName.get(0).getAuthor() );
    }

    @Test
    void testUpdateBook(){

        Library existingBook  = new Library();
        existingBook .setAisle(23);
        existingBook .setIsbn("abc");
        String id = "abc23";
        existingBook .setAuthor("magnus");
        existingBook .setBook_name("opus");


        Library updatedBook = new Library();
        updatedBook.setAisle(99);
        updatedBook.setAuthor("Updated Author");
        updatedBook.setBook_name("Updated Book");

        when(libraryRepository.findById(id)).thenReturn(Optional.of(existingBook));

        when(libraryRepository.save(any(Library.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ResponseEntity<Library> response = libraryController.updateBook(id , updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Author", Objects.requireNonNull(response.getBody()).getAuthor());
        assertEquals("Updated Book", response.getBody().getBook_name());
        assertEquals(99, response.getBody().getAisle());

    }

    @Test
    void testDeleteBookByID(){

        Library library = new Library();
        library.setAisle(23);
        library.setIsbn("abc");
        String id = "abc23";
        library.setId(id);
        library.setAuthor("magnus");
        library.setBook_name("opus");

        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));

        ResponseEntity<String> response = libraryController.deleteBookByID(id);

        verify(libraryRepository).deleteById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book deleted successfully", response.getBody());
    }

    @Test
    void testGetAllBooks(){

        Library book1 = new Library();
        book1.setId("abc123");
        book1.setIsbn("abc");
        book1.setAisle(123);
        book1.setAuthor("A1");
        book1.setBook_name("B1");

        Library book2 = new Library();
        book2.setId("xyz456");
        book2.setIsbn("xyz");
        book2.setAisle(456);
        book2.setAuthor("A2");
        book2.setBook_name("B2");

        List<Library> books = Arrays.asList(book1, book2);

        when(libraryRepository.findAll()).thenReturn(books);

        ResponseEntity<List<Library>> response = libraryController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("B1", response.getBody().get(0).getBook_name());
        assertEquals("B2", response.getBody().get(1).getBook_name());
        verify(libraryRepository).findAll();
    }



}
