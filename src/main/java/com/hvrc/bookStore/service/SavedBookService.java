package com.hvrc.bookStore.service;

import com.hvrc.bookStore.entity.Book;
import com.hvrc.bookStore.entity.SavedBook;
import com.hvrc.bookStore.entity.User;
import com.hvrc.bookStore.repository.SavedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedBookService {

    @Autowired
    private SavedBookRepository savedBookRepository;

    public List<SavedBook> getSavedBooksByUser(User user) {
        return savedBookRepository.findByUser(user);
    }

    public SavedBook saveBook(SavedBook savedBook) {
        return savedBookRepository.save(savedBook);
    }

    public void unsaveBook(User user, Book book) {
        savedBookRepository.deleteByUserAndBook(user, book);
    }
}
