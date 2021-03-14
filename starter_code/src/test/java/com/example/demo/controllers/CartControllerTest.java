package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp(){
        Cart cart =new Cart();
        cart.setId(1L);
        User user=new User();
        user.setId(1L);
        user.setUsername("nada");
        user.setPassword("passNada");
        cart.setUser(user);
        user.setCart(cart);
        Item item=new Item();
        item.setId(1L);
        item.setPrice(new BigDecimal(1.00000));
        item.setName("testItem");
        ArrayList<Item> listItems=new ArrayList<>();
        listItems.add(item);
        cart.setItems(listItems);
        when(userRepository.findByUsername("nada")).thenReturn(user);
        when(itemRepository.findById(any())).thenReturn(Optional.of(item));


    }

    @Order(1)
    @Test
    public void addToCartTest() {

        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(10);
        request.setItemId(1);
        request.setUsername("nada");
        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request.getUsername(), response.getBody().getUser().getUsername());
        assertEquals(11, response.getBody().getItems().size());
        Boolean findId = false;
        for (Item item : response.getBody().getItems()) {
            if (item.getId().equals(request.getItemId())) {
                findId = true;
                break;
            }
            assertEquals(true, findId);


        }

    }

    @Order(2)
    @Test
    public void removeFromCartTest(){

        ModifyCartRequest request = new ModifyCartRequest();
        request.setQuantity(5);
        request.setItemId(1);
        request.setUsername("nada");
        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("nada",response.getBody().getUser().getUsername());
        assertEquals(0,response.getBody().getItems().size());
        Boolean findId = false;
        for (Item item : response.getBody().getItems()) {
            if (item.getId().equals(request.getItemId())) {
                findId = true;
                break;
            }
            assertEquals(false, findId);


        }

    }

}
