package com.hvrc.bookStore.service;

import com.hvrc.bookStore.dto.CartDTO;
import com.hvrc.bookStore.dto.CartMapper;
import com.hvrc.bookStore.model.*;
import com.hvrc.bookStore.repository.CartRepository;
import com.hvrc.bookStore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartItemsService cartItemsService;

    @Autowired
    private OrderRepository orderRepository;

    public Cart getCartByUsername(String username) {
        Optional<Cart> cartOptional;
        User user = userService.findByUsername(username);
        cartOptional=cartRepository.findByUser(user);
        if(cartOptional.isPresent()){
            return cartOptional.get();
        }else{
            createCart(user);
            return getCartByUsername(username);
        }
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow();
    } // findById()

    private boolean createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        return true;
    }

    public boolean addToCart(String username, Long bookId,boolean isRenting) {
        Cart cart = getCartByUsername(username);
        Book book = bookService.getBookById(bookId);

        Optional<CartItems> cartItemsOptional=cartItemsService.getCartItemsByCartAndBook(cart, book);

    if (cartItemsOptional.isPresent()) {
        CartItems cartItem = cartItemsOptional.get();
        cartItem.setRenting(isRenting);
        cartItemsService.save(cartItem);
    } else {
        CartItems cartItem = new CartItems();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setRenting(isRenting);
        cartItemsService.save(cartItem);
    }
        return true;
    }

    public boolean removeFromCart(String username, Long bookId) {
        Cart cart= getCartByUsername(username);
        Book book = bookService.getBookById(bookId);

        Optional<CartItems> cartItemOptional = cartItemsService.getCartItemsByCartAndBook(cart, book);

        if (cartItemOptional.isPresent()) {
            cartItemsService.delete(cartItemOptional.get());
            return true;
        }

        return false;
    }

    public boolean removeAll(String name) {
        User user = userService.findByUsername(name);
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return true;
    }

}
