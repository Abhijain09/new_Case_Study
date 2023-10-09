package com.cg.shopping.cartservice.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.cg.shopping.cartservice.dao.CartRepository;
import com.cg.shopping.cartservice.entity.Cart;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;


@SpringBootTest
class CartRepositoryTest {
	
	@Mock
    private CartRepository cartRepository;

    @Test
    public void testFindByCartId_ExistingCartId() {
        int cartId = 1;
        Cart expectedCart = new Cart();
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(expectedCart));

        Cart result = cartRepository.findByCartId(cartId).orElse(null);

        assertEquals(expectedCart, result);
    }
    
    @Test
    public void testFindByCartId_NonExistingCartId() {
        int cartId = 1;
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.empty());

        Cart result = cartRepository.findByCartId(cartId).orElse(null);

        assertEquals(null, result);
    }
    
    

    @Test
    public void testFindTopByOrderByCartIdDesc() {
        Cart expectedCart = new Cart();
        when(cartRepository.findTopByOrderByCartIdDesc()).thenReturn(expectedCart);

        Cart result = cartRepository.findTopByOrderByCartIdDesc();

        assertEquals(expectedCart, result);
    }

    @Test
    public void testFindByUser_ExistingUser() {
        String userEmail = "test@example.com";
        Cart expectedCart = new Cart();
        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.of(expectedCart));

        Cart result = cartRepository.findByUser(userEmail).orElse(null);

        assertEquals(expectedCart, result);
    }

    @Test
    public void testFindByUser_NonExistingUser() {
        String userEmail = "newuser@example.com";
        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.empty());

        Cart result = cartRepository.findByUser(userEmail).orElse(null);

        assertEquals(null, result);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	 @Mock
//	    private CartRepository cartRepository;
//
//	    @InjectMocks
//	    private CartRepositoryTestService testService;
//
//	    @Test
//	    public void testFindByCartId_ExistingCartId() {
//	        int cartId = 1;
//	        Cart expectedCart = new Cart();
//	        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(expectedCart));
//
//	        Cart result = testService.findByCartId(cartId);
//
//	        assertEquals(expectedCart, result);
//	    }
//
//	    @Test
//	    public void testFindByCartId_NonExistingCartId() {
//	        int cartId = 1;
//	        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.empty());
//
//	        Cart result = testService.findByCartId(cartId);
//
//	        assertEquals(null, result);
//	    }
//
//	    @Test
//	    public void testFindTopByOrderByCartIdDesc() {
//	        Cart expectedCart = new Cart();
//	        when(cartRepository.findTopByOrderByCartIdDesc()).thenReturn(expectedCart);
//
//	        Cart result = testService.findTopByOrderByCartIdDesc();
//
//	        assertEquals(expectedCart, result);
//	    }
//
//	    @Test
//	    public void testFindByUser_ExistingUser() {
//	        String userEmail = "test@example.com";
//	        Cart expectedCart = new Cart();
//	        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.of(expectedCart));
//
//	        Cart result = testService.findByUser(userEmail);
//
//	        assertEquals(expectedCart, result);
//	    }
//
//	    @Test
//	    public void testFindByUser_NonExistingUser() {
//	        String userEmail = "newuser@example.com";
//	        when(cartRepository.findByUser(userEmail)).thenReturn(Optional.empty());
//
//	        Cart result = testService.findByUser(userEmail);
//
//	        assertEquals(null, result);
//	    }

	

}