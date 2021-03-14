package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {


    private UserController userController;

    private UserRepository userRepository=mock(UserRepository.class);

    private CartRepository cartRepository=mock(CartRepository.class);

    private BCryptPasswordEncoder encoder =mock(BCryptPasswordEncoder.class);


    @Order(1)
    @Before
    public void setUp(){

    userController=new UserController();

        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",encoder);


    }


    @Order(2)
    @Test
    public void create_user_happy_pathTest(){
        when(encoder.encode("passNada")).thenReturn("thisIsHashed");
        CreateUserRequest request=new CreateUserRequest();
        request.setUsername("nada");
        request.setPassword("passNada");
        request.setConfirmPassword("passNada");
        final ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        assertEquals("nada",response.getBody().getUsername());
        assertEquals("thisIsHashed",response.getBody().getPassword());
        assertEquals(200,response.getStatusCodeValue());
    }

    @Order(3)
    @Test
    public void create_user_not_happy_pathTest(){

        CreateUserRequest request=new CreateUserRequest();
        request.setUsername("nada");
        request.setPassword("pas");
        request.setConfirmPassword("pas");
        final ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        assertEquals(400,response.getStatusCodeValue());




    }

    @Order(4)
    @Test
    public void findByUserNameTest(){
        long id = 1L;
        User user = new User();
        user.setUsername("nada");
        user.setPassword("passNada");
        user.setId(id);

        when(userRepository.findByUsername("nada")).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName("nada");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("nada", response.getBody().getUsername());
        assertEquals("passNada", response.getBody().getPassword());
        assertEquals(id,response.getBody().getId());
    }

    @Order(5)
    @Test
    public void findByIdTest(){
        long id = 1L;
        User user = new User();
        user.setUsername("nada");
        user.setPassword("passNada");
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.findById(id);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getUsername(),response.getBody().getUsername());
        assertEquals(user.getPassword(),response.getBody().getPassword());
        assertEquals(id,response.getBody().getId());


    }

}
