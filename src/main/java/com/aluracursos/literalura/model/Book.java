package com.aluracursos.literalura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  @ManyToOne
  private Author author;
  private String language;
  private Double downloadCount;
  @Column(unique = true)
  private Long oldId;

  public Book() {
  }

  public Book(BookData bookData) {
    this.oldId = bookData.oldId();
    this.title = bookData.title();
    this.language = bookData.languages().get(0);
    this.downloadCount = bookData.downloadCount();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Double getDownloadCount() {
    return downloadCount;
  }

  public void setDownloadCount(Double downloadCount) {
    this.downloadCount = downloadCount;
  }

  public Long getOldId() {
    return oldId;
  }

  public void setOldId(Long oldId) {
    this.oldId = oldId;
  }

}
