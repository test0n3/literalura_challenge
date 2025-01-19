package com.aluracursos.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aluracursos.literalura.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  @Query("SELECT a FROM Author a WHERE a.name = :name")
  Author findbyName(String name);

  @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND a.deathYear >= :year")
  List<Author> findAuthorsByYear(int year);

}
