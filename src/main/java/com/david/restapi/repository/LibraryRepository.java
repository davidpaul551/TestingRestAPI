package com.david.restapi.repository;

import com.david.restapi.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,String> , LibraryRepositoryCustom
{
}
