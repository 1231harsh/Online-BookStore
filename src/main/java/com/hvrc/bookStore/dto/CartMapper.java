package com.hvrc.bookStore.dto;

import com.hvrc.bookStore.model.Cart;
import com.hvrc.bookStore.model.CartItems;

import java.util.stream.Collectors;

public class CartMapper {

    public static CartDTO toCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCartItems(cart.getCartItems().stream()
                .map(CartMapper::toCartItemsDTO)
                .collect(Collectors.toList()));
        cartDTO.setUser(UserMapper.toUserDTO(cart.getUser()));
        return cartDTO;
    }

    public static CartItemsDTO toCartItemsDTO(CartItems cartItems) {
        CartItemsDTO cartItemsDTO = new CartItemsDTO();
        cartItemsDTO.setId(cartItems.getId());
        cartItemsDTO.setBookId(cartItems.getBook().getId());
        cartItemsDTO.setBookName(cartItems.getBook().getName());
        cartItemsDTO.setAuthor(cartItems.getBook().getAuthor());
        cartItemsDTO.setBuyPrice(cartItems.getBook().getBuyPrice());
        cartItemsDTO.setRentalPrice(cartItems.getBook().getRentalPrice());
        cartItemsDTO.setCity(cartItems.getBook().getCity());
        cartItemsDTO.setPhoneNumber(cartItems.getBook().getPhoneNumber());
        cartItemsDTO.setAvailableForRent(cartItems.getBook().isAvailableForRent());
        cartItemsDTO.setPhoto(cartItems.getBook().getPhoto());
        cartItemsDTO.setRenting(cartItems.isRenting());

        return cartItemsDTO;
    }
}
