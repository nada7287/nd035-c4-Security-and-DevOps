package com.example.demo.controllers;


import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

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
        item.setName("item");
        ArrayList<Item> listItems=new ArrayList<>();
        listItems.add(item);
        cart.setItems(listItems);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.findAll()).thenReturn(listItems);
        when(itemRepository.findByName("item")).thenReturn(listItems);

    }

    @Order(1)
    @Test
    public void getItemsTest(){
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

    }

    @Order(2)
    @Test
    public void getItemByIdTest(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item item = response.getBody();

    }

    @Order(3)
    @Test
    public void getItemByIdInvalidTest(){

        ResponseEntity<Item> response = itemController.getItemById(10L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }


    @Order(4)
    @Test
    public  void getByNameTest(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

    }

}
