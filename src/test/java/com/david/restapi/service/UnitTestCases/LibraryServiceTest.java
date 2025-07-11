package com.david.restapi.service.UnitTestCases;

import com.david.restapi.models.Library;
import com.david.restapi.repository.LibraryRepository;
import com.david.restapi.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    LibraryRepository libraryRepository;

    @InjectMocks
    LibraryService libraryService;

    @Test
    void testBuildUniqueID(){
        String id = libraryService.BuildUniqueID("Z",24);
        assertEquals("OLDZ24", id);
    }

    @Test
    void testBuildUniqueIDWithoutZ(){
        String id = libraryService.BuildUniqueID("abc",24);
        assertEquals("abc24", id);
    }

    @Test
    void testCheckBookAlreadyExist(){
        Library library = new Library();
        library.setId("123");
        library.setBook_name("MagnumOpus");
        library.setAuthor("Michael");
        library.setAisle(24);
        library.setIsbn("xyz");

        when(libraryRepository.findById("123")).thenReturn(Optional.of(library));

        boolean result = libraryService.checkBookAlreadyExist("123");
        assertTrue(result);
        verify(libraryRepository).findById("123");
    }

    @Test
    void testCheckBookAlreadyExistWithBookNotPresent(){
        when(libraryRepository.findById("123")).thenReturn(Optional.empty());
         boolean result = libraryService.checkBookAlreadyExist("123");
         assertFalse(result);
    }


}
