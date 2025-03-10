package com.hvrc.bookStore.service;

import com.hvrc.bookStore.entity.UserBookActivity;
import com.hvrc.bookStore.repository.UserBookActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserBookActivityService {

    @Autowired
    private UserBookActivityRepository userBookActivityRepository;

    public List<UserBookActivity> getUserActivities() {

        return userBookActivityRepository.findAll();
    }

    public void save(UserBookActivity userBookActivity) {
        userBookActivityRepository.save(userBookActivity);
    }

}
