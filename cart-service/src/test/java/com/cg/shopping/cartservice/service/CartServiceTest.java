package com.cg.shopping.cartservice.service;

import static org.junit.jupiter.api.Assertions.*;
import com.cg.shopping.cartservice.client.Order;
import com.cg.shopping.cartservice.client.OrderServiceClient;
import com.cg.shopping.cartservice.dao.CartRepository;
import com.cg.shopping.cartservice.dto.ProductInOrder;
import com.cg.shopping.cartservice.entity.Cart;
import com.cg.shopping.cartservice.entity.Items;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class CartServiceTest {
	
	
	@Mock
    private CartRepository cartRepository;

    @Mock
    private OrderServiceClient orderServiceClient;

    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cartService = new CartService(cartRepository, orderServiceClient);
    }

    @Test
    public void testGetCartById_ExistingCartId() {
        int cartId = 1;
        Cart expectedCart = new Cart();
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(expectedCart));

        Cart result = cartService.getCartById(cartId);

        assertNotNull(result);
        assertEquals(expectedCart, result);
    }

    @Test
    public void testGetCartById_NonExistingCartId() {
        int cartId = 1;
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.empty());

        Cart result = cartService.getCartById(cartId);

        assertNotNull(result);
        assertNull(result.getCartId());
    }

    @Test
    public void testGetAllCart() {
        List<Cart> expectedCarts = List.of(new Cart(), new Cart());
        when(cartRepository.findAll()).thenReturn(expectedCarts);

        List<Cart> result = cartService.getAllCart();

        assertNotNull(result);
        assertEquals(expectedCarts, result);
    }

    @Test
    public void testGetCart_ExistingUser() {
        String userEmail = "test@example.com";
        Cart expectedCart = new Cart();
        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.of(expectedCart));

        Cart result = cartService.getCart(userEmail);

        assertNotNull(result);
        assertEquals(expectedCart, result);
    }

    @Test
    public void testGetCart_NewUser() {
        String userEmail = "newuser@example.com";
        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.empty());

        Cart result = cartService.getCart(userEmail);

        assertNotNull(result);
        assertNull(result.getCartId());
        assertEquals(userEmail, result.getUser());
    }

    @Test
    public void testMergeLocalCart() {
        String user = "user@example.com";
        Set<ProductInOrder> productInOrders = new HashSet<>();
        Cart existingCart = new Cart(user);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(existingCart));

        cartService.mergeLocalCart(productInOrders, user);

        verify(cartRepository, times(1)).save(existingCart);
        assertEquals(productInOrders, existingCart.getProducts());
    }

//    @Test
//    public void testCheckout_Success() {
//        String userEmail = "user@example.com";
//        Authentication auth = new UsernamePasswordAuthenticationToken(userEmail, "password");
//        Cart userCart = new Cart(userEmail);
//        ResponseEntity<Order> orderResponseEntity = new ResponseEntity<>(new Order(), HttpStatus.CREATED);
//
//        when(orderServiceClient.makeOrder(userEmail)).thenReturn(orderResponseEntity);
//        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.of(userCart));
//
//        String orderId = cartService.checkout(auth);
//
//        assertEquals(orderResponseEntity.getBody().get_id(), orderId);
//        verify(cartRepository, times(1)).delete(userCart);
//    }

//    @Test
//    public void testCheckout_Failure() {
//        String userEmail = "user@example.com";
//        Authentication auth = new UsernamePasswordAuthenticationToken(userEmail, "password");
//        Cart userCart = new Cart(userEmail);
//        ResponseEntity<Order> orderResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        when(orderServiceClient.makeOrder(userEmail)).thenReturn(orderResponseEntity);
//        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.of(userCart));
//
//        String orderId = cartService.checkout(auth);
//
//        assertEquals("", orderId);
//        verify(cartRepository, never()).delete(userCart);
//    }

	

}