package com.david.restapi.controller;


import com.david.restapi.models.AddResponse;
import com.david.restapi.models.Library;
import com.david.restapi.repository.LibraryRepository;
import com.david.restapi.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryService libraryService;

    @Autowired
    AddResponse addResponse;

    @PostMapping("/addBook")
        public ResponseEntity<AddResponse> addBook(@RequestBody Library library) {

            String id = libraryService.buildUniqueID(library.getIsbn(), library.getAisle());
            addResponse = new AddResponse();

            if (!libraryService.checkBookAlreadyExist(id)) {


                library.setId(id);

                libraryRepository.save(library);

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("uniqueID", id);

                addResponse.setId(library.getIsbn() + library.getAisle());
                addResponse.setMsg("Book saved Successfully");

                return new ResponseEntity<>(addResponse, httpHeaders, HttpStatus.CREATED);

        } else {

            addResponse.setMsg("Book Already Present");
            addResponse.setId(id);
            return new ResponseEntity<>(addResponse,HttpStatus.ACCEPTED);
        }


    }

//    Use ResponseEntity when:
//    You want to control:
//
//    HTTP status codes (201, 202, 204, etc.)
//
//    Headers (like Location, Authorization, etc.)
//
//    Custom error responses
//
//    You're building a RESTful API that needs more control.
//
//            ✖ Can skip ResponseEntity when:
//    You're just returning data, and the default 200 OK is fine.
//
//    No headers or special status codes are needed.
//
//    Simplicity is preferred (e.g., small internal projects).


    @GetMapping("/getBooks/{id}")
    public Library getBookByID(@PathVariable("id") String id) {
        return libraryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }




    @GetMapping("/getBooks/author")
    public List<Library> getBooksByAuthor(@RequestParam(value = "authorName") String authorName){

        return libraryRepository.findAllByName(authorName);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable(value = "id")String id , @RequestBody Library library){

        Library existingBook = libraryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        existingBook.setAisle(library.getAisle());
        existingBook.setAuthor(library.getAuthor());
        existingBook.setBook_name(library.getBook_name());

        libraryRepository.save(existingBook);

        return new ResponseEntity<>(existingBook,HttpStatus.OK);

    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBookByID(@PathVariable String id) {
        Optional<Library> book = libraryRepository.findById(id);

        if (book.isPresent()) {
            libraryRepository.deleteById(id);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/allBooks")
    public ResponseEntity<List<Library>> getAllBooks() {
        List<Library> allBooks = libraryRepository.findAll();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }






}
