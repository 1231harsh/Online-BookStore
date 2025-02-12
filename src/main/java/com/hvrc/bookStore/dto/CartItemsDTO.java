package com.hvrc.bookStore.dto;

import lombok.Data;

@Data
public class CartItemsDTO{

    private Long id;
    private Long bookId;
    private String bookName;
    private String author;
    private Double buyPrice;
    private Double rentalPrice;
    private String city;
    private String phoneNumber;
    private boolean availableForRent;
    private byte[] photo;
    private boolean renting;
}
