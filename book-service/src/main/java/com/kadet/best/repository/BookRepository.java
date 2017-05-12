package com.kadet.best.repository;

import com.kadet.best.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Auto generated /books CRUD endpoints.
 * They are connected to 'books' DB table.
 */
@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, String> {

}