package com.hvrc.bookStore.service;

import com.hvrc.bookStore.entity.SavedBook;
import com.hvrc.bookStore.repository.SavedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedBookService {

    @Autowired
    private SavedBookRepository savedBookRepository;

    public List<SavedBook> getSavedBooksByUser(Long userId) {
        return savedBookRepository.findByUserId(userId);
    }

    public SavedBook saveBook(SavedBook savedBook) {
        return savedBookRepository.save(savedBook);
    }
}
