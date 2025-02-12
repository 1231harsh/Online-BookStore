package com.hvrc.bookStore.service;

import com.hvrc.bookStore.dto.BookDTO;
import com.hvrc.bookStore.model.Book;
import com.hvrc.bookStore.model.User;
import com.hvrc.bookStore.repository.BookRepository;
import com.hvrc.bookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private UserRepository userRepository;


    public boolean save(Book book) {
        Book savedBook = bookRepository.save(book);

        return true;
    }

    public List<BookDTO> getBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOS = books.stream().map(book -> new BookDTO(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getDescription(),
                book.getCategory(),
                book.getRentalPrice(),
                book.getBuyPrice(),
                book.getCity(),
                book.getPhoto(),
                book.getPhoneNumber(),
                book.isAvailableForRent())).collect(Collectors.toList());
        return bookDTOS;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<Book> getBooksByCity(String city) {
        return bookRepository.findByCity(city);
    }

    public List<Book> getBooksForRentByCity(String city) {
        return bookRepository.findByCityAndAvailableForRentTrue(city);
    }

    public void sellBook(Long userId,Long bookId) {
        if (bookRepository.existsById(bookId)) {
//            Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
////            User user = userRepository.findById(userId).orElseThrow();
////            String ownerPhoneNumber = book.getPhoneNumber();
            bookRepository.deleteById(bookId);
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public void rentBook(Long userId,Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow();
        if (book.isAvailableForRent()) {
//            String ownerPhoneNumber = book.getPhoneNumber();
            book.setAvailableForRent(false);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Book is already rented");
        }
    }

    public void returnBook(Long userId,Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow();
        if (!book.isAvailableForRent()) {
//            String ownerPhoneNumber = book.getPhoneNumber();
            book.setAvailableForRent(true);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Book is already available for rent");
        }
    }

    public void viewedBook(Long userId,Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow();
    }
}
