package com.david.restapi.service;

import com.david.restapi.models.Library;
import com.david.restapi.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService {


    @Autowired
    LibraryRepository libraryRepository;

    public String BuildUniqueID(String isbn, int aisle){

        if(isbn.startsWith("Z")){
            return "OLD"+isbn+aisle;
        }
        return isbn+aisle;

    }

    public boolean checkBookAlreadyExist(String id){

        Optional<Library> library = libraryRepository.findById(id);

        if(library.isPresent()){
            return true;
        }else {
            return false;
        }


    }


}
