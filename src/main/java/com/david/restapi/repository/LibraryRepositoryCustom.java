package com.david.restapi.repository;

import com.david.restapi.models.Library;

import java.util.List;

public interface LibraryRepositoryCustom {
    List<Library> findAllByName(String authorName);
}
