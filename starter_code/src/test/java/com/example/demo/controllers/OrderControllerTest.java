package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;





    @Before
    public void setup() {

        when(userRepository.findByUsername("nada")).thenReturn(returnUser());

    }

    @Order(1)
    @Test
    public void submitTest() {

        ResponseEntity<UserOrder> secondResponse = orderController.submit("nada");
        assertNotNull(secondResponse);
        assertEquals(200, secondResponse.getStatusCodeValue());
        assertEquals(false, secondResponse.getBody().getItems().isEmpty());
        Boolean findId = false;
        for (Item item : secondResponse.getBody().getItems()) {
            if (item.getName().equals("item")) {
                findId = true;
                break;
            }
            assertEquals(true, findId);
        }


    }

    @Order(2)
    @Test
    public void submitInvalidTest() {

        ResponseEntity<UserOrder> response = orderController.submit("blablausername");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());

    }


    @Order(3)
    @Test
    public void getOrdersForUserTest(){

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("nada");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(!response.getBody().isEmpty());

    }





    public ArrayList<Item> returnItems() {
        Item item = new Item();
        item.setId(1L);
        item.setPrice(new BigDecimal(1.00000));
        item.setName("item");
        ArrayList<Item> listItems = new ArrayList<>();
        listItems.add(item);

        return listItems;
    }


    public Cart returnCart() {

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(returnItems());
        return cart;
    }


    public User returnUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("nada");
        user.setPassword("passNada");
        user.setCart(returnCart());
        return user;
    }
    public List<UserOrder> returnOrders(){

        UserOrder order = new UserOrder();
        order.setId(1L);
        order.setUser(returnUser());
        order.setItems(returnUser().getCart().getItems().stream().collect(Collectors.toList()));
        order.setTotal(returnUser().getCart().getTotal());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(order);
        return orders;
    }

}