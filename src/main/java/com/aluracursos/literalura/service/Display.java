package com.aluracursos.literalura.service;

import java.util.stream.Collectors;

import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.Book;

public class Display {
  public static void mainMenu() {
    System.out.println("\n----- Menú Principal -----" +
        "\n1. Buscar libro por título" +
        "\n2. Mostrar libros registrados" +
        "\n3. Mostrar autores registrados" +
        "\n4. Mostrar autores vivos en un año" +
        "\n5. Mostrar libros por idioma" +
        "\n\n0. Salir" +
        "\n-------------------------\n");
  }

  public static void languageMenu() {
    System.out.println("\n----- Idiomas -----" +
        "\n1. Español" +
        "\n2. Inglés" +
        "\n3. Francés" +
        "\n\n0. Atrás" +
        "\n-----------------\n");
  }

  public static void displayBook(Book book) {
    System.out.println("\n----- LIBRO -----" +
        "\nTítulo: " + book.getTitle() +
        "\nAutor: " + book.getAuthor().getName() +
        "\nIdioma: " + book.getLanguage() +
        "\nNúmero de Descargas: " + book.getDownloadCount() +
        "\n-----------------\n");
  }

  public static void displayAuthor(Author author) {
    System.out.println("\n----- Autor -----" +
        "\nAutor: " + author.getName() +
        "\nFecha de nacimiento: " + nullController(author.getBirthYear()) +
        "\nFecha de fallecimiento: " + nullController(author.getDeathYear()) +
        "\nLibros: "
        + author.getBooks().stream().map(book -> book.getTitle()).collect(Collectors.toList()) +
        "\n-----------------\n");
  }

  private static String nullController(Object obj) {
    if (obj == null) {
      return "";
    } else {
      return obj.toString();
    }
  }
}
