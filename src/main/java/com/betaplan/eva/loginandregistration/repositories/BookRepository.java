package com.betaplan.eva.loginandregistration.repositories;

import java.util.List;

import com.betaplan.eva.loginandregistration.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
}
