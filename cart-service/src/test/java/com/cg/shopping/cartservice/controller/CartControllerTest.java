package com.cg.shopping.cartservice.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.cg.shopping.cartservice.dto.ProductInOrder;
import com.cg.shopping.cartservice.entity.Cart;
import com.cg.shopping.cartservice.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;

class CartControllerTest {
	
	
	private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cartController = new CartController(cartService);
    }

//    @Test
//    public void testAddCart_Success() {
//        ProductInOrder productInOrder = new ProductInOrder();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//
//        when(cartService.mergeLocalCart(any(), anyString())).thenReturn(new Cart());
//
//        boolean result = cartController.addCart(productInOrder, auth);
//
//        assertTrue(result);
//        verify(cartService, times(1)).mergeLocalCart(any(), anyString());
//    }
    
    @Test
    public void testAddCart_Success() {
        ProductInOrder productInOrder = new ProductInOrder();
        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");

        // Stub the void method using doNothing()
        doNothing().when(cartService).mergeLocalCart(any(), anyString());

        boolean result = cartController.addCart(productInOrder, auth);

        assertTrue(result);
        verify(cartService, times(1)).mergeLocalCart(any(), anyString());
    }
    
    
    
    

//    @Test
//    public void testAddCart_Failure() {
//        ProductInOrder productInOrder = new ProductInOrder();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//
//        when(cartService.mergeLocalCart(any(), anyString())).thenThrow(new RuntimeException());
//
//        boolean result = cartController.addCart(productInOrder, auth);
//
//        assertFalse(result);
//        verify(cartService, times(1)).mergeLocalCart(any(), anyString());
//    }
//    
    
    
    
//    @Test
//    public void testAddCart_Failure() {
//        ProductInOrder productInOrder = new ProductInOrder();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//
//        // Stub the void method to throw an exception
//        doThrow(new RuntimeException("Expected exception message")).when(cartService).mergeLocalCart(any(), anyString());
//
//        boolean result = cartController.addCart(productInOrder, auth);
//
//        assertFalse(result);
//        verify(cartService, times(1)).mergeLocalCart(any(), anyString());
//    }
    
    
    
    
    

//    @Test
//    public void testMergeCart_Success() {
//        Set<ProductInOrder> productInOrders = new HashSet<>();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//        Cart cart = new Cart();
//
//        when(cartService.mergeLocalCart(any(), anyString())).thenReturn(cart);
//
//        ResponseEntity<Cart> responseEntity = cartController.mergeCart(productInOrders, auth);
//
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(cart, responseEntity.getBody());
//        verify(cartService, times(1)).mergeLocalCart(any(), anyString());
//    }
//    
//    @Test
//    public void testMergeCart_Success() {
//        Set<ProductInOrder> productInOrders = new HashSet<>();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//        
//        // Create an expected Cart object with the expected data
//        Cart expectedCart = new Cart();
//        expectedCart.setCartId("123"); // Set the expected cartId, if applicable
//        // Set other properties of the expectedCart as needed
//
//        // Stub the void method to do nothing
//        doNothing().when(cartService).mergeLocalCart(any(), anyString());
//
//        ResponseEntity<Cart> responseEntity = cartController.mergeCart(productInOrders, auth);
//
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        
//        // Compare the actual result with the expectedCart
//        assertEquals(expectedCart, responseEntity.getBody());
//
//        // Verify that cartService.mergeLocalCart was called with the expected arguments
//        verify(cartService, times(1)).mergeLocalCart(productInOrders, auth.getName());
//    }
//   
    
    

//    @Test
//    public void testMergeCart_Failure() {
//        Set<ProductInOrder> productInOrders = new HashSet<>();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//
//        when(cartService.mergeLocalCart(any(), anyString())).thenThrow(new RuntimeException());
//
//        ResponseEntity<Cart> responseEntity = cartController.mergeCart(productInOrders, auth);
//
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNull(responseEntity.getBody());
//        verify(cartService, times(1)).mergeLocalCart(any(), anyString());
//    }
    
    
//    @Test
//    public void testMergeCart_Failure() {
//        Set<ProductInOrder> productInOrders = new HashSet<>();
//        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
//
//        // Stub the void method to throw an exception
//        doThrow(new RuntimeException("Expected exception message")).when(cartService).mergeLocalCart(any(), anyString());
//
//        ResponseEntity<Cart> responseEntity = cartController.mergeCart(productInOrders, auth);
//
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertNull(responseEntity.getBody());
//
//        // Verify that cartService.mergeLocalCart was called with the expected arguments
//        verify(cartService, times(1)).mergeLocalCart(productInOrders, auth.getName());
//    }
    

    @Test
    public void testGetCart() {
        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
        Cart cart = new Cart();

        when(cartService.getCart(anyString())).thenReturn(cart);

        Cart result = cartController.getCart(auth);

        assertNotNull(result);
        assertEquals(cart, result);
        verify(cartService, times(1)).getCart(anyString());
    }

    @Test
    public void testGetAllCart() {
        List<Cart> cartList = Collections.singletonList(new Cart());

        when(cartService.getAllCart()).thenReturn(cartList);

        ResponseEntity<List<Cart>> responseEntity = cartController.getAllCart();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cartList, responseEntity.getBody());
        verify(cartService, times(1)).getAllCart();
    }

    @Test
    public void testCheckout_Success() {
        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
        String orderId = "123";

        when(cartService.checkout(auth)).thenReturn(orderId);

        ResponseEntity<Map<String, String>> responseEntity = cartController.checkout(auth);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Map<String, String> responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(orderId, responseBody.get("orderId"));
        verify(cartService, times(1)).checkout(auth);
    }

	

}