package com.aluracursos.literalura.main;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.aluracursos.literalura.model.APIResponseData;
import com.aluracursos.literalura.model.Book;
import com.aluracursos.literalura.model.BookData;
import com.aluracursos.literalura.service.DataConvert;
import com.aluracursos.literalura.service.RequestAPI;

public class Main {
  private Scanner input = new Scanner(System.in);
  private RequestAPI requestAPI = new RequestAPI();
  private DataConvert dataConvert = new DataConvert();
  private final String URL_BASE = "https://gutendex.com/books/";

  private List<BookData> bookData = new ArrayList<>();

  public void displayMenu() {
    var option = -1;
    while (option != 0) {
      var menu = """
          Bienvenido a Literalura
          -----------------------

          1. Buscar libro por título
          2. Listar libros registrados
          3. Listas autores registrados
          4. Buscar autores vivos en un determinado año
          5. Listar libros por idioma

          0. Salir

          Elija una opción:
          """;
      System.out.println(menu);
      option = input.nextInt();
      input.nextLine();

      switch (option) {
        case 1:
          searchBookByTitleFromAPI();
          break;
        case 2:
          // displayRegisteredBooks();
          break;
        case 3:
          // displayRegisteredAuthors();
          break;
        case 4:
          // displayAliveAuthorsByYear();
          break;
        case 5:
          // displayBooksByLanguage();
          break;
        case 0:
          System.out.println("Saliendo...");
          break;
        default:
          System.out.println("Opción inválida");
      }
    }
  }

  private void searchBookByTitleFromAPI() {
    Optional<BookData> temporal = getBookData();
    if (temporal.isPresent()) {
      BookData data = temporal.get();
      // Book book = new Book(data);
      // System.out.println(data);
      bookData.add(data);
    } else {
      System.out.println("No se encontró el libro");
    }
    bookData.forEach(book -> System.out.println(book));
    System.out.println(bookData.size());
  }

  private Optional<BookData> getBookData() {
    System.out.print("Escriba el nombre del libro que desea buscar: ");
    var bookTitle = input.nextLine();

    bookTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);
    var json = requestAPI.requestData(URL_BASE + "?search=" + bookTitle);

    APIResponseData apiResponseData = dataConvert.getData(json, APIResponseData.class);

    Optional<BookData> searchResult = apiResponseData.results().stream().findFirst();
    return searchResult;
  }
}
