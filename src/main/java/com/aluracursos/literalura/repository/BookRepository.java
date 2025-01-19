package com.aluracursos.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aluracursos.literalura.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  @Query("SELECT b FROM Book b WHERE b.oldId = :oldId")
  Optional<Book> findBookByOldId(Long oldId);

  @Query("SELECT b from Book b WHERE b.language = :language")
  List<Book> findBooksByLanguage(String language);
}
