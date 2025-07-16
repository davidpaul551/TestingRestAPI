package com.david.restapi.repository;

import com.david.restapi.models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;




public class LibraryRepositoryCustomImpl implements LibraryRepositoryCustom{

    LibraryRepository libraryRepository;

    public LibraryRepositoryCustomImpl(@Lazy LibraryRepository libraryRepository ){

        this.libraryRepository = libraryRepository;
    }

    @Override
    public List<Library> findAllByName(String authorName) {

        List<Library> booksWIthAuthor = new ArrayList<>();

        List<Library> books = libraryRepository.findAll();
        for(Library item: books){
            if(item.getAuthor().equalsIgnoreCase(authorName)){
                booksWIthAuthor.add(item);
            }
        }
        return booksWIthAuthor;
    }
}
