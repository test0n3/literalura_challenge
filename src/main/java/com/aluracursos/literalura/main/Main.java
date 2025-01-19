package com.aluracursos.literalura.main;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import com.aluracursos.literalura.model.APIResponseData;
import com.aluracursos.literalura.model.Author;
import com.aluracursos.literalura.model.AuthorData;
import com.aluracursos.literalura.model.Book;
import com.aluracursos.literalura.model.BookData;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BookRepository;
import com.aluracursos.literalura.service.DataConvert;
import com.aluracursos.literalura.service.Display;
import com.aluracursos.literalura.service.RequestAPI;

public class Main {
  private Scanner input = new Scanner(System.in);
  private RequestAPI requestAPI = new RequestAPI();
  private DataConvert dataConvert = new DataConvert();
  private final String URL_BASE = "https://gutendex.com/books/";
  private final Map<Integer, String> LANGUAGES = Map.of(1, "es", 2, "en", 3, "fr");

  private BookRepository bookRepository;
  private AuthorRepository authorRepository;

  public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  public void displayMenu() {
    var option = -1;
    while (option != 0) {
      Display.mainMenu();
      System.out.print("Elija una opción: ");

      if (!input.hasNextInt()) {
        System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
        input.nextLine();
        continue;
      }

      option = input.nextInt();
      input.nextLine();

      switch (option) {
        case 1:
          searchBookByTitleFromAPI();
          break;
        case 2:
          displayRegisteredBooks();
          break;
        case 3:
          displayRegisteredAuthors();
          break;
        case 4:
          System.out.print("Ingrese el año para listar los autores: ");
          if (input.hasNextInt()) {
            int year = input.nextInt();
            input.nextLine();
            displayAliveAuthorsByYear(year);
          } else {
            System.out.println("Entrada inválida. Por favor, ingrese un año válido.");
            input.nextLine();
          }
          break;
        case 5:
          displayBooksByLanguage();
          break;
        case 0:
          System.out.println("Saliendo...");
          break;
        default:
          System.out.println("Opción inválida");
      }
    }
  }

  private void displayBooksByLanguage() {
    Display.languageMenu();
    System.out.print("Elija el idioma que le interese: [1-3]: ");

    if (!input.hasNextInt()) {
      System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
      input.nextLine();
      return;
    }

    int op = input.nextInt();
    input.nextLine();
    String selectedLanguage = LANGUAGES.get(op);
    if (selectedLanguage == null) {
      System.out.println("Opción inválida");
      return;
    }
    List<Book> filteredBooks = bookRepository.findBooksByLanguage(selectedLanguage);
    if (filteredBooks.isEmpty()) {
      System.out.println("No se encontraron libros en el idioma seleccionado.");
      return;
    }
    filteredBooks.forEach(book -> Display.displayBook(book));
  }

  private void displayAliveAuthorsByYear(int year) {
    List<Author> aliveAuthors = authorRepository.findAuthorsByYear(year);
    aliveAuthors.forEach(author -> Display.displayAuthor(author));
  }

  private void displayRegisteredAuthors() {
    List<Author> registeredAuthors = authorRepository.findAll();

    if (registeredAuthors.isEmpty()) {
      System.out.println("No hay autores registrados.");
      return;
    }

    registeredAuthors.forEach(author -> Display.displayAuthor(author));
  }

  private void displayRegisteredBooks() {
    List<Book> registeredBooks = bookRepository.findAll();

    if (registeredBooks.isEmpty()) {
      System.out.println("No hay libros registrados.");
      return;
    }

    registeredBooks.forEach(book -> Display.displayBook(book));
  }

  private void searchBookByTitleFromAPI() {
    BookData bookData = getBookData();
    if (bookData != null) {
      AuthorData authorData = bookData.authors().get(0);

      Optional<Book> existingBook = bookRepository.findBookByOldId(bookData.oldId());
      System.out.println("existingBook: " + existingBook);

      if (existingBook.isEmpty()) {
        Author author = authorRepository.findbyName(authorData.name());
        System.out.println("existingAuthor: " + author);
        if (author == null) {
          author = new Author(authorData);
          authorRepository.save(author);
        }

        Book book = new Book(bookData);
        book.setAuthor(author);
        bookRepository.save(book);
        Display.displayBook(book);
      } else {
        System.out.println("No se puede registrar el libro más de una vez.");
      }
    }
  }

  private BookData getBookData() {
    System.out.print("Escriba el nombre del libro que desea buscar: ");
    var bookTitle = input.nextLine();

    if (bookTitle == null || bookTitle.isEmpty()) {
      System.out.println("El título del libro no puede estar vacío.");
      return null;
    }

    bookTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);
    var json = requestAPI.requestData(URL_BASE + "?search=" + bookTitle);

    APIResponseData apiResponseData = dataConvert.getData(json, APIResponseData.class);

    Optional<BookData> searchResult = apiResponseData.results().stream().findFirst();
    if (searchResult.isPresent()) {
      return searchResult.get();
    } else {
      System.out.println("No se encontró el libro.");
      return null;
    }
  }
}
