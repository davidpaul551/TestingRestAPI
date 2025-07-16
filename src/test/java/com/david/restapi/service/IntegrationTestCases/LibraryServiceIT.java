package com.david.restapi.service.IntegrationTestCases;


import com.david.restapi.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LibraryServiceIT {


    @Autowired
    private LibraryService libraryService;


    @Test
    void testBuildUniqueIDWhenStartsWithZ(){
        String id = libraryService.buildUniqueID("Z" ,123);
        assertEquals("OLDZ123",id);

    }

    @Test
    void testBuildUniqueID_WhenDoesNotStartWithZ() {
        String id = libraryService.buildUniqueID("abc", 123);
        assertEquals("abc123", id);
    }

    @Test
    void testCheckBookAlreadyExistWhenNotExist(){
        boolean result = libraryService.checkBookAlreadyExist("aaaaaa");
        assertFalse(result);
    }

    @Test
    void testCheckBookAlreadyExistWhenExist(){
        boolean result = libraryService.checkBookAlreadyExist("abc123");
        System.out.println(result);
        assertTrue(result);
    }


}
