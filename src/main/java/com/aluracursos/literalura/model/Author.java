package com.aluracursos.literalura.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
  private Integer birthYear;
  private Integer deathYear;
  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Book> books;

  public Author() {
  }

  public Author(AuthorData authorData) {
    this.name = authorData.name();
    this.birthYear = authorData.birthYear();
    this.deathYear = authorData.deathYear();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public Integer getDeathYear() {
    return deathYear;
  }

  public void setDeathYear(Integer deathYear) {
    this.deathYear = deathYear;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  @Override
  public String toString() {
    return "Author [id=" + id + ", name=" + name + ", birthYear=" + birthYear + ", deathYear=" + deathYear + ", books="
        + books + "]";
  }

}
