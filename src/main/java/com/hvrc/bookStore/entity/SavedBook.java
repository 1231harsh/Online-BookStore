package com.hvrc.bookStore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SavedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private boolean liked;
}
