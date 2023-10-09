package com.cg.shopping.orderservice.controller;

import com.cg.shopping.orderservice.client.CartServiceClient;
import com.cg.shopping.orderservice.client.ProfileServiceClient;
import com.cg.shopping.orderservice.client.UserProfile;
import com.cg.shopping.orderservice.client.Wallet;
import com.cg.shopping.orderservice.client.WalletRequest;
import com.cg.shopping.orderservice.client.WalletServiceClient;
import com.cg.shopping.orderservice.entity.Order;
import com.cg.shopping.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @Mock
    private CartServiceClient cartServiceClient;

    @Mock
    private WalletServiceClient walletServiceClient;

    @Mock
    private ProfileServiceClient profileServiceClient;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService, cartServiceClient, walletServiceClient, profileServiceClient)).build();
    }

    @Test
    void testPlaceOrder() throws Exception {
        Order order = new Order(); // Create an order as needed

        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders/placeOrder")
                .contentType("application/json")
                .content("{\"key\":\"value\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.key").value("value"));
    }

//    @Test
//    void testMakeOrder() throws Exception {
//        // Set up necessary mock data and expectations
//        Cart cart = new Cart(); // Create a cart as needed
//        UserProfile userProfile = new UserProfile(); // Create a user profile as needed
//
//        when(cartServiceClient.getCart(anyString())).thenReturn(cart);
//        when(profileServiceClient.getByEmail(anyString())).thenReturn(ResponseEntity.ok(userProfile));
//        when(orderService.placeOrder(any(Order.class))).thenReturn(new Order());
//
//        mockMvc.perform(post("/api/orders")
//                .with(authentication(mockAuthentication()))
//                .contentType("application/json"))
//                .andExpect(status().isCreated());
//    }

    

	@Test
    void testGetOrderById() throws Exception {
        Order order = new Order(); // Create an order as needed

        when(orderService.findById(anyString())).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("value")); // Adjust as needed for your order structure
    }

    
	
	@Test
    void testOnlinePayment() throws Exception {
        Order order = new Order(); // Create an order as needed

        when(profileServiceClient.getByEmail(anyString())).thenReturn(ResponseEntity.ok(new UserProfile())); // Mock user profile
        when(walletServiceClient.getByCustomerId(anyString())).thenReturn(ResponseEntity.ok(new Wallet())); // Mock wallet response
        when(walletServiceClient.payMoney(any(WalletRequest.class))).thenReturn(ResponseEntity.ok().build()); // Mock payment success
        when(orderService.getOrderById(anyInt())).thenReturn(Optional.of(order));

        mockMvc.perform(post("/api/orders/onlinePayment")
                .contentType("application/json")
                .content("{\"key\":\"value\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.key").value("value")); // Adjust as needed for your order structure
    }

//    @Test
//    void testChangeOrderStatus() throws Exception {
//        when(orderService.changeOrderStatus(anyInt(), anyInt())).thenReturn(ResponseEntity.ok().build());
//
//        mockMvc.perform(get("/api/orders/changeStatus/1/123"))
//                .andExpect(status().isOk());
//    }

    @Test
    void testGetAllOrder() throws Exception {
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>()); // Mock an empty list of orders

        mockMvc.perform(get("/api/orders/allOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetLatestOrder() throws Exception {
        when(orderService.getLatestOrder()).thenReturn(new Order()); // Mock a single order

        mockMvc.perform(get("/api/orders/findMaxByOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("value")); // Adjust as needed for your order structure
    }

    @Test
    void testGetByCustomerId() throws Exception {
        when(orderService.getOrderByCustomerId(anyString())).thenReturn(new ArrayList<>()); // Mock an empty list of orders

        mockMvc.perform(get("/api/orders/customer/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testUpdateOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/delete/123"))
                .andExpect(status().isNoContent());
    }

    private Authentication mockAuthentication() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return Mockito.mock(Authentication.class);
    }
    
    
    @Test
    void testFindByBuyerEmail() throws Exception {
        Page<Order> orderPage = mock(Page.class); // Mock a Page of orders

        when(orderService.findByBuyerEmail(anyString(), any())).thenReturn(orderPage);

        mockMvc.perform(get("/api/orders")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidEndpoint() throws Exception {
        mockMvc.perform(get("/api/orders/nonexistent"))
                .andExpect(status().isNotFound());
    }
}

